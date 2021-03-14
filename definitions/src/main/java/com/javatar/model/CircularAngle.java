package com.javatar.model;

public class CircularAngle {
	public static final int[] SINE = new int[2048]; // sine angles for each of the 2048 units, * 65536 and stored as an int
	public static final int[] COSINE = new int[2048]; // cosine
	private static final double UNIT = Math.PI / 1024d; // How much of the circle each unit of SINE/COSINE is

	static {
		for (int i = 0; i < 2048; ++i) {
			SINE[i] = (int) (65536.0D * Math.sin((double) i * UNIT));
			COSINE[i] = (int) (65536.0D * Math.cos((double) i * UNIT));
		}
	}
}
