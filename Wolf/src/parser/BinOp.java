package parser;
import token.Token;
import parser.OpType;
import parser.AST;

public class BinOp extends AST{
	private AST left, right;
	private Token op;
	public BinOp(AST left, Token op, AST right){
		super(OpType.BINOP);
		this.set_left(left);
		this.op = op;
		this.right = right;
	}
	
	public AST get_left() {
		return this.left;
	}
	public Token get_op() {
		return this.op;
	}
	public AST get_right() {
		return this.right;
	}
	void set_left(AST left) {
		this.left = left;
	}
	void set_op(Token op) {
		this.op = op;
	}
	void set_right(AST right) {
		this.right = right;
	}
	
	public String toString() {
		return "BinOp( "+this.get_left()+", "+this.op+", "+this.right+") ";
	}
}
