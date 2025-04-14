import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    String code = "x = 10 + 20\nif (x > 5) {\n print x\n}";

    System.out.println("Please enter some code: ");

    Scanner scan = new Scanner(System.in);
    StringBuilder builder = new StringBuilder();

    // make it stop if the user types "quit"

    while (true) {
      String line = scan.nextLine();
      if (line.equals("quit")) {
        break;
      }
      builder.append(line).append("\n");
    }
    scan.close();
    code = builder.toString();
    System.out.println("You entered: ");
    System.out.println(code);
    System.out.println("Tokenizing...");

    ExampleLexer lexer = new ExampleLexer(code);

    List<Token> tokens = lexer.tokenize();

    for (Token token : tokens) {
      System.out.println(token);
    }
  }
}
