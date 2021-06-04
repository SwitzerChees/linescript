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
    private static final List<Character> OPERATIONS;

    static {
        KEYWORDS = new ArrayList<String>();
        KEYWORDS.add("if");
        KEYWORDS.add("else");
        KEYWORDS.add("func");
        KEYWORDS.add("while");
        KEYWORDS.add("true");
        KEYWORDS.add("false");
        OPERATIONS = new ArrayList<Character>();
        OPERATIONS.add(':');
        OPERATIONS.add(',');
        OPERATIONS.add('=');
        OPERATIONS.add('!');
        OPERATIONS.add('<');
        OPERATIONS.add('>');
        OPERATIONS.add('+');
        OPERATIONS.add('-');
        OPERATIONS.add('*');
        OPERATIONS.add('/');
    }

    private static List<String> split(String buffer) {
        List<String> list = new ArrayList<String>();
        if (buffer == null || buffer.isEmpty()) {
            return list;
        }
        if (buffer.trim().startsWith("//")) {
            list.add(buffer);
        } else {
            boolean prevIsSpace = Character.isSpaceChar(buffer.charAt(0));
            int prevPos = 0;
            boolean inString = false;
            for (int i = 0; i < buffer.length(); ++i) {
                char c = buffer.charAt(i);
                if (c == '"') {
                    inString = !inString;
                }
                boolean isSpace = Character.isSpaceChar(c);
                if (!isSpace) {
                    for (Character operation : OPERATIONS) {
                        if (c == operation) {
                            isSpace = true;
                            break;
                        }
                    }
                }
                if (isSpace != prevIsSpace && (!inString || prevIsSpace)) {
                    list.add(buffer.substring(prevPos, i));
                    prevPos = i;
                    prevIsSpace = isSpace;
                }
            }
            list.add(buffer.substring(prevPos));
        }
        return list;
    }

    @Override
    public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder builder = new AttributedStringBuilder();
        List<String> tokens = split(buffer);

        for (String token : tokens) {
            if (isComment(token)) {
                builder.style(AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)).append(token);
            } else if (isKeyword(token)) {
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

    private boolean isComment(String token) {
        return token.trim().startsWith("//");
    }

    private boolean isKeyword(String token) {
        for (String keyword : KEYWORDS) {
            if (keyword.compareToIgnoreCase(token) == 0)
                return true;
        }
        return false;
    }

    private boolean isString(String token) {
        if (token.matches("\"[a-zA-z0-9\\s]*\"")) {
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