package parser;

import parser.OpType;

public class AST {
	public OpType type;
	AST(OpType type){
		this.type = type;
	}
}
