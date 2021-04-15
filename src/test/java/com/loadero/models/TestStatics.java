package com.loadero.models;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.google.gson.annotations.SerializedName;
import com.loadero.AbstractTestLoadero;
import loadero.model.MetricPaths;
import loadero.model.Statics;
import loadero.types.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
class MissingValues {
    private String name;
    private Path location;
    private Set<String> values;
}

@DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
public class TestStatics extends AbstractTestLoadero {
    // Set of Loadero type's values
    private static final Map<String, EnumSet<?>> locals = Map.of(
            "location",     EnumSet.copyOf(Arrays.asList(Location.values())),
            "computeUnit",  EnumSet.copyOf(Arrays.asList(ComputeUnit.values())),
            "mediaType",    EnumSet.copyOf(Arrays.asList(MediaType.values())),
            "network",      EnumSet.copyOf(Arrays.asList(Network.values())),
            "testMode",     EnumSet.copyOf(Arrays.asList(TestMode.values())),
            "incrementStrategy", EnumSet.copyOf(Arrays.asList(IncrementStrategy.values())),
            "audioFeed", EnumSet.copyOf(Arrays.asList(AudioFeed.values())),
            "videoFeed", EnumSet.copyOf(Arrays.asList(VideoFeed.values()))
    );
    // Enums that we have locally and their locations.
    // Key is name of the field from Statics.
    // Value is physical location of the respective Enum file.
    private static Map<String, Path> localEnumsLocation;
    
    @Test
    public void testGetMetricPaths() {
        Statics statics = loaderoClient.getLoaderoStatics();
        Assertions.assertNotNull(statics);
        MetricPaths metricPaths = loaderoClient.getMetricPaths();
        log.info(metricPaths.getMachinePaths());
        log.info(metricPaths.getWebrtcPaths());
        Assertions.assertNotNull(metricPaths);
    }
    
