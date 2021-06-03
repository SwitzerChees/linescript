package ch.ffhs.pm.fac;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java_cup.runtime.Symbol;
import ch.ffhs.pm.fac.instr.Evaluator;
import ch.ffhs.pm.fac.instr.Instruction;
import ch.ffhs.pm.fac.instr.Validator;
import ch.ffhs.pm.fac.parser.Parser;
import ch.ffhs.pm.fac.parser.Scanner;

/**
 * Interaktiver Interpreter als Konsolen-Applikation. 
 * Ein Skript-Teil wird jeweils nach Eingabe einer Leerzeile ausgeführt.
 *  
 * @author urs-martin
 */
public class SkriptRunner
{
    public static void main(String[] args)
    {
        Map<String,Object> context = new HashMap<String,Object>();
        for (;;)
        {
            try
            {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                for (;;)
                {
                    System.out.print("> ");
                    String line = br.readLine();
                    if (line.trim().length() == 0) break;
                    sb.append(line);
                    sb.append('\n');
                }
                String script = sb.toString();
                if (script.trim().length() == 0)
                {
                    continue;
                }
                else if (script.trim().equals("exit()")) {
                    System.out.println("Bye! 👋");
                    return;
                }
                Parser parser = new Parser(new Scanner(new StringReader(script)));
                Symbol symbol = parser.parse();
                Instruction instr = (Instruction) symbol.value;
                Validator validator = new Validator();
                instr.acceptVisitor(validator);
                if (! validator.getUnusedVariables().isEmpty())
                {
                    System.out.println("Warning: Unused variables: " +
                            setToString(validator.getUnusedVariables()));
                }
                if (! validator.getUndefinedVariables().isEmpty())
                {
                    System.out.println("Error: Undefined variables: " + 
                            setToString(validator.getUndefinedVariables()));
                }
                else
                {
                    Evaluator evaluator = new Evaluator(context);
                    Object result = instr.acceptVisitor(evaluator);
                    System.out.println("---> " + result);
                }
            }
            catch (Exception ex)
            {
                //System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    private static String setToString(Set<String> set)
    {
        return set.toString();
    }
}
