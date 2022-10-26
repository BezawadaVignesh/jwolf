package parser;

public class AssignOp extends AST{
	private AST right;
	private String name;
	
	public AssignOp(String name, AST right) {
		super(OpType.ASSIGNOP);
		this.name = name;
		this.right = right;
	}
	
	public String get_name() {
		return this.name;
	}
	
	public AST get_right() {
		return this.right;
	}
	
	public String toString() {
		return "AssignOp( " + this.name + ", " + this.right + " )";
	}
}
