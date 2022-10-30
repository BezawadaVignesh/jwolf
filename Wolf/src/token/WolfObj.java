package token;

import token.TokenType; 

public class WolfObj {
	public TokenType type;
	private Object value;
	
	public WolfObj(TokenType type, Object value){
		this.type = type;
		this.set_value(value);
	}
	public WolfObj(TokenType type){
		this.type = type;
		
		this.set_value(null);
	}
	
	public WolfObj(WolfObj t) {
		this.type = t.type;
		this.set_value(t.get_value());
	}
	public TokenType get_type() {
		return this.type;
	}
	
	public String toString() {
		return ""+this.get_value();//"WolfObj( "+this.type+", "+this.get_value()+" )";
	}
	public Object get_value() {
		return value;
	}
	public void set_value(Object value) {
		this.value = value;
	}
	
	public WolfObj add(WolfObj t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new WolfObj(TokenType.INT, (Integer)this.value + (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new WolfObj(TokenType.DOUBLE, (Double)this.value + (Double)t.get_value());
		}
		else if(this.type == TokenType.CONSTSTR && t.get_type() == TokenType.CONSTSTR)
			return new WolfObj(TokenType.CONSTSTR, (String)this.value + (String)t.get_value());
		else {
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);
		}
	}
	
	public WolfObj comLess(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.get_type() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Integer)this.value < (Integer)t.get_value());
			else if(t.get_type() == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Integer)this.value < (Double)t.get_value());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value < (Integer)t.get_value());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Double)this.value < (Double)t.get_value());
			
		}
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);	
	}
	
	public WolfObj comLessEqual(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.get_type() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Integer)this.value <= (Integer)t.get_value());
			else if(t.get_type() == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Integer)this.value <= (Double)t.get_value());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value <= (Integer)t.get_value());
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Double)this.value <= (Double)t.get_value());
			
		}
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);	
	}
	
	public WolfObj comEqual(WolfObj t) {
		if(this.type == TokenType.INT ) {
			if(t.get_type() == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Integer)this.value == (Integer)t.get_value());
			else if(t.get_type() == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, Double.valueOf((Integer)this.value) == (Double)t.get_value());
			
		}else if(this.type == TokenType.DOUBLE) {
			if(this.type == TokenType.INT)
				return new WolfObj(TokenType.BOOL, (Double)this.value == Double.valueOf((Integer)t.get_value()));
			else if(this.type == TokenType.DOUBLE)
				return new WolfObj(TokenType.BOOL, (Double)this.value == (Double)t.get_value());
		}
		else if(this.type == TokenType.CONSTSTR && t.get_type() == TokenType.CONSTSTR)
			return new WolfObj(TokenType.BOOL, ((String)this.value).equals((String)t.get_value()));
		else if(this.type == TokenType.BOOL && t.get_type() == TokenType.BOOL)
			return new WolfObj(TokenType.BOOL, this.value == t.get_value());
		
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);	
	}
	
	public WolfObj sub(WolfObj t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new WolfObj(TokenType.INT, (Integer)this.value - (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new WolfObj(TokenType.DOUBLE, (Double)this.value - (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);
		}
	}
	
	public WolfObj mul(WolfObj t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new WolfObj(TokenType.INT, (Integer)this.value * (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new WolfObj(TokenType.DOUBLE, (Double)this.value * (Double)t.get_value());
		}else if(this.type == TokenType.CONSTSTR && t.get_type() == TokenType.INT) {
			String s=(String)this.value; 
			for(int i=1;i<(Integer)t.get_value();i++) {
				s+=this.value;
			}
			return new WolfObj(TokenType.CONSTSTR, s );
		
		}else {
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);
		}
	}
	public WolfObj div(WolfObj t) {
		if(this.type == TokenType.INT && t.get_type() == TokenType.INT) {
			return new WolfObj(TokenType.INT, (Integer)this.value / (Integer)t.get_value());
		}
		else if((this.type == TokenType.INT || this.type == TokenType.DOUBLE) 
				&& (t.get_type() == TokenType.INT || t.get_type() == TokenType.DOUBLE)) {
			return new WolfObj(TokenType.DOUBLE, (Double)this.value / (Double)t.get_value());
		}else {
			assert false: "addition of invalid type";
			return new WolfObj(TokenType.NONE);
		}
	}
	
	public WolfObj and(WolfObj t) {
		if(this.type == TokenType.BOOL && t.get_type() == TokenType.BOOL) {
			return new WolfObj(TokenType.BOOL, (boolean)this.value && (boolean)t.get_value());
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
		if(this.type == TokenType.BOOL && t.get_type() == TokenType.BOOL) {
			return new WolfObj(TokenType.BOOL, (boolean)this.value || (boolean)t.get_value());
		}
		return new WolfObj(TokenType.NONE);
	}
}
