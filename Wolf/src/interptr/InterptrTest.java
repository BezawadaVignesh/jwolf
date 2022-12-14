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
			
			for(parser.AST a: p.getStms()) {
				//System.out.println(a);
				i.interptr(a);
			}
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
/*
 * C:\Users\vigne\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.4.v20221004-1257\jre\bin\javaw.exe -p "C:\Users\vigne\OneDrive\Desktop\jwolf-main11\Wolf\bin -m Wolf/interptr.InterptrTest""*/
