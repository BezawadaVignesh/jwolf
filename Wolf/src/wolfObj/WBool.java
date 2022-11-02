package wolfObj;

import parser.ParserError;
import token.TokenType;

public class WBool extends WolfObj{

	WBool(boolean value) {
		super(TokenType.BOOL, value);
	}
	WolfObj and(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
		return new WBool((boolean)this.getValue() && (boolean)obj.getValue());
		
		throw new ParserError("Cannot and error..");
	}
	
	
	WolfObj comEqual(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
			return new WBool((boolean)this.getValue() == (boolean)obj.getValue());
		throw new ParserError("Cannot compare error..");
	}
	
	WolfObj not() {
		return new WBool(!((boolean)this.getValue()));
	}
	
	WolfObj or(WolfObj obj) {
		if(obj.getType() == TokenType.BOOL)
			return new WBool((boolean)this.getValue() || (boolean)obj.getValue());
			
		throw new ParserError("Cannot or error..");
	}

}
