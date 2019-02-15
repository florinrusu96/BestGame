package com.mygdx.game.handlers;

public class MyInput {

	public static int x;
	public static int y;
	public static boolean down;
	public static boolean pdown;
	
	private static boolean[] keys;
	private static boolean[] pkeys;
	
	private static final int NUMKEYS = 2;
	public static final int BUTTONZ = 0;
	public static final int BUTTONX = 1;
	
	static {
		keys = new boolean[NUMKEYS];
		pkeys = new boolean[NUMKEYS];
	}
	
	public static void update() {
		pdown = down;
		for (int i = 0; i < NUMKEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static boolean isDown() { return down; }
	public static boolean isPressed() { return down && !pdown; }
	public static boolean isReleased() { return !down && pdown; }
	
	public static void setKey(int i, boolean b) { keys[i] = b; }
	public static boolean isPressed(int i) { return keys[i] && !pkeys[i]; }
	public static boolean isDown(int i) { return keys[i]; }
}
