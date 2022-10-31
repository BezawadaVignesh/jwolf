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
			/*for(Map<String, Entry<ArrayList<String>, ArrayList<AST>>> scope:this.funcLists) {
				if(scope.containsKey((String)t.get_name())) {
					if(scope.get(t.get_name()).getKey().size() == t.get_args().size()) {
						try {
						return run_block(scope.get(t.get_name()).getValue(), Map.entry(scope.get(t.get_name()).getKey(), t.get_args())); 
						}catch(interptr.ReturnRequest rtn) {
							WolfObj o = rtn.get_args();
							//System.out.println("func call "+o.get_value());
							return o;
						}
					}
				}
			}*/
			for(Map<String, WolfObj> scope:this.varLists) {
				if(scope.containsKey((String)t.get_name())) {
					if(scope.get((String)t.get_name()).type == TokenType.FUNC) {
						Entry<ArrayList<String>, ArrayList<AST>> func = (Entry<ArrayList<String>, ArrayList<AST>>)scope.get((String)t.get_name()).get_value();
						if(func.getKey().size() == t.get_args().size()) {
							try {
									return run_block(func.getValue(), Map.entry(func.getKey(), t.get_args())); 
								}catch(interptr.ReturnRequest rtn) {
									WolfObj o = rtn.get_args();
									//System.out.println("func call "+o.get_value());
									return o;
								}
						}
					}
					throw new parser.ParserError(t.get_name() +"() is not callable");
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
		//System.out.println(t.get_right().type);
		WolfObj obj = interptr(t.get_right());
		
		if(obj.type != TokenType.PACKED) {
			throw new ParserError("Dev Error");
		}
		ArrayList<WolfObj> aObj = (ArrayList<WolfObj>)obj.get_value();
		//System.out.print("\n"+aObj.get(0));
		if( aObj.size() == 1 && aObj.get(0).type == TokenType.PACKED) {
			obj = obj.unpackByInt(0);
		
		}
		if(t.get_name().size() == ((ArrayList<WolfObj>)obj.get_value()).size()) {
			
			for(int i=0;i<t.get_name().size();i++)
				this.varLists.get(0).put(t.get_name().get(i), obj.unpackByInt(i));
		}
		else throw new parser.ParserError("Cannot assign "+t.get_name()+" = "+obj);
		return new WolfObj(TokenType.NONE);
	}
	
	WolfObj i_CToken(parser.CToken t) {
		/*double result = 0;
		if(t.token.type == TokenType.INT)
			result = (Integer)t.token.get_value();
		else if(t.token.type == TokenType.DOUBLE)
			result = (Double)t.token.get_value();
		else
			assert false:"i_CToken()";*/
		
		if(t.token.get_type() == TokenType.VAR) {
			for(Map<String, WolfObj> scope:this.varLists) {
				if(scope.containsKey((String)t.token.get_value())) {
					return (WolfObj)scope.get((String)t.token.get_value()); //to change create new
				}
			}
			return new WolfObj(TokenType.NONE);
		}
		else if(t.token.get_type() == TokenType.LIST) {
			ArrayList<WolfObj> tmpObj = new ArrayList<WolfObj>();
			for(AST a: (ArrayList<AST>)t.token.get_value())	
				tmpObj.add(interptr(a));
			return new WolfObj(TokenType.LIST, tmpObj);
		}else if(t.token.get_type() == TokenType.PACKED) {
			ArrayList<WolfObj> tmpObj = new ArrayList<WolfObj>();
			for(AST a: (ArrayList<AST>)t.token.get_value())	
				tmpObj.add(interptr(a));
			return new WolfObj(TokenType.PACKED, tmpObj);
		}
		else
			return  new WolfObj(t.token.get_type(), t.token.get_value());
		
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
		else if(t.type == OpType.FUNCCALL) 
			return i_FuncCall((parser.FuncCall)t);
		else if(t.type == OpType.UNPACKOP) 
			return i_UnpackOp((parser.UnpackOp)t);
		else if(t.type == OpType.RETURNSTMT)
			return i_ReturnStmt((parser.ReturnStmt) t);
		else 
			assert false: "interptr";
		return new WolfObj(TokenType.NONE);
		
	}
	
	private WolfObj i_ReturnStmt(ReturnStmt t) {
		ArrayList<WolfObj> tmpObj = new ArrayList<WolfObj>();
		for(AST a:t.get_values()) {
			tmpObj.add(interptr(a));
		}
		if(tmpObj.size() == 1) {
			throw new ReturnRequest("Return ", tmpObj.get(0));
		}
		//System.out.println("Return "+tmpObj);
		throw new ReturnRequest("Return ",new WolfObj(TokenType.PACKED, tmpObj));
	}
	WolfObj i_UnpackOp(UnpackOp t) {
		for(Map<String, WolfObj> scope:this.varLists) {
			if(scope.containsKey((String)t.get_name())) {
				WolfObj obj = (WolfObj)scope.get((String)t.get_name());
				for(int i=0;i<t.get_index().size();i++)
					obj = obj.unpack(interptr(t.get_index().get(i))); //to change create new
				return obj;
			}
		}
		throw new ParserError("Undefined Variable "+ t.get_name());
	}
	//WolfObj
	
	WolfObj i_FuncDecl(parser.FuncDecl t) {
		WolfObj obj = new WolfObj(TokenType.FUNC, Map.entry(t.get_args(), t.get_stms()));
		this.varLists.get(0).put(t.get_name(), obj);
		/*
		this.funcLists.get(0).put(t.get_name(), Map.entry(t.get_args(), t.get_stms()));
		*/
		return new WolfObj(TokenType.NONE);
		
	}
	 // args should be assigned 
	WolfObj run_block(ArrayList<AST> stms, Entry args) {
		
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		ArrayList<String> names = (ArrayList<String>)args.getKey();
		i_AssignOp(new parser.AssignOp((ArrayList<String>)args.getKey(), new CToken( TokenType.PACKED, (ArrayList<AST>)args.getValue() )));
		for(int i=0;i<names.size();i++) {
			
		}
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
		WolfObj obj = interptr(t.get_condition()), tmpObj = new WolfObj(TokenType.NONE);
		if(obj.type == TokenType.BOOL && (boolean)obj.get_value()) {
			for(AST a:t.get_stmts()) 
				interptr(a);
		}
		return tmpObj;
	}
	
	

}
