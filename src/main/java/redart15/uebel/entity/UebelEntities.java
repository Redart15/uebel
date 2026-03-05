package redart15.uebel.entity;

import static redart15.uebel.UebelMod.*;

public class UebelEntities {
	private static boolean init = false;
	private UebelEntities(){}
	public static void init(){
		if(init) return;
		init = true;
	}
}
