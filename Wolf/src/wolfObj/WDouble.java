package wolfObj;

import parser.ParserError;
import token.TokenType;

public class WDouble extends WolfObj{
	public WDouble(double value) {
		super(TokenType.DOUBLE, value);
	}
	
	public WolfObj add(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WDouble((double)this.getValue() + (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WDouble((double)this.getValue() + (double)obj.getValue());
		return super.add(obj);
	}
	
	public WolfObj sub(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WDouble((double)this.getValue() - (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WDouble((double)this.getValue() - (double)obj.getValue());
		return super.sub(obj);
	}
	
	public WolfObj mul(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WDouble((double)this.getValue() * (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WDouble((double)this.getValue() * (double)obj.getValue());
		return super.mul(obj);
	}
	
	public WolfObj div(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WDouble((double)this.getValue() / (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WDouble((double)this.getValue() / (double)obj.getValue());
		return super.div(obj);
	}
	
	public WolfObj and(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WBool((double)this.getValue() != 0 && (int)obj.getValue() != 0);
		return super.and(obj);
	}
	
	public WolfObj comLess(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WBool((double)this.getValue() < (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WBool((double)this.getValue() < (double)obj.getValue());
		return super.comLess(obj);
	}
	
	public WolfObj comLessEqual(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WBool((double)this.getValue() <= (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WBool((double)this.getValue() <= (double)obj.getValue());
		return super.comLessEqual(obj);
	}
	
	public WolfObj comEqual(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return new WBool((double)this.getValue() == (int)obj.getValue());
		else if(obj.getType() == TokenType.DOUBLE)
			return new WBool((double)this.getValue() == (double)obj.getValue());
		return super.comEqual(obj);
	}
	
}
