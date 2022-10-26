package parser;
import parser.AST;
import token.Token;
import token.TokenType;
import token.Lexer;
import parser.*;

public class Parser {
	Lexer lexer;
	Token currentToken;
	public Parser(String text) {
		this.lexer = new Lexer(text);
	}
	
	public void toParseTxt(String txt) {
		this.lexer.set_text(txt);
	}
	
	void eat(TokenType type) {
		if(this.currentToken.get_type() == type) {
			System.out.println(this.currentToken);
			this.currentToken = lexer.get_nextToken();
			//System.out.println(this.currentToken);
		}
		
		else{assert false: "eat error";
		
		System.out.println(this.currentToken.get_type() +" "+ type);
		}
		
	}
	
	AST boolExpr() {
		AST node = expr();
		Token tmpToken;
		while(this.currentToken.get_type() == TokenType.LESS 
				|| this.currentToken.get_type() == TokenType.EQUALEQUAL
				|| this.currentToken.get_type() == TokenType.LESSEQUAL
				|| this.currentToken.get_type() == TokenType.GREATER 
				|| this.currentToken.get_type() == TokenType.GREATEREQUAL) {
			tmpToken = new Token(this.currentToken.get_type());
			eat(this.currentToken.get_type());
			node = new parser.BinOp(node,tmpToken, term());
		}
		return node;
	}
	
	AST expr() {
		AST node = term();
		Token tmpToken;
		while(this.currentToken.get_type() == TokenType.PLUS 
				|| this.currentToken.get_type() == TokenType.MINUS) {
			tmpToken = new Token(this.currentToken.get_type());
			eat(this.currentToken.get_type());
			node = new parser.BinOp(node,tmpToken, term());
			
		}
		return node;
	}
	AST term() {
		AST node = factor();
		Token tmpToken;
		while(this.currentToken.get_type() == TokenType.DIV 
				|| this.currentToken.get_type() == TokenType.MUL) {
			tmpToken = new Token(this.currentToken.get_type());
			eat(this.currentToken.get_type());
			node = new parser.BinOp(node,tmpToken, factor());
			
		}
		return node;
	}
	
	AST factor() {
		Token tmpToken = new Token(this.currentToken);
		AST tmp;
		switch(this.currentToken.get_type()) {			
		case INT :
			eat(TokenType.INT);
			break;
		case DOUBLE:
			eat(TokenType.DOUBLE);
			break;
		case CONSTSTR:
			eat(TokenType.CONSTSTR);
			break;
		case BOOL:
			
			eat(TokenType.BOOL);
			break;
		case VAR:
			eat(TokenType.VAR);
			break;
		case MINUS:
		case PLUS:
			eat(this.currentToken.get_type());
			tmp = expr();
			return new parser.UnaryOp(tmpToken, tmp);
		case OPENPARN:
			eat(TokenType.OPENPARN);
			tmp = expr();
			eat(TokenType.CLOSEPARN);
			return tmp;
		default:
			System.out.println("End of factor"+ this.currentToken.get_type());
			assert false;
		
		}
		return new parser.CToken(tmpToken);
	}
	
	Token peekToken() {
		int tpos = lexer.get_pos();
		Token r = lexer.get_nextToken();
		lexer.set_pos(tpos);
		return r;
	}
	
	public AST parse() {
		
		if(this.currentToken.get_type() == TokenType.VAR && peekToken().get_type() == TokenType.EQUAL) {
			String n = (String)this.currentToken.get_value();
			eat(TokenType.VAR);
			eat(TokenType.EQUAL);
			return new parser.AssignOp(n, boolExpr());
		}
		
		return boolExpr();
	}
	
	public void callFirst() {
		this.currentToken = lexer.get_nextToken();
	}
	
	public static void main(String[] args) {
		Integer i = 7;
		parser.BinOp b = new parser.BinOp(new CToken(new Token(TokenType.INT, i)),new Token(TokenType.PLUS),new CToken(new Token(TokenType.INT, i)));
		System.out.print(b);
	}
}
