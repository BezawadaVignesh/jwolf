package wolfObj;

import token.TokenType;

public class WString extends WolfObj{
	public WString(String value){
		super(TokenType.CONSTSTR, value);
		
	}
	
	public WolfObj add(WolfObj obj) {
		if(obj.getType() == TokenType.CONSTSTR)
			return new WString((String)this.getValue() + (String)obj.getValue());
		return super.add(obj);
	}
	
	public WolfObj comEqual(WolfObj obj) {
		if(obj.getType() == TokenType.CONSTSTR) {
			return new WBool(((String)this.getValue()).equals((String)obj.getValue()));
		}
		return super.add(obj);
	}
	
	public WolfObj comLess(WolfObj obj) {
		String s;
		if(obj.getType() == TokenType.CONSTSTR) {
			return new WBool(((String)this.getValue()).compareTo((String)obj.getValue())<0);
		}
		return super.add(obj);
	}
	
	public WolfObj comLessEqual(WolfObj obj) {
		String s;
		if(obj.getType() == TokenType.CONSTSTR) {
			return new WBool(((String)this.getValue()).compareTo((String)obj.getValue())<=0);
		}
		return super.add(obj);
	}
	
	public WolfObj mul(WolfObj obj) {
		if(obj.getType() == TokenType.INT) {
			String s = (String)this.getValue();
			for(int i=1;i<(Integer)obj.getValue();i++) {
				s+=this.getValue();
			}
			return new WString(s);
		
		}return super.mul(obj);
	}
	
}
