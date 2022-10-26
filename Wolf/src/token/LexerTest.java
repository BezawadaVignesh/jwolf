package token;
import token.Lexer;

public class LexerTest {
	public static void main(String[] args) {
		Lexer l = new Lexer("a print(' Hello ') 22 + 22.55");
		Token t;
		do {
			t = l.get_nextToken();
			//if(t.type == TokenType.INT) {
			//int i = (Integer)t.value;
			System.out.println(t);//}
			//System.out.println(l.get_nextToken());
		}while(t.type != TokenType.EOF);
		
	}
}
