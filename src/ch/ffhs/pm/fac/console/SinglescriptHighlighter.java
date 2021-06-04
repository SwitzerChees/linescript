package ch.ffhs.pm.fac.console;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.List;

public class SinglescriptHighlighter implements Highlighter {
    private static final List<String> KEYWORDS;

    static {
        KEYWORDS = new ArrayList<String>();
        KEYWORDS.add("if");
        KEYWORDS.add("else");
        KEYWORDS.add("func");
        KEYWORDS.add("while");
    }

    private static List<String> splitWithSpace(String buffer) {
        List<String> list = new ArrayList<String>();
        if (buffer == null || buffer.isEmpty()) {
            return list;
        }

        boolean prevIsSpace = Character.isSpaceChar(buffer.charAt(0));
        int prevPos = 0;
        for (int i = 1; i < buffer.length(); ++i) {
            char c = buffer.charAt(i);
            boolean isSpace = Character.isSpaceChar(c);
            if (isSpace != prevIsSpace) {
                list.add(buffer.substring(prevPos, i));
                prevPos = i;
                prevIsSpace = isSpace;
            }
        }
        list.add(buffer.substring(prevPos));
        return list;
    }

    @Override
    public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder builder = new AttributedStringBuilder();
        List<String> tokens = splitWithSpace(buffer);

        for (String token : tokens) {
            if (isKeyword(token)) {
                builder.style(AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)).append(token);
            } else if (isString(token)) {
                builder.style(AttributedStyle.BOLD.foreground(AttributedStyle.MAGENTA)).append(token);
            } else if (isNumber(token)) {
                builder.style(AttributedStyle.BOLD.foreground(AttributedStyle.CYAN)).append(token);
            } else {
                builder.style(AttributedStyle.DEFAULT).append(token);
            }
        }

        return builder.toAttributedString();
    }

    private boolean isKeyword(String token) {
        for (String keyword : KEYWORDS) {
            if (keyword.compareToIgnoreCase(token) == 0)
                return true;
        }
        return false;
    }

    private boolean isString(String token) {
        if (token.length() > 1 && token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {
            return true;
        }
        return false;
    }

    private boolean isNumber(String token) {
        if (token.matches("\\+?-?[0-9]+")) {
            return true;
        }
        if (token.matches("\\+?-?[0-9]+\\.")) {
            return true;
        }
        if (token.matches("\\+?-?[0-9]+\\.[0-9]+")) {
            return true;
        }
        return false;
    }
}