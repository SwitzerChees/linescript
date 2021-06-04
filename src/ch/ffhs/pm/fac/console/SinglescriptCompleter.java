package ch.ffhs.pm.fac.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;
import java.util.Map;

public class SinglescriptCompleter implements Completer {
    private final Map<String, Object> context;
   
    public SinglescriptCompleter(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        for (String var : context.keySet()) {
            if(var.startsWith(line.line())) {
                candidates.add(new Candidate(var));
            }
        }
    }
}