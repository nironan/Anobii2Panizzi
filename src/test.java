
public class test {

	public static void main(String args[]) {
		String a = "<b>Salinger, J. D.</b><br>Il giovane Holden / J. D. Salinger<br>2001";
		
		String[] aStr = a.split("<br>");
		System.out.println("aStr: " + aStr[0]);
		System.out.println("aStr: " + aStr[1]);
		System.out.println("aStr: " + aStr[2]);
	}
}
