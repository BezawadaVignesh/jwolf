package parser;

import java.util.ArrayList;

public class FuncCall extends AST{
	private ArrayList<AST> args;
	private AST name;
	FuncCall(AST name, ArrayList<AST> args) {
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
		return "Funccall( "+this.name+","+this.args+" )";
	}
	public AST get_name() {
		return name;
	}
	public void set_name(AST name) {
		this.name = name;
	}
}
