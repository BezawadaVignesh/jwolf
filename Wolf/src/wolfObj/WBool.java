package wolfObj;

import parser.ParserError;
import token.TokenType;

public class WBool extends WolfObj{

	public WBool(boolean value) {
		super(TokenType.BOOL, value);
	}
	public WolfObj and(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
		return new WBool((boolean)this.getValue() && (boolean)obj.getValue());
		
		throw new ParserError("Cannot and error..");
	}
	
	
	public WolfObj comEqual(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
			return new WBool((boolean)this.getValue() == (boolean)obj.getValue());
		throw new ParserError("Cannot compare error..");
	}
	
	public WolfObj not() {
		return new WBool(!((boolean)this.getValue()));
	}
	
	public WolfObj or(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
			return new WBool((boolean)this.getValue() || (boolean)obj.getValue());
			
		throw new ParserError("Cannot or error..");
	}

}
