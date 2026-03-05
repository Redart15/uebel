package redart15.uebel.block;

public class UebelBlockDetails {
	private static boolean init = false;
	private UebelBlockDetails(){}
	public static void init(){
		if(init) return;
		init = true;
	}
}
