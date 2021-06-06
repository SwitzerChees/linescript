package ch.ffhs.pm.fac;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java_cup.runtime.Symbol;
import ch.ffhs.pm.fac.console.LinescriptCompleter;
import ch.ffhs.pm.fac.console.LinescriptHighlighter;
import ch.ffhs.pm.fac.instr.Evaluator;
import ch.ffhs.pm.fac.instr.Instruction;
import ch.ffhs.pm.fac.instr.Validator;
import ch.ffhs.pm.fac.parser.Parser;
import ch.ffhs.pm.fac.parser.Scanner;

import org.jline.reader.*;

/**
 * Interactive Interpreter as console applikation.
 * 
 * @author Patrick Michel
 */
public class Linescript {
    public static void main(String[] args) {
        try {
            Map<String, Object> context = new HashMap<String, Object>();
            Validator validator = new Validator();
            LineReader reader = LineReaderBuilder.builder().highlighter(new LinescriptHighlighter())
                    .completer(new LinescriptCompleter(context)).build();
            System.out.println("Linescript 1.0.0");
            System.out.println("Use <tab> to use autocomplete for variables & functions");
            System.out.println("Type 'exit()' to close the console");
            System.out.println("©2021 SwitzerChees. Made with a lot of ☕");
            boolean isInit = true;
            String script = Files.readString(Path.of("init.ls"));
            for (;;) {
                try {
                    if (isInit) {
                        isInit = false;
                    } else {
                        script = reader.readLine("> ");
                        script += '\n';
                    }
                    if (script.trim().length() == 0) {
                        continue;
                    } 
                    Parser parser = new Parser(new Scanner(new StringReader(script)));
                    Symbol symbol = parser.parse();
                    Instruction instr = (Instruction) symbol.value;
                    validator.cleanup();
                    instr.acceptVisitor(validator);
                    if (!validator.getUndefinedVariables().isEmpty()) {
                        System.out.println(
                                "Error: Undefined variables: " + setToString(validator.getUndefinedVariables()));
                    } else {
                        Evaluator evaluator = new Evaluator(context);
                        Object result = instr.acceptVisitor(evaluator);
                        if (result != null) {
                            if (result instanceof ArrayList<?>) {
                                ArrayList<?> results = (ArrayList<?>) result;
                                for (Object res : results) {
                                    System.out.println(res);
                                }
                            } else {
                                System.out.println(result);
                            }
                        }
                    }
                } catch (UserInterruptException ex) {
                    continue;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String setToString(Set<String> set) {
        return set.toString();
    }
}
