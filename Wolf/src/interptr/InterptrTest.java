package interptr;
import parser.Parser;
import interptr.Interptr;
public class InterptrTest {

	public static void main(String[] args) {
		Parser p = new Parser("a =  False == False");
		
		Interptr i = new Interptr();
		p.callFirst();
		System.out.println("\n"+i.interptr(p.parse()));
		p.toParseTxt("a");
		p.callFirst();
		System.out.println(i.interptr(p.parse()));
		/*p.toParseTxt("c");
		p.callFirst();
		System.out.println(i.interptr(p.parse()));*/
	}

}
