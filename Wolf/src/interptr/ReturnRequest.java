package interptr;

import java.util.ArrayList;

public class ReturnRequest extends RuntimeException{
	private token.WolfObj args;
	public ReturnRequest(String msg, token.WolfObj objs) {
		super(msg);
		this.set_args(objs);
	}
	public token.WolfObj get_args() {
		return args;
	}
	public void set_args(token.WolfObj args) {
		this.args = args;
	}
}
