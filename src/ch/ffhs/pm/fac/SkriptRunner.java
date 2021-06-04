package ch.ffhs.pm.fac;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java_cup.runtime.Symbol;
import ch.ffhs.pm.fac.console.SinglescriptCompleter;
import ch.ffhs.pm.fac.console.SinglescriptHighlighter;
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
public class SkriptRunner {
    public static void main(String[] args) {
        try {
            Map<String, Object> context = new HashMap<String, Object>();
            Validator validator = new Validator();
            LineReader reader = LineReaderBuilder.builder().highlighter(new SinglescriptHighlighter())
                    .completer(new SinglescriptCompleter(context)).build();
            for (;;) {
                try {
                    String script = reader.readLine("> ");
                    script += '\n';
                    if (script.trim().length() == 0) {
                        continue;
                    } else if (script.trim().equals("exit()")) {
                        System.out.println("Bye! 👋");
                        return;
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
