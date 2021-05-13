package com.loadero.model;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.loadero.util.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class contains functions for parsing different script files.
 * Since Java's script is a little different and expected to be written as unit test, we have
 * to use different approach of parsing for them.
 * For Javascript/Python script's file parsing are quite trivial for now.
 */
final class ScriptBodyParser {
    private static final Logger log = LogManager.getLogger(ScriptBodyParser.class);

    /**
     * Retrieves the content of the test method from .java file annotated with @Test and with
     * provided test's method name.
     *
     * @param path     Path to the script.
     * @param testName Name of the test method.
     * @return Content of the script as String.
     */
    public static String getScriptContentJava(String path, String testName) {
        String result = "";
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(path));
            PrettyPrinterConfiguration conf = new PrettyPrinterConfiguration();
            // removing comments
            conf.setPrintComments(false);
            // preserving chaining, otherwise would be oneliner
            conf.setColumnAlignFirstMethodChain(true);
            PrettyPrinter printer = new PrettyPrinter(conf);
            result = printer.print(cu);
            result = result.substring(result.lastIndexOf("public void " + testName));
            result = result.substring(0, result.lastIndexOf('}'));
        } catch (FileNotFoundException e) {
            result = null;
            log.error("{}", e.getMessage());
        }
        return result;
    }

    /**
     * Reads file content line by line into String.
     */
    public static String getScriptContent(String pathToScript) {
        if (StringUtil.empty(pathToScript)) {
            throw new IllegalArgumentException("Path to script cannot be empty.");
        }

        Path path = Paths.get(pathToScript);
        StringBuilder builder = new StringBuilder();
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
