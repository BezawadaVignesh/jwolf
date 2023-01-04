package token;

public class WINT {
	WINT(String str){
		for(char ch:str.toCharArray())
			Byte.parseByte(ch+"");
	}
}
