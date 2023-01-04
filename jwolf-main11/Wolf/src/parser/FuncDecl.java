package parser;

import java.util.ArrayList;

public class FuncDecl extends AST{
	private ArrayList<AST>  stms;
	private String name;
	private ArrayList<AST> args;
	FuncDecl(String name, ArrayList<AST> args, ArrayList<AST> stms) {
		super(OpType.FUNCDECL);
		this.set_name(name);
		this.set_args(args);
		this.set_stms(stms);
	}
	public String get_name() {
		return this.name;
	}
	public void set_name(String name) {
		this.name = name;
	}
	public ArrayList<AST> get_args() {
		return args;
	}
	public void set_args(ArrayList<AST> args) {
		this.args = args;
	}
	public ArrayList<AST> get_stms() {
		return stms;
	}
	public void set_stms(ArrayList<AST> stms) {
		this.stms = stms;
	}
	public String toString() {
		return "FuncDecl( "+this.args+ ", "+this.stms+" )";
	}

}
