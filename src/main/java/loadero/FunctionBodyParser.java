package loadero;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

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
            LexicalPreservingPrinter.setup(cu);
            result = LexicalPreservingPrinter.print(cu);
            result = result.substring(result.lastIndexOf("public"));
            result = result.substring(0, result.indexOf('}')+1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
