package token;

import token.TokenType; 

public class Token {
	public TokenType type;
	private Object value;
	
	public Token(TokenType type, Object value){
		this.type = type;
		this.set_value(value);
	}
	public Token(TokenType type){
		this.type = type;
		this.set_value(null);
	}
	
	public Token(Token t) {
		this.type = t.type;
		this.set_value(t.get_value());
	}
	public TokenType get_type() {
		return this.type;
	}
	
	public String toString() {
		return "Token( "+this.type+", "+this.get_value()+" )";
	}
	public Object get_value() {
		return value;
	}
	public void set_value(Object value) {
		this.value = value;
	}
	
	public Token add(Token t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new Token(TokenType.INT, (Integer)this.value + (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new Token(TokenType.DOUBLE, (Double)this.value + (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new Token(TokenType.NONE);
		}
	}
	
	public Token sub(Token t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new Token(TokenType.INT, (Integer)this.value - (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new Token(TokenType.DOUBLE, (Double)this.value - (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new Token(TokenType.NONE);
		}
	}
	
	public Token mul(Token t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new Token(TokenType.INT, (Integer)this.value * (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new Token(TokenType.DOUBLE, (Double)this.value * (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new Token(TokenType.NONE);
		}
	}
	public Token div(Token t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new Token(TokenType.INT, (Integer)this.value / (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new Token(TokenType.DOUBLE, (Double)this.value / (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new Token(TokenType.NONE);
		}
	}
}
