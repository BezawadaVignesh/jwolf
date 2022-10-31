package parser;

import java.util.ArrayList;

public class UnpackOp extends AST{
	private String name;
	private ArrayList<AST> index;
	UnpackOp(String name, ArrayList<AST> i) {
		super(OpType.UNPACKOP);
		this.set_name(name);
		this.set_index(i);
	}
	public String get_name() {
		return name;
	}
	public void set_name(String name) {
		this.name = name;
	}
	public ArrayList<AST> get_index() {
		return index;
	}
	public void set_index(ArrayList<AST> index) {
		this.index = index;
	}
	
	public String toString() {
		return "UnpackOp ( "+this.name+", "+this.index+" )";
	}

}
