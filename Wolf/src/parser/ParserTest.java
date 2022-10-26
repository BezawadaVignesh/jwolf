package parser;

import parser.Parser;

public class ParserTest {
	public static void main(String[] args) {
		Parser p = new Parser("a = -(2 + 3)*5");
		System.out.println(p.parse());
	}
}
