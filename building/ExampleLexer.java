import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

public class ExampleLexer {
  private String sourceCode;
  private int pos = 0;
  private int line = 1;
  private int column = 1;

  private static final Set<String> KEYWORDS = new HashSet<>(
      Arrays.asList("if", "else", "while", "for", "return", "print"));

  public ExampleLexer(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  private boolean isAtEnd() {
    return pos >= sourceCode.length();
  }

  /**
   * {@code peek()} -> Peeks for the next sequence of characters or "tokens"
   * If there is nothing found, or at the end then it return 0 or {empty set}
   * 
   * @return char
   **/

  private char peek() {
    if (isAtEnd())
      return '\0';
    return sourceCode.charAt(pos);
  }

  /**
   * {@code advance()} -> advances the Analyzer forward if the there are no issues
   * if there is a \n "newline" char, then it is accounted for by moving down
   * 
   * @return char
   **/

  private char advance() {
    char current = sourceCode.charAt(pos++);
    if (current == '\n') {
      line++;
      column = 1; // need to make sure that the column increases in the (x, y)
    } else {
      column++;
    }
    return current;
  }

  /**
   *
   * {@code tokenize()} -> Creating the lexemes or "tokens" that are being parsed
   * Character.isDigit(peek()) -> checks for digits
   * Character.isLetter(peek()) -> checks for letters in the alphabet
   *
   **/
  public List<Token> tokenize() {

    List<Token> tokens = new ArrayList<>();
    while (!isAtEnd()) {

      if (Character.isWhitespace(peek())) {
        advance();
        continue;
      }
      int startLine = line;
      int startColumn = column;

      if (Character.isDigit(peek())) {
        StringBuilder number = new StringBuilder();
        while (!isAtEnd() && Character.isDigit(peek())) {
          number.append(advance());
        }
        tokens.add(new Token(TokenType.NUMBER, number.toString(), startLine, startColumn));
        continue;
      }

      if (Character.isLetter(peek()) || peek() == '_') {
        StringBuilder identifier = new StringBuilder();
        while (!isAtEnd() && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
          identifier.append(advance());
        }
        String word = identifier.toString();
        if (KEYWORDS.contains(word)) {
          tokens.add(new Token(TokenType.KEYWORD, word, startLine, startColumn));
        } else {
          tokens.add(new Token(TokenType.IDENTIFIER, word, startLine, startColumn));
        }
        continue;
      }

      if ("+-*/=(){}[]<>".indexOf(peek()) != -1) {
        tokens.add(new Token(TokenType.OPERATOR, String.valueOf(advance()), startLine, startColumn));
        continue;
      }
      System.err.printf("Error: Unknown character '%c' at line %d, column %d%n", peek(), line, column);
      advance();
    }
    tokens.add(new Token(TokenType.EOF, "", line, column));
    return tokens;
  }

}
