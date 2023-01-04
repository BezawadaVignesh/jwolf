package wolfObj;

import java.util.ArrayList;

import parser.ParserError;
import token.TokenType;

public class WPacked extends WolfObj{

	public WPacked(ArrayList<WolfObj> value) {
		super(TokenType.PACKED, value);
	}
	
	public WolfObj unpackByInt(int index) {
		ArrayList<WolfObj> tmp = (ArrayList<WolfObj>)this.getValue();
		if(tmp.size() > index)
			return tmp.get(index);
		throw new ParserError("Invalid Size request "+index);
	}
	
	public WolfObj unpack(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return unpackByInt((int)obj.getValue());
		throw new ParserError("Required a int value for unpacking got "+ obj.getType());
	}
	
}
