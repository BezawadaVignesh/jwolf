package parser;
import token.Token;
import token.TokenType;
import parser.AST;
import parser.OpType;

public class CToken extends AST{
	public Token token;
	public CToken(Token token){
		super(OpType.CTOKEN);
		this.token = token;
	}
	public CToken(TokenType tokentype, Object obj) {
		super(OpType.CTOKEN);
		this.token = new Token(tokentype, obj);
	}
	public String toString() {
		return this.token.toString();
	}
}
