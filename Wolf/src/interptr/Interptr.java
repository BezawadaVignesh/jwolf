package interptr;

import token.TokenType;
import token.WolfObj;

import java.io.InvalidClassException;
/*
import wolfObj.WDouble;
import wolfObj.WInt;
import wolfObj.WolfObj;*/
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

	Interptr(){

		this.varLists.addFirst(new HashMap<String, WolfObj>());
		//this.varLists.get(this.varLists.size()-1).put("a", new WolfObj(TokenType.INT, 7));
	}
	Interptr(ArrayList<AST> treeList){
		this.treeList = treeList;
		this.varLists.addFirst(new HashMap<String, WolfObj>());
	}
	
	WolfObj i_FuncCall(parser.FuncCall t) {
	    Object obj = new PreDefFunc();
	    Method method = null;
	    WolfObj result = null;
		String msg = "No function with name "+t.get_name();
		result =  interptr(t.get_name());
		if(result.getType() == TokenType.CONSTSTR) {
			try {
				method = PreDefFunc.class.getDeclaredMethod((String)result.getValue() , WolfObj.class);
			} catch (NoSuchMethodException | SecurityException e) {throw new parser.ParserError(msg);}
			try {
		    	ArrayList<WolfObj> wargs = new ArrayList<WolfObj>();
				for(AST a : t.get_args()) 
					wargs.add(interptr(a));
		    	return (WolfObj) method.invoke(obj, new WolfObj(TokenType.PACKED, wargs));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				
			}
		}
		else if(result.getType() == TokenType.FUNC){
			Entry<ArrayList<String>, ArrayList<AST>> func = (Entry<ArrayList<String>, ArrayList<AST>>)result.getValue();
			if(func.getKey().size() == t.get_args().size()) {
				try {
						return run_block(func.getValue(), Map.entry(func.getKey(), t.get_args())); 
					}catch(interptr.ReturnRequest rtn) {
						return rtn.get_args();
					}
			}	
			msg = " Args mismatch wanted "+func.getKey().size() + " got " +t.get_args().size();
			
		}else 
			msg = t.get_name() +"() is not callable";
			
	    throw new parser.ParserError(msg);
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
		case PERIOD:
			System.out.println(interptr(t.get_left())+"---");
			return interptr(t.get_left()).getProperties(interptr(t.get_right()));
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
		
		if(obj.getType() != TokenType.PACKED) {
			throw new ParserError("Dev Error");
		}
		ArrayList<WolfObj> aObj = (ArrayList<WolfObj>)obj.getValue();
		//System.out.print("\n"+aObj.get(0));
		if( aObj.size() == 1 && aObj.get(0).getType() == TokenType.PACKED) {
			obj = obj.unpackByInt(0);
		
		}
		if(t.get_name().size() == ((ArrayList<WolfObj>)obj.getValue()).size()) {
			
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
		ArrayList<WolfObj> tmpObj ;
		switch(t.token.get_type()) {
		case VAR:
			String funcName = (String)t.token.get_value();
			for(Map<String, WolfObj> scope:this.varLists) {
				if(scope.containsKey(funcName)) {
					return (WolfObj)scope.get((String)t.token.get_value()); //to change create new
				}
			}
			try {
				Method[] methods = PreDefFunc.class.getMethods();
				for(Method method : methods) {
					if(funcName.equals(method.getName())) {
						return new WolfObj(TokenType.CONSTSTR, funcName);
					}
				}
			}catch(SecurityException e) {
				throw new parser.ParserError("No var with name "+funcName);
			}
			return new WolfObj(TokenType.NONE);
		case LIST:
			tmpObj = new ArrayList<WolfObj>();
			for(AST a: (ArrayList<AST>)t.token.get_value())	
				tmpObj.add(interptr(a));
			return new WolfObj(TokenType.LIST, tmpObj);
		case PACKED:
			tmpObj = new ArrayList<WolfObj>();
			for(AST a: (ArrayList<AST>)t.token.get_value())	
				tmpObj.add(interptr(a));
			return new WolfObj(TokenType.PACKED, tmpObj);
		/*case INT:
			return new WInt((int)t.token.get_value());
		case BOOL:
			return new WBool((boolean)t.token.get_value());
		case DOUBLE:
			return new WDouble((double)t.token.get_value());*/
		default:
			return  new WolfObj(t.token.get_type(), t.token.get_value());
		}
		
		
		
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
		WolfObj tmpObj = interptr(t.get_name());
		/*for(Map<String, WolfObj> scope:this.varLists) {
			if(scope.containsKey((String)t.get_name())) {
				WolfObj obj = (WolfObj)scope.get((String)t.get_name());
				for(int i=0;i<t.get_index().size();i++)
					obj = obj.unpack(interptr(t.get_index().get(i))); //to change create new
				return obj;
			}
		}
		throw new ParserError("Undefined Variable "+ t.get_name());*/
		return tmpObj.unpack(interptr(t.get_index()));
	}
	//WolfObj
	
	WolfObj i_FuncDecl(parser.FuncDecl t) {
		WolfObj obj = new WolfObj(TokenType.FUNC, Map.entry(t.get_args(), t.get_stms()));
		this.varLists.get(0).put(t.get_name(), obj);
		
		return new WolfObj(TokenType.NONE);
		
	}
	 // args should be assigned 
	WolfObj run_block(ArrayList<AST> stms, Entry args) {
		
		this.varLists.addFirst(new HashMap<String, WolfObj>());
		ArrayList<String> names = (ArrayList<String>)args.getKey();
		i_AssignOp(new parser.AssignOp((ArrayList<String>)args.getKey(), new CToken( TokenType.PACKED, (ArrayList<AST>)args.getValue() )));
		
		for(AST a:stms)		
			interptr(a);
		
		this.varLists.removeFirst();
		return new WolfObj(TokenType.NONE);
	}
	
	WolfObj i_WhileBlock(ConBlock t) {
		//System.out.println(t.get_stmts());
		WolfObj obj = interptr(t.get_condition());
		while(obj.getType() == TokenType.BOOL && (boolean)obj.getValue()) {
			for(AST a:t.get_stmts())
				interptr(a);
			obj = interptr(t.get_condition());
		}
		return new WolfObj(TokenType.NONE);
	}
	WolfObj i_IfBlock(ConBlock t) {
		//System.out.println(t.get_stmts());
		WolfObj obj = interptr(t.get_condition()), tmpObj = new WolfObj(TokenType.NONE);
		if(obj.getType() == TokenType.BOOL && (boolean)obj.getValue()) {
			for(AST a:t.get_stmts()) 
				interptr(a);
		}
		return tmpObj;
	}
}
