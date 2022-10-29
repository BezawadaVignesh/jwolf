package parser;
import parser.AST;
import token.Token;
import token.TokenType;
import token.Lexer;
import token.LexerError;

import java.util.ArrayList;

import parser.*;

public class Parser{
	Lexer lexer;
	Token currentToken;
	public Parser(String text) {
		this.lexer = new Lexer(text);
	}
	
	public void toParseTxt(String txt) {
		this.lexer.set_text(txt);
	}
	
	Token get_nextToken() {
		return lexer.get_nextToken();
			
	}
	
	void throw_and_stop(String msg) throws ParserError {
		throw new ParserError(msg);
	}
	
	void eat(TokenType type) throws ParserError {
		if(this.currentToken.get_type() == type) {
			//System.out.println(this.currentToken);
			this.currentToken = get_nextToken();
			//System.out.println(this.currentToken);
		}
		
		else{
			throw new ParserError("EAT ERROR "+this.currentToken.get_type() +" "+ type);
		}
	}
	
	AST boolExpr(){
		AST node = expr();
		Token tmpToken;
		while(this.currentToken.get_type() == TokenType.LESS 
				|| this.currentToken.get_type() == TokenType.EQUALEQUAL
				|| this.currentToken.get_type() == TokenType.NOTEQUAL
				|| this.currentToken.get_type() == TokenType.LESSEQUAL
				|| this.currentToken.get_type() == TokenType.GREATER 
				|| this.currentToken.get_type() == TokenType.GREATEREQUAL) {
			tmpToken = new Token(this.currentToken.get_type());
			eat(this.currentToken.get_type());
			node = new parser.BinOp(node,tmpToken, expr());
		}
		//System.out.print(this.currentToken+"<<<");
		while(this.currentToken.get_type() == TokenType.AND || this.currentToken.get_type() == TokenType.OR) {
			tmpToken = new Token(this.currentToken.get_type());
			eat(this.currentToken.get_type());
			node = new parser.BinOp(node,tmpToken, boolExpr());
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
		case NOT:
		case MINUS:
		case PLUS:
			eat(this.currentToken.get_type());
			tmp = expr();
			return new parser.UnaryOp(tmpToken, tmp);
		case OPENPARN:
			eat(TokenType.OPENPARN);
			tmp = boolExpr();
			eat(TokenType.CLOSEPARN);
			return tmp;
		default:
			System.out.println("End of factor"+ this.currentToken.get_type()+peekToken());
			assert false;
		
		}
		return new parser.CToken(tmpToken);
	}
	
	Token peekToken() {
		int tpos = lexer.get_pos();
		Token r = get_nextToken();
		lexer.set_pos(tpos);
		return r;
	}
	
	void skip_newline() {
		while(this.currentToken.get_type() == TokenType.NEWLINE )eat(TokenType.NEWLINE);
	}
	
	public ArrayList<AST> getStms(){
		ArrayList<AST> tmp = new ArrayList<AST>();
		int i=0;
		//System.out.println("Entered the loop");
		while(this.currentToken.get_type() != TokenType.EOF 
				&& this.currentToken.get_type() != TokenType.CLOSEBRACE) {
			tmp.add(parse());
			if(i++>10) {System.out.println("Breaking thi loop"); break;}
			
			if(this.currentToken.get_type() == TokenType.SEMICOLON )
				eat(TokenType.SEMICOLON);
			else if(this.currentToken.get_type() == TokenType.NEWLINE ) {
				skip_newline();
			}
			else {
				System.out.println("Statement not completed get block"+tmp.get(tmp.size()-1));
			}
			skip_newline();
				
		}
		return tmp;
	}
	
	public ArrayList<AST> getBlock(){
		skip_newline();
		eat(TokenType.OPENBRACE);
		ArrayList<AST> tmp = getStms();
		eat(TokenType.CLOSEBRACE);
		return tmp;
	}
	
	public AST parse() {
		skip_newline();
		if(this.currentToken.get_type() == TokenType.VAR && peekToken().get_type() == TokenType.EQUAL) {
			String n = (String)this.currentToken.get_value();
			eat(TokenType.VAR);
			eat(TokenType.EQUAL);
			return new parser.AssignOp(n, boolExpr());
		}
		switch(this.currentToken.get_type()){
		case VAR:
		case INT:
		case DOUBLE:
			return boolExpr();
		case IF:
			eat(TokenType.IF);
			return new parser.ConBlock(OpType.IFBLOCK, boolExpr(), getBlock());
		case WHILE:
			eat(TokenType.WHILE);
			return new parser.ConBlock(OpType.WHILEBLOCK, boolExpr(), getBlock());
		}
		throw new ParserError("Unknown token(parser): "+this.currentToken);
		
	}
	
	
	public void callFirst() {
		this.currentToken = get_nextToken();
	}
	
	public static void main(String[] args) {
		Integer i = 7;
		parser.BinOp b = new parser.BinOp(new CToken(new Token(TokenType.INT, i)),new Token(TokenType.PLUS),new CToken(new Token(TokenType.INT, i)));
		System.out.print(b);
	}
}
