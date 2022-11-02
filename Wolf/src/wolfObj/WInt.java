package wolfObj;

import parser.ParserError;
import token.TokenType;

public class WInt extends WolfObj{
	WInt(int value) {
		super(TokenType.INT, value);
	}
	
	WolfObj add(WolfObj obj) {
		return new WInt((int)getValue() + (int)obj.getValue()); 
	}
	WolfObj sub(WolfObj obj) {
		return new WInt((int)getValue() - (int)obj.getValue());
	}
	WolfObj mul(WolfObj obj) {
		return new WInt((int)getValue() * (int)obj.getValue());
	}
	WolfObj div(WolfObj obj) {
		return new WInt((int)getValue() / (int)obj.getValue());
	}
	WolfObj and(WolfObj obj) {
		return new WBool((int)getValue() != 0 && (int)obj.getValue() != 0);
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
	
}
