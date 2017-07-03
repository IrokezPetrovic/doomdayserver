import org.junit.Test;

public class StringTest {
	@Test
	public void test1(){
		String s = "TRIGGER  1     3  4 1    3";
		String t = s.replaceAll("( {2,})", "_");
		System.out.println(t);;
	}
}
