package loadero;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.File;

public class TryParser {
    private static final String path = "src/test/java/TestParser.java";
    public static void main(String[] args) {
        try {
            CompilationUnit cu =  StaticJavaParser.parse(new File(path));
            LexicalPreservingPrinter.setup(cu);
            String test = LexicalPreservingPrinter.print(cu);
            test = test.substring(test.lastIndexOf("public"));
            test = test.substring(0, test.indexOf('}')+1);
            System.out.println(test);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
