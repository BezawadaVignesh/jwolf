package parser;

import java.util.ArrayList;

public class FuncDecl extends AST{
	private ArrayList<AST>  stms;
	private String name;
	private ArrayList<String> args;
	FuncDecl(String name, ArrayList<String> args, ArrayList<AST> stms) {
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
	public ArrayList<String> get_args() {
		return args;
	}
	public void set_args(ArrayList<String> args) {
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
