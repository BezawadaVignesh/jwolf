package interptr;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.*;
import java.util.Scanner;

import parser.Parser;
import token.TokenType;
import interptr.Interptr;

public class InterptrTest {

	
	public static void main(String[] args) {
		if(args.length >= 1) {
			String everything = null;
			try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
	
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    everything = sb.toString();
			}catch(Exception e){
				e.printStackTrace();
			}
		    //System.out.println(everything);
			parser.Parser p = new parser.Parser(everything);
			
			
			Interptr i = new Interptr();
			p.callFirst();
			//System.out.println("Parsing complete ");
			for(parser.AST a: p.getStms()) {
				
				i.interptr(a);
			}
			if(p.getCurrentToken().get_type() != TokenType.EOF) throw new parser.ParserError("Invalid syntax");
		}
		else {
			Scanner sc = new Scanner(System.in);
			Interptr i = new Interptr();
			Parser p = new Parser("");
			String inp;
			boolean running = true;
			while(running) {
				System.out.print(">>>");
				inp = sc.nextLine();
				if(inp.equalsIgnoreCase("exit")) {
					running = false;
					System.out.println("bye");
					break;
				}
				p.toParseTxt(inp);
				try {
				p.callFirst();
				
				System.out.println(i.interptr(p.parse()));
				}catch(RuntimeException e) {
					System.out.println(e);
				}
			}
		}
	}

}
/*ab = 10
def fun(n){
	def f(){
		return 0
	}
	print(ab)
	ab = [1,2,3]
	print("ab in function:",ab)
	while n != 0{
		if(n%2 == 0){
			print(n)
		}
		n = n-1
	}
	return a
}

b= {
	1:2,3:4,"Hi":"Bye"
	}

a = [1,2,3,"Hi", fun]
x = fun
if __name__ == "__main__" {
	print(b)
	print(fun(20))
	print(b["Hi"])
	print(a.count(1))
	print(ab)
}*/
/*
 def fun(){
	return 0
}

b= {
	1:2,3:4,"Hi":"Bye"
	}

a = [1,2,3,"Hi", fun]
x = fun
if __name__ == "__main__" 
{
	print(b)
	a[0] = 1
	print(a.count(x))
}
 */
/*
 * C:\Users\vigne\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.4.v20221004-1257\jre\bin\javaw.exe -p "C:\Users\vigne\OneDrive\Desktop\jwolf-main11\Wolf\bin -m Wolf/interptr.InterptrTest""*/
