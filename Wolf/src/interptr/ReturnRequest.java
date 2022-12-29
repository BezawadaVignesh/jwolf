package interptr;

import java.util.ArrayList;
import wolfObj.WolfObj;


public class ReturnRequest extends RuntimeException{
	private WolfObj args;
	public ReturnRequest(String msg, WolfObj objs) {
		super(msg);
		this.set_args(objs);
	}
	public WolfObj get_args() {
		return args;
	}
	public void set_args(WolfObj args) {
		this.args = args;
	}
}
