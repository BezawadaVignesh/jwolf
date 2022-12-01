package wolfObj;

import token.TokenType;

import java.util.ArrayList;

import parser.ParserError;

public class WolfObj {
	private TokenType type;
	private Object value;
	public WolfObj(TokenType t, Object value){
		this.setType(t);
		this.setValue(value);
	}
	public WolfObj(TokenType t){
		this.setType(t);
	}
	
	public WolfObj add(WolfObj obj) {
		throw new ParserError("Cannot add '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	public WolfObj sub(WolfObj obj) {
		throw new ParserError("Cannot sub '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	public WolfObj mul(WolfObj obj) {
		throw new ParserError("Cannot mul '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	public WolfObj div(WolfObj obj) {
		throw new ParserError("Cannot div '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	public WolfObj and(WolfObj obj) {
		throw new ParserError("Cannot and '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	
	public WolfObj comLess(WolfObj obj) {
		throw new ParserError("Cannot comLess '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	
	public WolfObj comLessEqual(WolfObj obj) {
		throw new ParserError("Cannot comLessEqual '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	
	public WolfObj comEqual(WolfObj obj) {
		throw new ParserError("Cannot comEqual '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	
	public WolfObj not() {
		throw new ParserError("Cannot and error..");
	}
	
	public WolfObj or(WolfObj obj) {
		throw new ParserError("Cannot or '"+ this.getType()+"' '"+obj.getType()+"' error..");
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
	
	public WolfObj unpack(WolfObj index) {
		
		throw new parser.ParserError("Cannot unpack obj of type "+this.type);
	}
	public WolfObj unpackByInt(int index) {
		throw new parser.ParserError("Cannot unpack obj of type "+this.type);
	}

	public void setType(TokenType type) {
		this.type = type;
	}
	public String toString() {
		return ""+this.getValue();
	}
}
