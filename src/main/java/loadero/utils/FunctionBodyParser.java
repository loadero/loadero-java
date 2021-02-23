package loadero.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import loadero.model.LoaderoTestScriptParams;

import java.io.File;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * This class responsibility is to provide simple parser to parse
 * TestUI code snippets from given path and return them as strings
 * to later pass those snippets as part of JSON request.
 */
public class FunctionBodyParser {
    private static final Pattern REPLACE_PARTICIPANT_ID  =
            Pattern.compile("(particId = )(\\d*)(;)");
    private static final Pattern REPLACE_CALL_DURATION   =
            Pattern.compile("(callDuration = )(\\d*)(;)");
    private static final Pattern REPLACE_ELEMENT_TIMEOUT =
            Pattern.compile("(elementTimeout = )(\\d*)(;)");
    private static final Pattern REPLACE_APP_URL         =
            Pattern.compile("(appUrl = )\"(.*)\"(;)");

    /**
     * Retrieves the content of the public void test(){}; method from .java file.
      * @param path - Path to the script.
     * @return      - Content of the script as String.
     */
    public static String getScriptContent(String path) {
        String result = "";
        try {
            CompilationUnit cu =  StaticJavaParser.parse(new File(path));
            PrettyPrinterConfiguration conf = new PrettyPrinterConfiguration();
            // removing comments
            conf.setPrintComments(false);
            // preserving chaining, otherwise would be oneliner
            conf.setColumnAlignFirstMethodChain(true);
            PrettyPrinter printer = new PrettyPrinter(conf);
            result = printer.print(cu);
            result = result.substring(result.lastIndexOf("public void test"));
            result = result.substring(0, result.lastIndexOf('}'));
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Applies new parameters provided by LoaderoTestScriptParams object to the script in .java file.
     * @param path  - Path to the script.
     * @param scriptParams - LoaderoTestScriptParams with parameters wish to apply.
     * @return  - Script content with new parameters as String.
     */
    public static String applyParamsToScript(String path, LoaderoTestScriptParams scriptParams) {
        Objects.requireNonNull(scriptParams, "scriptParams cannot be null");

        String result              = getScriptContent(path);
        String callDurParam        = String.valueOf(scriptParams.getCallDuration());
        String participantIdParam  = scriptParams.getParticipantId();
        String elementTimeoutParam = String.valueOf(scriptParams.getElementTimeout());
        String appUrlParam         = scriptParams.getAppUrl();

        result = replaceParamValueWithoutQuotes(result, callDurParam, REPLACE_CALL_DURATION);
        result = replaceParamValueWithoutQuotes(result, participantIdParam, REPLACE_PARTICIPANT_ID);
        result = replaceParamValueWithoutQuotes(result, elementTimeoutParam, REPLACE_ELEMENT_TIMEOUT);
        result = replaceParamValueWithQuotes(result, appUrlParam, REPLACE_APP_URL);
        return result;
    }

    private static String replaceParamValueWithoutQuotes(String value,
                                                         String newValue,
                                                         Pattern pattern) {
        return pattern.matcher(value).replaceAll("$1" + newValue + "$3");
    }

    private static String replaceParamValueWithQuotes(String value, String newValue, Pattern pattern) {
        return pattern.matcher(value).replaceAll("$1\"" + newValue + "\"$3");
    }
}
