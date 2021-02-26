package loadero.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class responsibility is to provide simple parser to parse
 * TestUI code snippets from given path and return them as strings
 * to later pass those snippets as part of JSON request.
 */
public class FunctionBodyParser {
    private static final Logger log = LogManager.getLogger(FunctionBodyParser.class);
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
            log.error("{}", e.getMessage());
        }
        return result;
    }

    /**
     * Applies new parameters provided in Map<String, String> object to the script in .java file.
     * If some parameters are not found in Map object, default values in template will be used.
     * @param path  - Path to the script.
     * @param scriptParams - Map<String, String> object with parameters wish to apply.
     * @return  - Script content with new parameters as String.
     */
    public static String applyParamsToScript(String path, Map<String, String> scriptParams) {
        LoaderoClientUtils.checkArgumentsForNull(path, scriptParams);

        String script = getScriptContent(path);
        for (Map.Entry<String, String> param: scriptParams.entrySet()) {
            script = replaceParameters(script, param.getKey(), param.getValue());
        }
        return script;
    }

    private static String replaceParameters(String script, String replaceKey, String newValue) {
        Pattern pattern;

        if (newValue.matches("^\\d+$") ||
            newValue.contains("globalConfig")) { // Loadero specific constant
            pattern = getDigitValuePattern(replaceKey);
            return pattern.matcher(script).replaceAll("$1" + newValue + "$3");
        }
        pattern = getStringValuePattern(replaceKey);
        return pattern.matcher(script).replaceAll("$1\"" + newValue + "\"$3");
    }

    private static Pattern getDigitValuePattern(String val) {
        String pattern = String.format("(%s = )(\\d*)(;)", val);
        return Pattern.compile(pattern);
    }

    private static Pattern getStringValuePattern(String val) {
        String pattern =  String.format("(%s = )\"(.*)\"(;)", val);
        return Pattern.compile(pattern);
    }
}
