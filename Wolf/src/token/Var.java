package token;

public class Var {
	private String name;
	private TokenType type;
	private Object value;
	 Var(String name, TokenType type,Object value){
		 this.name = name;
		 this.type = type;
		 this.value = value;
	 }
	
	String get_name() {
		return this.name;
	}
	
	Object get_value() {
		return this.value;
	}
	 
	public String toString() {
		return "Var( " + this.name + " "+this.value+" )";
	}

	public TokenType get_type() {
		return type;
	}

	public void set_type(TokenType type) {
		this.type = type;
	}
}
