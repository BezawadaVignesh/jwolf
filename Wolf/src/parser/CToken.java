package parser;
import token.Token;
import parser.AST;
import parser.OpType;

public class CToken extends AST{
	public Token token;
	public CToken(Token token){
		super(OpType.CTOKEN);
		this.token = token;
	}
	public String toString() {
		return this.token.toString();
	}
}
