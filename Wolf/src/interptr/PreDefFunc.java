package interptr;

import java.util.ArrayList;
import java.util.Scanner;

import token.TokenType;
import wolfObj.*;
import wolfObj.WolfObj;

public class PreDefFunc {
	public WolfObj print(WolfObj wobj) {
		if(wobj.getType() == TokenType.PACKED) {
			ArrayList<WolfObj> objs = (ArrayList<WolfObj>)wobj.getValue();
			for(WolfObj obj : objs)
				System.out.print(obj);
		}
		else {
			System.out.print(wobj);
		}
		System.out.println();
		return new WolfObj(TokenType.NONE);
	}
	public WolfObj input(WolfObj wobj) {
		print(wobj);
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		sc.close();
		return new WString(str);
	}
	public WolfObj type(WolfObj wobj) {
		if(wobj.getType() == TokenType.PACKED) {
			ArrayList<WolfObj> objs = (ArrayList<WolfObj>)wobj.getValue();
			if(objs.size() == 1)
				return new WolfObj(TokenType.TYPE, objs.get(0).getType());
		}
		throw new parser.ParserError("Required only one args");
	}
	
	public WolfObj integer(WolfObj wobj) {
		if(wobj.getType() != TokenType.PACKED)
			throw new parser.ParserError("Dev ERROR");
		ArrayList<WolfObj> objs = (ArrayList<WolfObj>)wobj.getValue();
		if(objs.size() == 1) {
			WolfObj obj = objs.get(0);
			if(obj.getType() == TokenType.CONSTSTR) {
				return new WInt(Integer.parseInt((String)obj.getValue()));
			}
			throw new parser.ParserError("Cannot convert to int");
		}
			throw new parser.ParserError("integer takes required only 1 arg but "+objs.size());
		
	}
}
