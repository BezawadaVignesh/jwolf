package wolfObj;

import parser.ParserError;
import java.util.ArrayList;
import java.util.Map;

import interptr.PreDefFunc;
import token.TokenType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WList extends WolfObj{
	public WList(ArrayList<WolfObj> value) {
		super(TokenType.LIST, value);
		try {
			properties.put("count", new WolfObj(TokenType.JFUNC, Map.entry(this, WList.class.getDeclaredMethod("L_count",  wolfObj.WolfObj.class))));
			
		}catch (NoSuchMethodException | SecurityException e) {throw new parser.ParserError("Dev Erroe WList");}
	}
	
	public WolfObj L_count(WolfObj obj) {
		if(!(obj.getType() == TokenType.PACKED)) throw new parser.ParserError("Dev Error");
		ArrayList<WolfObj> args = (ArrayList<WolfObj>)obj.getValue();
		if(args.size() != 1) throw new parser.ParserError("count() excepts 1 but got "+args.size()); 
		int c = 0;
			for(WolfObj o : (ArrayList<WolfObj>)this.getValue()) {
				//System.out.println(o.getType()+" "+args.get(0).getType());
				if(o.getType() == args.get(0).getType() && (boolean)o.comEqual(args.get(0)).getValue()) c++;
					
			}
				
		
		return new WInt(c);
	}
	
	public void assignTo(WolfObj index, WolfObj newObj) {
		if(index.getType() != TokenType.INT) throw new ParserError("Required a int value for unpacking got "+ index.getType());
		ArrayList<WolfObj> tmp = (ArrayList<WolfObj>)this.getValue();
		if(tmp.size()>(int)index.getValue()) tmp.set((int)index.getValue(), newObj);
		else throw new ParserError("Index OutOfBound");
			
	}
	
	public WolfObj unpackByInt(int index) {
		ArrayList<WolfObj> tmp = (ArrayList<WolfObj>)this.getValue();
		if(tmp.size() > index)
			return tmp.get(index);
		throw new ParserError("List OutOfBound");
	}
	
	public WolfObj unpack(WolfObj obj) {
		if(obj.getType() == TokenType.INT)
			return unpackByInt((int)obj.getValue());
		throw new ParserError("Required a int value for unpacking got "+ obj.getType());
	}
	
}
