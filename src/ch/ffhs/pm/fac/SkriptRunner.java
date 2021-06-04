package ch.ffhs.pm.fac;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java_cup.runtime.Symbol;
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
            LineReader reader = LineReaderBuilder.builder()
            .highlighter(new SinglescriptHighlighter()).build();
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
                    instr.acceptVisitor(validator);
                    if (!validator.getUnusedVariables().isEmpty()) {
                        System.out.println("Warning: Unused variables: " + setToString(validator.getUnusedVariables()));
                    }
                    if (!validator.getUndefinedVariables().isEmpty()) {
                        System.out.println(
                                "Error: Undefined variables: " + setToString(validator.getUndefinedVariables()));
                    } else {
                        Evaluator evaluator = new Evaluator(context);
                        Object result = instr.acceptVisitor(evaluator);
                        System.out.println(result);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String setToString(Set<String> set) {
        return set.toString();
    }
}
