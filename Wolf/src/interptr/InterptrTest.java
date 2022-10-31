package interptr;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.*;
import java.util.Scanner;

import parser.Parser;
import interptr.Interptr;

public class InterptrTest {

	
	public static void main(String[] args) {
		/*String everything = null;
		try(BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\vigne\\OneDrive\\Desktop\\jwolf-main11\\Wolf\\src\\interptr\\test.wl"))) {
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
		Parser p = new Parser(everything);
		
		Interptr i = new Interptr();
		p.callFirst();
		
		for(parser.AST a: p.getStms()) {
			//System.out.println(a);
			i.interptr(a);
		}*/
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
				System.out.println("bye wooof!");
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
