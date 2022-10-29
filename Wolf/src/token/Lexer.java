package token;
import token.Token;
import token.TokenType;

public class Lexer {
	private String text;
	private int pos;
	
	private int length;
	
	Lexer(){}
	
	public Lexer(String text){
		this.text = text;
		this.pos = 0;
		this.length = text.length();
	}
	
	public Lexer(String text, int pos){
		this.text = text;
		this.pos = pos;
		this.length = text.length();
	}
	
	public void set_text(String text) {
		this.text = text;
		this.pos = 0;
		this.length = text.length();
	}
	
	public int get_pos() {
		return this.pos;
	}
	
	public void set_pos(int pos) {
		this.pos = pos;
	}
	
	Token _constString(char startWith) {
		
		if(this.text.charAt(this.pos) == startWith) this.pos ++;
		else assert false;
		int fromPos=this.pos;
		while(this.pos < this.length && 
				this.text.charAt(this.pos) != startWith) {
			this.pos++;
		}
		if(this.text.charAt(this.pos) == startWith) this.pos ++;
		else assert false;
		return new Token(TokenType.CONSTSTR, (String)this.text.substring(fromPos, this.pos-1));
	}
	
	Token _id() {
		int fromPos=this.pos;
		while(this.pos < this.length && 
				(Character.isAlphabetic(this.text.charAt(this.pos)) || 
				(this.text.charAt(this.pos) == '_'))) {
			this.pos++;
		}
		String name = this.text.substring(fromPos, this.pos);
		
		if(name.equals("if"))
			return new Token(TokenType.IF);
		else if(name.equals("and"))
			return new Token(TokenType.AND);
		else if(name.equals("or"))
			return new Token(TokenType.OR);
		else if(name.equalsIgnoreCase("True"))
			return new Token(TokenType.BOOL, true);
		else if(name.equalsIgnoreCase("False"))
			return new Token(TokenType.BOOL, false);
		else if(name.equals("while"))
			return new Token(TokenType.WHILE);
		return new Token(TokenType.VAR, (String)name);
	}
	
	Token _number() {
		boolean pointNotFound = true;
		int fromPos=this.pos;
		while(this.pos < this.length && 
				(Character.isDigit(this.text.charAt(this.pos)) || 
				(this.text.charAt(this.pos) == '.' && pointNotFound))) {
			if(this.text.charAt(this.pos) == '.') pointNotFound = false;
			this.pos++;
		}
		if(pointNotFound) 
			return new Token(TokenType.INT, Integer.parseInt(this.text.substring(fromPos, this.pos)));
		else 
			return new Token(TokenType.DOUBLE, Double.parseDouble(this.text.substring(fromPos, this.pos)));
		
		
	}
	
	public char peek() {
		if(this.pos+1>=this.length) return '\0';
		return (this.text.charAt(this.pos+1));
	}
	
	public Token get_nextToken() throws LexerError {
		char currentCh; 
		while (this.pos < this.length) {
			currentCh = this.text.charAt(this.pos);
			if(currentCh == '\n') {
				pos++;
				return new Token(TokenType.NEWLINE);
			}
			if(Character.isWhitespace(currentCh)) {
				pos++;
				continue;
			}
			if(Character.isDigit(currentCh)) {
				return _number();
			}
			switch(this.text.charAt(this.pos)) {
				case '+':
					this.pos++;
					return new Token(TokenType.PLUS);
				case '-':
					this.pos++;
					return new Token(TokenType.MINUS);
				case '*':
					this.pos++;
					return new Token(TokenType.MUL);
				case '/':
					this.pos++;
					return new Token(TokenType.DIV);
				case '(':
					this.pos++;
					return new Token(TokenType.OPENPARN);
				case ')':
					this.pos++;
					return new Token(TokenType.CLOSEPARN);
				case ';':
					this.pos++;
					return new Token(TokenType.SEMICOLON);
				case ':':
					this.pos++;
					return new Token(TokenType.COLON);
				case '{':
					this.pos++;
					return new Token(TokenType.OPENBRACE);
				case '}':
					this.pos++;
					return new Token(TokenType.CLOSEBRACE);
				case '=':
					
					if(peek() == '=') {
						this.pos+=2;
						return new Token(TokenType.EQUALEQUAL);
					}
					this.pos++;
					return new Token(TokenType.EQUAL);
				case '!':
					
					if(peek() == '=') {
						this.pos+=2;
						return new Token(TokenType.NOTEQUAL);
					}
					this.pos++;
					return new Token(TokenType.NOT);
				case '<':
					
					if(peek() == '=') {
						this.pos+=2;
						return new Token(TokenType.LESSEQUAL);
					}this.pos++;
					return new Token(TokenType.LESS);
				case '>':
					if(peek() == '=') {
						this.pos+=2;
						return new Token(TokenType.GREATEREQUAL);
					}this.pos++;
					return new Token(TokenType.GREATER);
				case '"':
					this.pos++;
					return _constString('"');
				case '\'':
					this.pos++;
					return _constString('\'');
			}
			
			if(Character.isAlphabetic(this.text.charAt(this.pos))) {
				return _id();
			}
			
			throw new LexerError("Unknown token "+currentCh);
			//System.out.println("Reached end of lexer returning EOF");
			//return new Token(TokenType.EOF, "EOF");
			
		}
		return new Token(TokenType.EOF, "EOF");
	}
	
	public static void main(String[] args) {
		Token t = new Token(TokenType.VAR, "i");
		String s = "234  23.4";
		
		System.out.println(s);

	}

}
