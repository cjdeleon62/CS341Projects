public class test {

	public static void changeMe( boolean s) {
		s = false;
	}
	public static void main(String[] args) {
		boolean x = true;
		changeMe(x);
		System.out.println(x);
	}
}