package redart15.uebel.config;

import static redart15.uebel.UebelMod.*;

public class UebelConfig {
	private static int BLOCK_ID_STARTING_FROM = 11000;
	private static int ITEM_ID_STARTING_FROM = 27000;
	public static int currentBlockID;
	public static int currentItemID;

	private static boolean init = false;
	private UebelConfig(){}
	public static void init(){
		if(init) return;
		init = true;
		loadProperties();
	}

	private static void loadProperties(){
		currentItemID = ITEM_ID_STARTING_FROM;
		currentBlockID = BLOCK_ID_STARTING_FROM;
	}


	public static int nextItemID() {
		return currentItemID++;
	}

	public static int nextBlockID() {
		return currentBlockID++;
	}
}
