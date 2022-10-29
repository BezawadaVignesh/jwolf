package interptr;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.*;
import parser.Parser;
import interptr.Interptr;

public class InterptrTest {

	
	public static void main(String[] args) {
		String everything = null;
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
			System.out.println(i.interptr(a));
		}
		/*/System.out.println("\n"+i.interptr(p.parse()));
		p.toParseTxt("a");
		p.callFirst();
		System.out.println(i.interptr(p.parse()));
		p.toParseTxt("c");
		p.callFirst();
		System.out.println(i.interptr(p.parse()));*/
	}

}
