package wolfObj;

import token.TokenType;
import parser.ParserError;

public class WolfObj {
	private TokenType type;
	private Object value;
	WolfObj(TokenType t, Object value){
		this.setType(t);
		this.setValue(value);
	}
	WolfObj(TokenType t){
		this.setType(t);
	}
	
	WolfObj add(WolfObj obj) {
		throw new ParserError("Cannot add error..");
	}
	WolfObj sub(WolfObj obj) {
		throw new ParserError("Cannot sub error..");
	}
	WolfObj mul(WolfObj obj) {
		throw new ParserError("Cannot mul error..");
	}
	WolfObj div(WolfObj obj) {
		throw new ParserError("Cannot div error..");
	}
	WolfObj and(WolfObj obj) {
		throw new ParserError("Cannot and error..");
	}
	
	WolfObj comLess(WolfObj obj) {
		throw new ParserError("Cannot and error..");
	}
	
	WolfObj comLessEqual(WolfObj obj) {
		throw new ParserError("Cannot and error..");
	}
	
	WolfObj comEqual(WolfObj obj) {
		throw new ParserError("Cannot and error..");
	}
	
	WolfObj not() {
		throw new ParserError("Cannot and error..");
	}
	
	WolfObj or(WolfObj obj) {
		throw new ParserError("Cannot and error..");
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}
	public String toString() {
		return ""+this.getValue();
	}
}
