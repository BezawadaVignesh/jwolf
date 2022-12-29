package wolfObj;

import java.util.ArrayList;
import java.util.Map;

import token.TokenType;

public class WDict extends WolfObj {
	public WDict(ArrayList<Map.Entry<WolfObj, WolfObj>> v){
		super(TokenType.DICT, v);
	}
	public String toString() {
		String str="";
		str += "{";
		for(Map.Entry<WolfObj, WolfObj> tmp : (ArrayList<Map.Entry<WolfObj, WolfObj>>)this.getValue()) {
			str += tmp.getKey()+": "+tmp.getValue()+ ", ";
		}
		str +="}";
		return str;
	}
	
	public void assignTo(WolfObj index, WolfObj newObj) {
		ArrayList<Map.Entry<WolfObj, WolfObj>> tmp = (ArrayList<Map.Entry<WolfObj, WolfObj>>)this.getValue();
		for(int i=0; i < tmp.size();i++) {
			if(tmp.get(i).getKey().getType() == index.getType() 
					&& (boolean)tmp.get(i).getKey().comEqual(index).getValue()) {
				tmp.set(i, Map.entry(index, newObj));
				return;
			}
		}
		tmp.add(Map.entry(index, newObj));
	}
	
	public WolfObj unpack(WolfObj index) {
		ArrayList<Map.Entry<WolfObj, WolfObj>> tmp = (ArrayList<Map.Entry<WolfObj, WolfObj>>)this.getValue();
		for(int i=0; i < tmp.size();i++) {
			if(tmp.get(i).getKey().getType() == index.getType() 
					&& (boolean)tmp.get(i).getKey().comEqual(index).getValue()) {
				return tmp.get(i).getValue();
			}
		}
		throw new parser.ParserError("Key Error "+index);
	}
}
