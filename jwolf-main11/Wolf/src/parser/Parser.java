package parser;
import parser.AST;
import token.Token;
import token.TokenType;
import token.Lexer;
import token.LexerError;

import java.util.ArrayList;
import java.util.Map;

import parser.*;

public class Parser{
	Lexer lexer;
	private Token currentToken;
	public Parser(String text) {
		this.lexer = new Lexer(text);
	}
	
	public Token getCurrentToken() {
		return this.currentToken;
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
			throw new ParserError("EAT ERROR got:"+this.currentToken +" expected:"+ type);
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
				|| this.currentToken.get_type() == TokenType.MUL
				|| this.currentToken.get_type() == TokenType.MODLUS) {
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
			return factor_string();
		case OPENBRACE:
			ArrayList<AST> keys = new ArrayList<AST>(),values = new ArrayList<AST>();
			eat(TokenType.OPENBRACE);skip_newline();
			while(this.currentToken.get_type() != TokenType.EOF 
					&& this.currentToken.get_type() != TokenType.CLOSEBRACE) {
				skip_newline();
				keys.add(boolExpr());skip_newline();
				eat(TokenType.COLON);skip_newline();
				values.add(boolExpr());skip_newline();
				if(this.currentToken.get_type() == TokenType.COMMA)
				eat(TokenType.COMMA);
				else break;
			}
			
			eat(TokenType.CLOSEBRACE);
			return new CToken(TokenType.DICT, Map.entry(keys, values));
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
		case OPENBRACKET:
			eat(TokenType.OPENBRACKET);
			ArrayList<AST> tmpArgs = getCommaSepExpr();
			eat(TokenType.CLOSEBRACKET);
			return new CToken( TokenType.LIST, tmpArgs);
		default:
			System.out.println("End of factor currentToken : "+ this.currentToken.get_type()+peekToken());
			assert false;
		
		}
		return new parser.CToken(tmpToken);
	}
	
	AST factor_string() {
		AST node = deepFactorString();
		Token tmpToken;
		
		/*while(this.currentToken.get_type() == TokenType.PERIOD) {
			
		}*/
		while(this.currentToken.get_type() == TokenType.PERIOD) {
				tmpToken = new Token(this.currentToken);
				eat(TokenType.PERIOD);
				AST tmpv = deepFactorString();
				//System.out.println(tmpv);
				node = new parser.BinOp(node, tmpToken, tmpv);
			}
			
		return node;
	}
	
	AST deepFactorString() {
		AST node = new parser.CToken(this.currentToken);
		eat(TokenType.VAR);
		Token tmpToken;
		while(this.currentToken.get_type() == TokenType.OPENPARN 
				|| this.currentToken.get_type() == TokenType.OPENBRACKET ) {
			switch(this.currentToken.get_type()) {
				case OPENPARN:
					eat(TokenType.OPENPARN);
					ArrayList<AST> tmpArgs = getArgs();
					eat(TokenType.CLOSEPARN);
					node = new parser.FuncCall(node, tmpArgs);
					break;
				case OPENBRACKET:
					AST tmpindex;
					//while(this.currentToken.get_type() == TokenType.OPENBRACKET) {
						eat(TokenType.OPENBRACKET);
						tmpindex = boolExpr();
						eat(TokenType.CLOSEBRACKET);
					
					node = new parser.UnpackOp(node, tmpindex);
					break;
			}
		}
			return node;
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
			if(i++>10) {System.out.println("Breaking this loop"); break;}
			
			if(this.currentToken.get_type() == TokenType.SEMICOLON)
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
	// can detect mult expr separated by commas(expr, expr,...)
	ArrayList<AST> getCommaSepExpr(){
		ArrayList<AST> args = new ArrayList<AST>();
		skip_newline();
		while(this.currentToken.get_type() != TokenType.CLOSEPARN) {
			skip_newline();
			args.add(boolExpr());
			//args.add(parse());
			if(this.currentToken.get_type() == TokenType.COMMA)
				eat(TokenType.COMMA);
			else break;
		}
		return args;
	}
	// can detect VAR and VAR[] (subscripts) used in assig op
	ArrayList<AST> getVarNames(){
		ArrayList<AST> args = new ArrayList<AST>();
		AST tmpindex;
		while(this.currentToken.get_type() == TokenType.VAR) {
			tmpindex = new parser.CToken(new Token(TokenType.VAR, this.currentToken.get_value()));
			eat(TokenType.VAR);
			if(this.currentToken.get_type() == TokenType.OPENBRACKET) {
				eat(TokenType.OPENBRACKET);
				tmpindex = new parser.UnpackOp(tmpindex, boolExpr());
				eat(TokenType.CLOSEBRACKET);
			}
			args.add(tmpindex);
			if(this.currentToken.get_type() == TokenType.COMMA) {
				eat(TokenType.COMMA);
			}else break;
		}
		return args;
	}
	
	ArrayList<AST> getArgs(){
		ArrayList<AST> args = new ArrayList<AST>();
		AST tmpindex;
		while(this.currentToken.get_type() != TokenType.CLOSEPARN && this.currentToken.get_type() != TokenType.EOF) {
			if(this.currentToken.get_type() == TokenType.VAR && peekToken().get_type() == TokenType.EQUAL) {
				tmpindex = new parser.CToken(new Token(TokenType.VAR, this.currentToken.get_value()));
				eat(TokenType.VAR);
				if(this.currentToken.get_type() == TokenType.EQUAL) {
					eat(TokenType.EQUAL);
					ArrayList<AST> tmpVarName = new ArrayList<AST>(), tmpArgs = new ArrayList<AST>();
					tmpVarName.add(tmpindex);
					tmpArgs.add(boolExpr());
					tmpindex = new parser.AssignOp(tmpVarName,new CToken( TokenType.PACKED, tmpArgs));
				}
				args.add(tmpindex);
			}
			else {
				args.add(boolExpr());
			}
			if(this.currentToken.get_type() == TokenType.COMMA) {
				eat(TokenType.COMMA);
			}else break;
		}
		return args;
	}
	
	ArrayList<AST> getArgsNames(){
		ArrayList<AST> args = new ArrayList<AST>();
		AST tmpindex;
		while(this.currentToken.get_type() == TokenType.VAR) {
			tmpindex = new parser.CToken(new Token(TokenType.VAR, this.currentToken.get_value()));
			eat(TokenType.VAR);
			if(this.currentToken.get_type() == TokenType.EQUAL) {
				eat(TokenType.EQUAL);
				ArrayList<AST> tmpVarName = new ArrayList<AST>(), tmpArgs = new ArrayList<AST>();
				tmpVarName.add(tmpindex);
				tmpArgs.add(boolExpr());
				tmpindex = new parser.AssignOp(tmpVarName,new CToken( TokenType.PACKED, tmpArgs));
			}
			args.add(tmpindex);
			if(this.currentToken.get_type() == TokenType.COMMA) {
				eat(TokenType.COMMA);
			}else break;
		}
		return args;
	}
	
	public AST parse() {
		skip_newline();
		if(this.currentToken.get_type() == TokenType.VAR 
				&& (peekToken().get_type() == TokenType.EQUAL 
				|| peekToken().get_type() == TokenType.COMMA
				|| peekToken().get_type() == TokenType.OPENBRACKET)) {
			ArrayList<AST> n = getVarNames();
			eat(TokenType.EQUAL);
			return new parser.AssignOp(n, new CToken( TokenType.PACKED, getCommaSepExpr()));
		}
		
		switch(this.currentToken.get_type()){
		case VAR:
		case INT:
		case DOUBLE:
		case CONSTSTR:
			return boolExpr();
		case IF:
			eat(TokenType.IF);
			return new parser.ConBlock(OpType.IFBLOCK, boolExpr(), getBlock());
		case WHILE:
			eat(TokenType.WHILE);
			return new parser.ConBlock(OpType.WHILEBLOCK, boolExpr(), getBlock());
		case OPENBRACKET:
			eat(TokenType.OPENBRACKET);
			ArrayList<AST> tmpArgs = getCommaSepExpr();
			eat(TokenType.CLOSEBRACKET);
			return new CToken( TokenType.LIST, tmpArgs);
		case CLASS:
			eat(TokenType.CLASS);
			if(this.currentToken.get_type() != TokenType.VAR)
				break;
			String name1 = (String) this.currentToken.get_value();
			
			eat(TokenType.VAR);/*
			eat(TokenType.OPENPARN);
			ArrayList<AST> args1 = getCommaSepNames();
			eat(TokenType.CLOSEPARN);*/
			parser.AST tmp = new parser.FuncDecl(name1, new ArrayList<AST>(), getBlock());
			tmp.type = OpType.CLASSDECL;
			return tmp;
		case FUNC:
			eat(TokenType.FUNC);
			if(this.currentToken.get_type() != TokenType.VAR)
				break;
			String name = (String) this.currentToken.get_value();
			
			eat(TokenType.VAR);
			eat(TokenType.OPENPARN);
			ArrayList<AST> args = getArgsNames();
			eat(TokenType.CLOSEPARN);
			return new parser.FuncDecl(name, args, getBlock());
		case RETURN:
			eat(TokenType.RETURN);
			return new parser.ReturnStmt(getCommaSepExpr());
		}
		throw new ParserError("Unknown token(parser): "+this.currentToken);
		
	}
	
	
	public void callFirst() {
		this.currentToken = get_nextToken();
	}
	
}