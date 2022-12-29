package wolfObj;

import token.TokenType;
//import token.WolfObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parser.AST;
import parser.ParserError;

public class WolfObj {
	private TokenType type;
	private Object value;
	private WolfObj parent;
	protected HashMap<String, WolfObj> properties = new HashMap<String, WolfObj>(); 
	
	public WolfObj(TokenType t, Object value){
		this.setType(t);
		this.setValue(value);
		init(t);
	}
	public WolfObj(TokenType t, Object value, boolean Init){
		this.setType(t);
		this.setValue(value);
		if(Init)init(t);
	}
	public WolfObj(TokenType t){
		this.setType(t);
		init(t);
	}
	
	void init(TokenType t) {
		//this.properties.put("__name__", new WString(t.name()));
		//this.properties.put("__str__", new WString());
	}
	
	public WolfObj callPropertie(AST a) {
		return null;
	}
	public Map<String, WolfObj> getProperties() {
		return (Map<String, WolfObj>)this.properties;
	}
	
	public void setProperties(HashMap<String, WolfObj> obj) {
		this.properties = obj;
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
	public WolfObj modlus(WolfObj obj) {
		throw new ParserError("Cannot modlus '"+ this.getType()+"' '"+obj.getType()+"' error..");
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
		if(this.getType()==obj.getType()) return new WBool(this.getValue() == obj.getValue());
		throw new ParserError("Cannot comEqual '"+ this.getType()+"' '"+obj.getType()+"' error..");
	}
	
	public WolfObj not() {
		throw new ParserError("Cannot not and error..");
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
	
	public void assignTo(WolfObj index, WolfObj newObj) {
		throw new parser.ParserError("Cannot unpack obj of type "+this.type);
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
