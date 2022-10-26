package interptr;

import token.TokenType;
import token.WolfObj;
import parser.AST;
import parser.OpType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import parser.*;

public class Interptr {
	private ArrayList<AST> treeList; 
	private LinkedList<Map<String, WolfObj>> varLists = new LinkedList<Map<String, WolfObj>>();
	Interptr(){
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		//this.varLists.get(this.varLists.size()-1).put("a", new WolfObj(TokenType.INT, 7));
	}
	Interptr(ArrayList<AST> treeList){
		this.treeList = treeList;
		this.varLists.addFirst(new HashMap<String, WolfObj>());
	}
	WolfObj i_BinOp(BinOp t) {
		switch(t.get_op().type) {
		case PLUS:
			return interptr(t.get_left()).add(interptr(t.get_right()));
		case MINUS:
			return interptr(t.get_left()).sub(interptr(t.get_right()));
		case MUL:
			return interptr(t.get_left()).mul(interptr(t.get_right()));
		case DIV:
			return interptr(t.get_left()).div(interptr(t.get_right()));
		case LESS:
			return interptr(t.get_left()).comLess(interptr(t.get_right()));
		case GREATER:
			return interptr(t.get_right()).comLess(interptr(t.get_left()));
		case LESSEQUAL:
			return interptr(t.get_left()).comLessEqual(interptr(t.get_right()));
		case GREATEREQUAL:
			return interptr(t.get_right()).comLessEqual(interptr(t.get_left()));
		case EQUALEQUAL:
			return interptr(t.get_left()).comEqual(interptr(t.get_right()));
		default:
			System.out.println("End of i_BinOp");
			assert false:"i_BinOP";
			return new WolfObj(TokenType.NONE);
		}
	}
	
	WolfObj i_UnaryOp(UnaryOp t) {
		if(t.get_op().get_type() == TokenType.MINUS)
		return interptr(t.get_right()).mul(new WolfObj(TokenType.INT, -1));
		return interptr(t.get_right());
	}
	
	WolfObj i_AssignOp(parser.AssignOp t) {
		WolfObj obj = interptr(t.get_right());
		this.varLists.get(0).put(t.get_name(), obj); 
		return obj;
	}
	
	WolfObj i_CToken(parser.CToken t) {
		/*double result = 0;
		if(t.token.type == TokenType.INT)
			result = (Integer)t.token.get_value();
		else if(t.token.type == TokenType.DOUBLE)
			result = (Double)t.token.get_value();
		else
			assert false:"i_CToken()";*/
		WolfObj wo;
		if(t.token.get_type() == TokenType.VAR) {
			for(Map<String, WolfObj> scope:this.varLists) {
				if(scope.containsKey((String)t.token.get_value())) {
					return (WolfObj)scope.get((String)t.token.get_value()); //to change create new
				}
			}
			return new WolfObj(TokenType.NONE);
		}
		else
			wo = new WolfObj(t.token.get_type(), t.token.get_value());
		return wo;
	}
	
	WolfObj interptr(AST t) {
		if(t.type == OpType.ASSIGNOP)
			return i_AssignOp((parser.AssignOp)t);
		else if(t.type == OpType.BINOP)
			return i_BinOp((parser.BinOp)t);
		else if(t.type == OpType.UNARYOP)
			return i_UnaryOp((parser.UnaryOp)t);
		else if(t.type == OpType.CTOKEN)
			return i_CToken((parser.CToken)t);
		else 
			assert false: "interptr";
		return new WolfObj(TokenType.NONE);
		
	}
	
	

}
