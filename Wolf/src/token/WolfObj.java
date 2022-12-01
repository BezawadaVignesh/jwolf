package token;

import token.TokenType; 
import java.util.ArrayList;
import java.util.HashMap;


public class WolfObj {
	private TokenType type;
	private Object value;
	private HashMap<String, WolfObj> properties; 
	
	
	public WolfObj(TokenType type, Object value){
		this.type = type;
		this.setValue(value);
	}
	public WolfObj(TokenType type, Object value, String name){
		this.type = type;
		this.setValue(value);
		this.type.set_typeValue(name);
	}
	public WolfObj(TokenType type){
		this.setType(type);
		
		this.setValue(null);
	}
	
	
	public WolfObj(WolfObj t) {
		this.type = t.type;
		this.setValue(t.getValue());
	}
	public TokenType getType() {
		return this.type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	public String toString() {
		return ""+this.getValue();//"WolfObj( "+this.type+", "+this.get_value()+" )";
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public WolfObj getProperties(WolfObj name) {
		WolfObj tmpObj = null;
		if(name.getType() == TokenType.CONSTSTR)
			tmpObj = properties.get((String)name.getValue());
		if(tmpObj != null) return tmpObj;
		throw new parser.ParserError("No attribute name ");
	}
	public void setProperties(HashMap<String, WolfObj> obj) {
		this.properties = obj;
	}
	public WolfObj unpack(WolfObj index) {
		if(this.type == TokenType.LIST || this.type == TokenType.TUPLE || this.type == TokenType.PACKED)
		return (WolfObj)((ArrayList<WolfObj>)this.value).get((int)index.value);
		throw new parser.ParserError("Cannot unpack obj of type "+this.type);
	}
	public WolfObj unpackByInt(int index) {
		if(this.type == TokenType.LIST || this.type == TokenType.TUPLE || this.type == TokenType.PACKED)
		return (WolfObj)((ArrayList<WolfObj>)this.value).get(index);
		throw new parser.ParserError("Cannot unpack obj of type "+this.type);
	}
	
	public WolfObj add(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.INT, (Integer)this.value + (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Integer)this.value + (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value + (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value + (Double)t.getValue());
			
		}
		else if(this.type == TokenType.CONSTSTR && t.getType() == TokenType.CONSTSTR)
			return new WolfObj(TokenType.CONSTSTR, (String)this.value + (String)t.getValue());
		else if(this.type == TokenType.LIST && t.getType() == TokenType.LIST) {
			ArrayList<WolfObj> tmpObj = (ArrayList<WolfObj>) ((ArrayList<WolfObj>)this.value).clone();
			tmpObj.addAll((ArrayList<WolfObj>)t.getValue());
			return new WolfObj(TokenType.LIST, tmpObj);
		}
			throw new parser.ParserError("addition not possible");
		
	}
	
	public WolfObj comLess(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Integer)this.value < (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Integer)this.value < (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value < (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Double)this.value < (Double)t.getValue());
			
		}
		throw new parser.ParserError("Cannot compare obj of type "+this.type +" and "+t.getType());
	}
	
	public WolfObj comLessEqual(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Integer)this.value <= (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Integer)this.value <= (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value <= (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Double)this.value <= (Double)t.getValue());
			
		}
		throw new parser.ParserError("Cannot compare obj of type "+this.type +" and "+t.getType());
	}
	
	public WolfObj comEqual(WolfObj t) {
		if(this.type == TokenType.INT && t.getType() == TokenType.DOUBLE) 
				return new WolfObj(TokenType.BOOL, Double.valueOf((Integer)this.value) == (Double)t.getValue());
		else if(this.type == TokenType.DOUBLE && t.getType() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value == Double.valueOf((Integer)t.getValue()));
		else if(this.type == t.getType()) {
			switch(this.type) {
			case INT:
				return new WolfObj(TokenType.BOOL, (Integer)this.value == (Integer)t.getValue());
			case DOUBLE:
				return new WolfObj(TokenType.BOOL, (Double)this.value == (Double)t.getValue());
			case CONSTSTR:
				return new WolfObj(TokenType.BOOL, ((String)this.value).equals((String)t.getValue()));
			case BOOL:
				return new WolfObj(TokenType.BOOL, this.value == t.getValue());
			case TYPE:
				return new WolfObj(TokenType.BOOL, this.getType().get_typeValue().equals(t.getType().get_typeValue()));
			}
		}
		
		throw new parser.ParserError("Cannot compare obj of type "+this.type +" and "+t.getType());	
	}
	
	public WolfObj sub(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.INT, (Integer)this.value - (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Integer)this.value - (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value - (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value - (Double)t.getValue());
			
		}
		throw new parser.ParserError("Cannot compare obj of type "+this.type +" and "+t.getType());
		
	}
	
	public WolfObj mul(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.INT, (Integer)this.value * (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Integer)this.value * (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value * (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value * (Double)t.getValue());
			
		}
		if(this.type == TokenType.CONSTSTR && t.getType() == TokenType.INT) {
			String s=(String)this.value; 
			for(int i=1;i<(Integer)t.getValue();i++) {
				s+=this.value;
			}
			return new WolfObj(TokenType.CONSTSTR, s );
		
		}
		throw new parser.ParserError("Cannot sub obj of type "+this.type +" and "+t.getType());
		
	}
	public WolfObj div(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.getType() == TokenType.INT)
				return new WolfObj(TokenType.DOUBLE, Double.valueOf((Integer)this.value) / (Integer)t.getValue());
			else if(t.getType() == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Integer)this.value / (Double)t.getValue());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value / (Integer)t.getValue());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.DOUBLE, (Double)this.value / (Double)t.getValue());
			
		}
		throw new parser.ParserError("Cannot divide obj of type "+this.type +" and "+t.getType());
		
	}
	
	public WolfObj and(WolfObj t) {
		if(this.type == TokenType.BOOL && t.getType() == TokenType.BOOL) {
			return new WolfObj(TokenType.BOOL, (boolean)this.value && (boolean)t.getValue());
		}
		return new WolfObj(TokenType.NONE);
	}
	public WolfObj not() {
		if(this.type == TokenType.BOOL) {
			return new WolfObj(TokenType.BOOL, !((boolean)this.value));
		}
		//System.out.println()
		return new WolfObj(TokenType.NONE);
	}
	public WolfObj or(WolfObj t) {
		if(this.type == TokenType.BOOL && t.getType() == TokenType.BOOL) {
			return new WolfObj(TokenType.BOOL, (boolean)this.value || (boolean)t.getValue());
		}
		return new WolfObj(TokenType.NONE);
	}
}
