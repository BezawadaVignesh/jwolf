package token;

public enum TokenType {
	
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/"),
    MODLUS("%"),
    
    EQUAL("="),
    COMMA(","),
    COLON(":"),
    SEMICOLON(";"),
    PERIOD("."),
    
    NOT("!"),
    EQUALEQUAL("=="),
	NOTEQUAL("!="),
	LESS("<"),
	LESSEQUAL("<="),
	GREATER(">"),
	GREATEREQUAL(">="),
	AND("and"),
	OR("or"),
	NEWLINE("\\n"),
    
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
    TUPLE("TUPLE"),
    LIST("LIST"),
    DICT("DICT"),
    SET("SET"),
    PACKED("PACKED"),
    
    RETURN("RETURN"),
    FUNC("DEF"),
    JFUNC("JFUNC"),
    CLASS("CLASS"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    WHILE("WHILE"),
    
    TYPE("TYPE"),
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
	public String toString() {
		return "<WolfObj '"+this.get_typeValue()+"'>";
	}
}
