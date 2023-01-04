package parser;

import java.util.ArrayList;
import java.util.List;

public class AssignOp extends AST{
	private AST right;
	private List<AST> name;
	
	public AssignOp(List<AST> list, AST right) {
		super(OpType.ASSIGNOP);
		this.name = list;
		this.right = right;
	}
	
	public List<AST> get_name() {
		return this.name;
	}
	
	public AST get_right() {
		return this.right;
	}
	
	public String toString() {
		return "AssignOp( " + this.name + ", " + this.right + " )";
	}
}
