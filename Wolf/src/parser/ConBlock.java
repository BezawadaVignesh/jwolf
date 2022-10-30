package parser;

import java.util.ArrayList;

public class ConBlock extends AST{
	private AST condition;
	private ArrayList<AST> stmts;
	ConBlock(OpType type, AST condition, ArrayList<AST> stmts) {
		super(type);
		this.set_condition(condition);
		this.set_stmts(stmts);
	}
	public AST get_condition() {
		return condition;
	}
	public void set_condition(AST condition) {
		this.condition = condition;
	}
	public ArrayList<AST> get_stmts() {
		return stmts;
	}
	public void set_stmts(ArrayList<AST> stmts) {
		this.stmts = stmts;
	}
	
	public String toString() {
		return "IfBlock( "+this.condition+", "+this.stmts+")";
	}
	
}
