package token;

public class Var {
	private String name;
	private Object value;
	 Var(String name, Object value){
		 this.name = name;
		 this.value = value;
	 }
	
	String get_name() {
		return this.name;
	}
	
	Object get_value() {
		return this.value;
	}
	 
	public String toString() {
		return "Var( " + this.name + " )";
	}
}
