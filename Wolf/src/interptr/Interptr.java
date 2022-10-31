package interptr;

import token.TokenType;
import token.WolfObj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import parser.*;

public class Interptr {
	private ArrayList<AST> treeList;
	private LinkedList<Map<String, WolfObj>> varLists = new LinkedList<Map<String, WolfObj>>();
	private LinkedList<Map<String,Entry<ArrayList<String>, ArrayList<AST>>>> funcLists = new LinkedList<Map<String,Entry<ArrayList<String>, ArrayList<AST>>>>();
	Interptr(){
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		this.funcLists.add(new HashMap<String, Entry<ArrayList<String>, ArrayList<AST>>>());
		//this.varLists.get(this.varLists.size()-1).put("a", new WolfObj(TokenType.INT, 7));
	}
	Interptr(ArrayList<AST> treeList){
		this.treeList = treeList;
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		this.funcLists.add(new HashMap<String, Entry<ArrayList<String>, ArrayList<AST>>>());
	}
	
	WolfObj i_FuncCall(parser.FuncCall t) {
		//t.get_args().size()/////
		ArrayList<WolfObj> wargs = new ArrayList<WolfObj>();
		for(AST a : t.get_args()) {
			wargs.add(interptr(a));
		}
		Class c = null;
		try {
			c = Class.forName("interptr.PreDefFunc");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	    Object obj = null;
		try {
			obj = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	    Method method = null;
		try {
			method = c.getDeclaredMethod(t.get_name() , WolfObj.class);
		} catch (NoSuchMethodException | SecurityException e) {
			for(Map<String, Entry<ArrayList<String>, ArrayList<AST>>> scope:this.funcLists) {
				if(scope.containsKey((String)t.get_name())) {
					if(scope.get(t.get_name()).getKey().size() == t.get_args().size()) {
						run_block(scope.get(t.get_name()).getValue());
						return new WolfObj(TokenType.NONE); 
					}
				}
			}
			throw new parser.ParserError("No function with name "+t.get_name());
		}
	    try {
	    	for(WolfObj a: wargs)
	    		method.invoke(obj, a);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	    System.out.println();
	    return new WolfObj(TokenType.NONE);
	}
	
	WolfObj i_BinOp(BinOp t) {
		switch(t.get_op().type) {
		case PLUS:
			return interptr(t.get_left()).add(interptr(t.get_right()));
		case AND:
			return interptr(t.get_left()).and(interptr(t.get_right()));
		case OR:
			return interptr(t.get_left()).or(interptr(t.get_right()));
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
		case NOTEQUAL:
			return (interptr(t.get_left()).comEqual(interptr(t.get_right()))).not();
		default:
			System.out.println("End of i_BinOp"+t.get_op().type);
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
		else if(t.type == OpType.IFBLOCK)
			return i_IfBlock((parser.ConBlock)t);
		else if(t.type == OpType.WHILEBLOCK)
			return i_WhileBlock((parser.ConBlock)t);
		else if(t.type == OpType.BINOP)
			return i_BinOp((parser.BinOp)t);
		else if(t.type == OpType.UNARYOP)
			return i_UnaryOp((parser.UnaryOp)t);
		else if(t.type == OpType.CTOKEN)
			return i_CToken((parser.CToken)t);
		else if(t.type == OpType.FUNCDECL)
			return i_FuncDecl((parser.FuncDecl)t);
		else if(t.type == OpType.FUNCCALL) {
			return i_FuncCall((parser.FuncCall)t);
		}
		else 
			assert false: "interptr";
		return new WolfObj(TokenType.NONE);
		
	}
	
	//WolfObj
	
	WolfObj i_FuncDecl(parser.FuncDecl t) {
		this.funcLists.get(0).put(t.get_name(), Map.entry(t.get_args(), t.get_stms()));
		return new WolfObj(TokenType.NONE);
	}
	 // args should be assigned 
	WolfObj run_block(ArrayList<AST> stms) {
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		for(AST a:stms) {
			interptr(a);
		}
		this.varLists.removeFirst();
		return new WolfObj(TokenType.NONE);
	}
	
	WolfObj i_WhileBlock(ConBlock t) {
		//System.out.println(t.get_stmts());
		WolfObj obj = interptr(t.get_condition());
		while(obj.type == TokenType.BOOL && (boolean)obj.get_value()) {
			for(AST a:t.get_stmts())
				interptr(a);
			obj = interptr(t.get_condition());
		}
		return new WolfObj(TokenType.NONE);
	}
	WolfObj i_IfBlock(ConBlock t) {
		//System.out.println(t.get_stmts());
		WolfObj obj = interptr(t.get_condition());
		if(obj.type == TokenType.BOOL && (boolean)obj.get_value()) {
			for(AST a:t.get_stmts())
				interptr(a);
			
		}
		return new WolfObj(TokenType.NONE);
	}
	
	

}
