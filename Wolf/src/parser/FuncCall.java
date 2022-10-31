package parser;

import java.util.ArrayList;

public class FuncCall extends AST{
	private ArrayList<AST> args;
	private String name;
	FuncCall(String name, ArrayList<AST> args) {
		super(OpType.FUNCCALL);
		this.set_name(name);
		this.args = args;
	}
	public ArrayList<AST> get_args() {
		return args;
	}
	public void set_args(ArrayList<AST> args) {
		this.args = args;
	}
	public String toString() {
		return "FuncDecl( "+this.args+" )";
	}
	public String get_name() {
		return name;
	}
	public void set_name(String name) {
		this.name = name;
	}
}
