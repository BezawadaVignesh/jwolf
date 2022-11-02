package parser;

import java.util.ArrayList;

public class AssignOp extends AST{
	private AST right;
	private ArrayList<String> name;
	
	public AssignOp(ArrayList<String> name, AST right) {
		super(OpType.ASSIGNOP);
		this.name = name;
		this.right = right;
	}
	
	public ArrayList<String> get_name() {
		return this.name;
	}
	
	public AST get_right() {
		return this.right;
	}
	
	public String toString() {
		return "AssignOp( " + this.name + ", " + this.right + " )";
	}
}
