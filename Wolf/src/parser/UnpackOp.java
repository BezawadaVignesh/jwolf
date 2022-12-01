package parser;

import java.util.ArrayList;

public class UnpackOp extends AST{
	private AST name;
	private AST index;
	UnpackOp(AST name, AST i) {
		super(OpType.UNPACKOP);
		this.set_name(name);
		this.set_index(i);
	}
	public AST get_name() {
		return name;
	}
	public void set_name(AST name) {
		this.name = name;
	}
	public AST get_index() {
		return index;
	}
	public void set_index(AST index) {
		this.index = index;
	}
	
	public String toString() {
		return "UnpackOp ( "+this.name+", "+this.index+" )";
	}

}
