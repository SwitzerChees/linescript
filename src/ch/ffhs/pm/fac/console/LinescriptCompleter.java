package ch.ffhs.pm.fac.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import ch.ffhs.pm.fac.instr.InstructionFuncStatement;

import java.util.List;
import java.util.Map;

public class LinescriptCompleter implements Completer {
    private final Map<String, Object> context;
   
    public LinescriptCompleter(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        for (String var : context.keySet()) {
            int wordStart = line.cursor() - line.wordCursor();
            if(var.startsWith(line.line().substring(wordStart, wordStart + line.word().length()))) {
                String candidateGroup = "Variables";
                String candidateValue = null;
                Object value = context.get(var);
                if (value instanceof InstructionFuncStatement) {
                    InstructionFuncStatement funcStatement = (InstructionFuncStatement)value;
                    candidateValue = funcStatement.getParameters();
                    candidateGroup = "Functions";
                } else {
                    candidateValue = context.get(var).toString();
                }
                candidates.add(new Candidate(var, var, candidateGroup, candidateValue, null, null, false));
            }
        }
    }
}