package token;

public enum TokenType {
	
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/"),
    
    EQUAL("="),
    
    COLON(":"),
    SEMICOLON(";"),
    PERIOD("."),
    
    EQUALEQUAL("=="),
	NOTEQUAL("!="),
	LESS("<"),
	LESSEQUAL("<="),
	GREATER(">"),
	GREATEREQUAL(">="),
    
    OPENPARN("("),
    CLOSEPARN(")"),
    OPENBRACE("{"),
    CLOSEBRACE("}"),
    OPENBRACKET("["),
    CLOSEBRACKET("]"),
    
    VAR("VAR"),
    NUM("NUM"),
    CONSTSTR("STR"),
    INT("INT"),
    DOUBLE("DOUBLE"),
    BOOL("BOOL"),
    
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    WHILE("WHILE"),
    
    NONE("NONE"),
    EOF("EOF");
	
	private String typeValue;
	TokenType(String string) {
		this.typeValue = string;
	}
	
	String get_typeValue() {
		return typeValue;
	}
	
	void set_typeValue(String str) {
		this.typeValue = str;
	}
}
