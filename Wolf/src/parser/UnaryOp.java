package parser;
import token.Token;
import parser.OpType;
import parser.AST;

public class UnaryOp extends AST{
	private AST right;
	private Token op;
	public UnaryOp(Token op, AST right){
		super(OpType.UNARYOP);
		this.op = op;
		this.right = right;
	}
	
	public Token get_op() {
		return this.op;
	}
	public AST get_right() {
		return this.right;
	}
	
	void set_op(Token op) {
		this.op = op;
	}
	void set_right(AST right) {
		this.right = right;
	}
	
	public String toString() {
		return "UnaryOp( "+this.op+", "+this.right+") ";
	}
}
