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
	
	@Override
	public boolean equals(Object o) {
		try {
			AST a = (AST)o;
			if(a.type == OpType.CTOKEN) {
				return this.token.equals(((CToken)a).token);
			}
			if(a.type == OpType.ASSIGNOP) {
				return this.equals(((parser.AssignOp)a).get_name().get(0));
			}
		}catch(Exception e) {
			
		}
		System.out.println("Should not reach in CToken");
		return super.equals(o);
	}
}