    @Test
    public void generateMachineAndWebRtcEnums() {
        MetricPaths metricPaths = loaderoClient.getMetricPaths();
        Set<String> machinePaths = metricPaths.getMachinePaths();
        machinePaths.forEach(this::formatName);
        Set<String> webRtcPaths = metricPaths.getWebrtcPaths();
        webRtcPaths.forEach(this::formatName);
        Path machinePathsEnumLoc = Path.of("src/main/java/loadero/types/MachineAsserts.java");
        Path webRtcPathsEnumLoc = Path.of("src/main/java/loadero/types/WebRtcAsserts.java");
        
        String machinePathNewEnum = addMissingValuesToEnum(machinePathsEnumLoc, machinePaths);
        String webRtcPathNewEnum = addMissingValuesToEnum(webRtcPathsEnumLoc, webRtcPaths);
        
        try {
            Files.writeString(machinePathsEnumLoc, machinePathNewEnum, StandardCharsets.UTF_8);
            Files.writeString(webRtcPathsEnumLoc, webRtcPathNewEnum, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
        }
    }
    
    @Test
    public void updateLocalEnums() {
        Statics statics = loaderoClient.getLoaderoStatics();
        try {
            // Set of all Enum paths in types package.
            Set<Path> enumFiles = Files.walk(
                    Paths.get("src/main/java/com/loadero/types"))
                    .collect(Collectors.toSet());
            localEnumsLocation = mapKeysToLocations(locals.keySet(), enumFiles);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        
        List<MissingValues> missingEnumValues = findEnumMissingValues(statics);
        
        if (!missingEnumValues.isEmpty()) {
            log.info("Found missing values in local Enums. Updating...");
            Map<Path, String> updatedEnums = updateEnumValues(missingEnumValues);
            writeUpdatedEnumsBack(updatedEnums);
        } else {
            log.info("Local Enums are up to date.");
            Assertions.assertTrue(missingEnumValues.isEmpty());
        }
    }
    
    @Test
    public void testFormatName() {
        Assertions.assertEquals("DB_50_1080P",    formatName("1080p-50db"));
        Assertions.assertEquals("MARKED_720P",    formatName("720p-marked"));
        Assertions.assertEquals("AV_1080P",       formatName("1080pAV"));
        Assertions.assertEquals("DEFAULT",        formatName("default"));
        Assertions.assertEquals("PACKET_10",      formatName("10packet"));
        Assertions.assertEquals("PACKET_100",     formatName("100packet"));
        Assertions.assertEquals("CONNECTION_3G",  formatName("3g"));
        Assertions.assertEquals("AP_NORTHWEST_2", formatName("ap-northwest-2"));
        Assertions.assertEquals("CHROME_LATEST",  formatName("chromeLatest"));
        Assertions.assertEquals("FIREFOX_89",     formatName("firefox89"));
        Assertions.assertEquals("MACHINE_RAM_50TH", formatName("machine/ram/50th"));
        Assertions.assertEquals("MINUS_128_DB", formatName("-128db"));
        Assertions.assertEquals("KBPS_128", formatName("128kbps"));
        Assertions.assertEquals("DTMF", formatName("dtmf"));
        Assertions.assertEquals("CENTER_MARKED_1080P", formatName("1080p-marked-center"));
        Assertions.assertEquals("LEFT_TOP_MARKED_1080P", formatName("1080p-marked-top-left"));
        Assertions.assertEquals("P_1080", formatName("1080p"));
    }
    
    private void writeUpdatedEnumsBack(Map<Path, String> updatedEnums) {
        Objects.requireNonNull(updatedEnums);
        updatedEnums.forEach((loc, updatedEnum) -> {
            try {
                Files.writeString(loc, updatedEnum, StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error(e.getMessage(), e.fillInStackTrace());
            }
        });
    }
    
    
    /**
     * Goes through list of MissingValues objects and adds missing values to Enums
     * in localEnumLocation.
     */
    private Map<Path, String> updateEnumValues(List<MissingValues> missingValues) {
        Objects.requireNonNull(missingValues);
        Map<Path, String> enumLocs = new HashMap<>();
        missingValues.forEach(v -> {
            log.info("Local Enum {} missing values: {}",
                    v.getLocation().getFileName(),
                    v.getValues()
            );
            log.info("Adding missing values...");
            String updatedEnum = addMissingValuesToEnum(localEnumsLocation.get(v.getName()), v.getValues());
            enumLocs.put(v.getLocation(), updatedEnum);
        });
        
        return enumLocs;
    }
    
    /**
     * Maps a set of keys to a respective file locations.
     * Assumed that file name contains key.
     * @param keys               Set of keys that represents fields in Statics.
     * @param enumFilesLocations Set of path location where for each enum.
     * @return                   HashMap where key is field's name and value it's location.
     */
    private static Map<String, Path> mapKeysToLocations(Set<String> keys, Set<Path> enumFilesLocations) {
        Objects.requireNonNull(enumFilesLocations);
        Map<String, Path> keysLocations = new HashMap<>();
        enumFilesLocations.forEach(enumLocation -> {
            keys.forEach(k -> {
                if (enumLocation.toString().toLowerCase().contains(k.toLowerCase())) {
                    keysLocations.put(k, enumLocation);
                };
            });
        });
        return keysLocations;
    }
    
    /**
     * Parses file provided by pathToEnum argument and adds new missing Enum values from
     * missingValues set.
     * @param pathToEnum    Path to Enum that we need to update.
     * @param missingValues Set of values that are currently missing in Enum.
     * @return              Enum as String that later can be written to file.
     */
    private String addMissingValuesToEnum(Path pathToEnum, Set<String> missingValues) {
        Objects.requireNonNull(pathToEnum, "Path to Enum is null");
        String result = "";
        try {
            ParserConfiguration configuration = new ParserConfiguration();
            configuration.setLexicalPreservationEnabled(true);
            String currentEnumBody = Files.readString(pathToEnum);
            currentEnumBody = currentEnumBody.substring(currentEnumBody.lastIndexOf("public enum"));
            StaticJavaParser.setConfiguration(configuration);
            EnumDeclaration newEnumBody = StaticJavaParser.parseBodyDeclaration(currentEnumBody).asEnumDeclaration();
            
            // Going over all missing values in set and creating respective
            // field value in Enum with @SerializedName annotation and label.
            missingValues.forEach(v -> {
                EnumConstantDeclaration constantDeclaration = new EnumConstantDeclaration();
                AnnotationExpr annotationExpr = new SingleMemberAnnotationExpr(
                        new Name("SerializedName"),
                        new StringLiteralExpr(v)
                );
                
                constantDeclaration.addAnnotation(annotationExpr);
                constantDeclaration.setName(formatName(v));
                constantDeclaration.addArgument(new StringLiteralExpr(v));
                newEnumBody.addEntry(constantDeclaration);
            });
    
            CompilationUnit cu = new CompilationUnit();
            PackageDeclaration packageDeclaration = new PackageDeclaration(new Name("loadero.types"));
            cu.setPackageDeclaration(packageDeclaration);
            cu.addImport(SerializedName.class);
            // Replacing old enum body with new and adding package and import statements.
            result = cu.toString() + currentEnumBody.replace(currentEnumBody, newEnumBody.toString());
        } catch (IOException ex) {
            log.error("{} {}", ex.getMessage(), ex.getCause());
        }
        return result;
    }
    
    /** Formats:
     * this-is-name  -> THIS_IS_NAME.
     * firefoxLatest -> FIREFOX_LATEST
     * chrome67      -> CHROME_67
     * 1080p-30db    -> DB_30_1080P
     * 480pAV        -> AV_180P
     * machine/ram/50th -> MACHINE_RAM_50TH
     * -128db -> MINUS_128_DB
     */
    private String formatName(String name) {
        String formattedName = "";
        String regex = "";
        Matcher matcher;
        if (Character.isDigit(name.charAt(0))) {
            
            if (name.contains("-")) {
                String[] tokens = name.split("-");
                Collections.reverse(Arrays.asList(tokens));
                formattedName = String.join("_", tokens);
            }
    
            if (name.contains("db") && name.contains("-")) {
                regex = "(?<rest>.*)(?:-)(?<dbNum>[0-9]+)(?<db>[a-z]+)";
                matcher = getMatcher(regex, name);
                if (matcher.find()) {
                    formattedName = String.format("%s_%s_%s",
                            matcher.group("db"),
                            matcher.group("dbNum"),
                            matcher.group("rest"));
                }
            }

            if (name.contains("AV")) {
                regex = "(?<rest>.*)(?<AV>\\w{2})$";
                matcher = getMatcher(regex, name);
                if (matcher.find()) {
                    formattedName = String.format("%s_%s",
                            matcher.group("AV"),
                            matcher.group("rest"));
                }
            }

            if (name.contains("kbps")) {
                regex = "(?<nums>\\d+)(?<kbps>\\w+)$";
                matcher = getMatcher(regex, name);
                if (matcher.find()) {
                    formattedName = String.format("%s_%s",
                        matcher.group("kbps"),
                        matcher.group("nums"));
                }
            }
            
            if (name.contains("packet")) {
                regex = "^(?<lossNum>\\d{1,})(?<packet>[a-z]{2,})$";
                matcher = getMatcher(regex, name);
                if (matcher.find()) {
                    formattedName = String.format("%s_%s",
                            matcher.group("packet"),
                            matcher.group("lossNum"));
                }
            }
            
            if (name.equals("3g") || name.equals("4g")) {
                formattedName = String.format("CONNECTION_%s",
                        name.toUpperCase(Locale.ENGLISH));
            }

            if (name.endsWith("p")) {
                regex = "(?<nums>\\d+)(?<p>\\w{1})";
                matcher = getMatcher(regex, name);
                if (matcher.find()) {
                    formattedName = String.format("%s_%s",
                        matcher.group("p"), matcher.group("nums"));
                }
            }

            return formattedName.toUpperCase(Locale.ENGLISH);
        }

        if (name.startsWith("-")) {
            regex = "(?<minus>-)(?<nums>\\d+)(?<rest>\\w+)";
            matcher = getMatcher(regex, name);
            if (matcher.find()) {
                formattedName = String.format("MINUS_%s_%s",
                    matcher.group("nums"), matcher.group("rest"));
            }

            return formattedName.toUpperCase(Locale.ENGLISH);
        }
        
        if (name.startsWith("firefox") || name.startsWith("chrome")) {
            regex = "(?<browser>^firefox|^chrome)(?<rest>.*)";
            matcher = getMatcher(regex, name);
            if (matcher.find()) {
                formattedName = String.format("%s_%s",
                        matcher.group("browser"),
                        matcher.group("rest"));
                return formattedName.toUpperCase(Locale.ENGLISH);
            }
        }
    
        if (name.startsWith("machine") || name.startsWith("webrtc")) {
            return name.replace("/", "_").toUpperCase(Locale.ENGLISH);
        }
        
        return name.replace("-", "_").toUpperCase(Locale.ENGLISH);
    }
    
    private Matcher getMatcher(String regex, String name) {
        Pattern compile = Pattern.compile(regex);
        return compile.matcher(name);
    }
    
    /**
     * Iterates through fields in Statics object and constructs
     * List of MissingValues if there is any differences between values in local Enums and Statics.
     * Otherwise, returns an empty list.
     */
    private List<MissingValues> findEnumMissingValues(Statics staticsValues) {
        Objects.requireNonNull(staticsValues);
        Field[] fields = staticsValues.getClass().getDeclaredFields();
        List<MissingValues> missingValuesList = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                @SuppressWarnings("unchecked")
                Set<Map<String, String>> values = (Set<Map<String, String>>) field.get(staticsValues);
                Set<String> valsFromStatics = values.stream().map(v -> v.get("value")).collect(Collectors.toSet());
                Set<String> localEnumStringValues = locals.get(fieldName).stream()
                        .map(Enum::toString)
                        .collect(Collectors.toSet());
                
                if (!localEnumStringValues.containsAll(valsFromStatics)) {
                    Set<String> missValuesSet = getSetDifference(valsFromStatics, localEnumStringValues);
                    missingValuesList.add(
                            new MissingValues(fieldName, localEnumsLocation.get(fieldName), missValuesSet)
                    );
                }
            } catch (NullPointerException | IllegalAccessException ex) {
                log.error(ex.getMessage());
            }
        }
        return missingValuesList;
    }
    
    private Set<String> getSetDifference(Set<String> set1, Set<String> set2) {
        Set<String> tmp = new HashSet<>(set1);
        tmp.removeAll(set2);
        return tmp;
    }
}
