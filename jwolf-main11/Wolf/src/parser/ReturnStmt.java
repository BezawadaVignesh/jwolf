package parser;

import java.util.ArrayList;

public class ReturnStmt extends AST{

	private ArrayList<AST> values;
	ReturnStmt(ArrayList<AST> v) {
		super(OpType.RETURNSTMT);
		this.set_values(v);
	}
	public ArrayList<AST> get_values() {
		return values;
	}
	public void set_values(ArrayList<AST> values) {
		this.values = values;
	}
	public String toString() {
		return "return ( "+this.values+" )";
	}
	
}
