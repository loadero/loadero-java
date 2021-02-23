package loadero.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import java.io.File;

/**
 * This class responsibility is to provide simple parser to parse
 * TestUI code snippets from given path and return them as strings
 * to later pass those snippets as part of JSON request.
 */
public class FunctionBodyParser {
    public static String getBody(String path) {
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
            result = result.substring(result.lastIndexOf("public"));
            result = result.substring(0, result.indexOf('}')+1);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }
}
