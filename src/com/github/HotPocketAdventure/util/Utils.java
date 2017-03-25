package com.github.HotPocketAdventure.util;


public class Utils {

	/**
	 * Ends the program with a specified error message.
	 * @param msg
	 */
	public static void die(String msg) {
		System.err.println("Error: " + msg);
		System.exit(1);
	}

	/**
	 * @param low
	 * @param high
	 * @return [low, high]
	 */
	public static int randomInt(int low, int high) {
		if (low < 0) { low--; }
		if (high < 0) { high--; }
		return (int)((high - low + 1) * Math.random() + low);
	}

	/**
	 * @param low
	 * @param high
	 * @return [low, high]
	 */
	public static long randomLong(long low, long high) {
		return (long)((high - low + 1) * Math.random() + low);
	}
	
	/**
	 * @return Either true or false
	 */
	public static boolean randomBool() {
		if (randomInt(0, 1) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * @return [A, Z]
	 */
	public static char randomUpperLetter() {
		int low = 'A';
		int high = 'Z';

		return (char)randomInt(low, high);
	}

	/**
	 * 
	 * @return [a, z]
	 */
	public static char randomLowerLetter() {
		int low = 'a';
		int high = 'z';

		return (char)randomInt(low, high);
	}

	/**
	 * [A, Z] or [a, z]
	 * @return
	 */
	public static char randomLetter() {
		if (randomBool()) {
			return randomLowerLetter();
		}
		
		return randomUpperLetter();
	}
	
	/**
	 * 
	 * @return a lower case vowel
	 */
	public static char randomVowel() {
		char vows[] = {'a', 'e', 'i', 'o', 'u'};
		int high = 4;
		int low = 0;
		
		return vows[randomInt(low, high)];
	}
	
	/**
	 * 
	 * @return a lower case consonant
	 */
	public static char randomConsonant() {
		char ret;
		
		//such shitty code
		do {
			ret = randomLowerLetter();
		} while (isVowel(ret));
		
		return ret;
	}

	/**
	 * 
	 * @param low
	 * @param high
	 * @param sigfig
	 * @return [low, high] to sigfig number of decimal points
	 */
	public static double randomDub(double low, double high, int sigfig) {
		double mult = Math.pow(10, sigfig);
		double nLow = low * mult;
		double nHigh = high * mult;
		double ret = randomInt((int)nLow, (int)nHigh);

		return ret / mult;
	}
	
	/**
	 * 
	 * @param low
	 * @param high
	 * @param sigfig
	 * @return [low, high] to sigfig number of decimal points
	 */
	public static float randomFloat(float low, float high, int sigfig) {
		return (float)randomDub(low, high, sigfig);
	}

	/**
	 * 
	 * @return A name generated randomly piece by piece that is at least two characters long.
	 */
	public static String randomName() {
		int i = 0;
		int rand = randomInt(0, 8);
		String name = "" + randomUpperLetter();
		
		if (name.charAt(i) == 'Q') {
			name += 'u';
			i++;
		}
		
		if (isVowel(name.charAt(i)) && name.charAt(i) != 'u') {
			name += randomConsonant();
			if (name.charAt(i + 1) == 'q') {
				name += 'u';
				name += randomVowel();
				i += 2;
			}
		} else {
			name += randomVowel();
		}
		i++;
		
		if (randomInt(0, 4) == 0) {
			if (randomBool()) {
				name += randomVowel();
				i++;
			}
			return name;
		}

		name += randomConsonant();
		i++;
		
		switch (name.charAt(i)) {
		case 'c':
		case 't':
		case 's':
		case 'p':
			if (rand == 0) {
				name += 'h';
				i++;
			}
			
		case 'b':
		case 'f':
		case 'g':
		case 'k':
		case 'm':
		case 'n':
			if (rand == 1) {
				name += 'l';
				i++;
			} else if (rand == 2) {
				name += 'r';
				i++;
			} else if (rand == 3) {
				name += 'w';
				i++;
			}
			break;
			
		case 'q':
			name += 'u';
			i++;
		}
		
		name += randomVowel();
		i++;
		
		if (randomInt(0, 2) == 0) {
			int rando = randomInt(0, 2);
			
			switch (rando) {
			case 0:
				name += 'l';
				break;
			case 1:
				name += 'r';
				break;
			case 2:
				name += 's';
			}
			i++;
		}
		
		name += randomConsonant();
		i++;
		
		return name;
		
	}

	/**
	 * Case insensitive
	 * @param c
	 * @return True if c is a vowel, false otherwise
	 */
	public static boolean isVowel(char c) {
		switch (c) {
		case 'a':
		case 'A':
		case 'e':
		case 'E':
		case 'i':
		case 'I':
		case 'o':
		case 'O':
		case 'u':
		case 'U':
			return true;
		}

		return false;
	}
	
	/**
	 * 
	 * @param times
	 * @param c
	 * @return a string with times number of c
	 */
	public static String repeatCharacter(int times, char c) {
		String ret = new String();
		for (int i = 0; i < times; i++) {
			ret += c;
		}
		
		return ret;
	}
	
	/**
	 * Yields a random set of size numbers totaling max
	 * @param size
	 * @param max
	 * @return
	 */
	public static int[] randomValues(int size, int max) {
		int index;
		int[] ret = new int[size];
		
		for (int i = 0; i < max; i++) {
			index = randomInt(0, size - 1);
			
			ret[index]++;
		}
		
		return ret;
	}
	
	
	/**
	 * @param values
	 * @return Index of the array that was chosen
	 */
	public static int weightedRandom(int[] values) {
		int i = 0;
		int rand = Utils.randomInt(0, total(values));
		
		while (rand > values[i]) {
			rand -= values[i];
			i++;
		}
		
		return i;
	}
	
	/**
	 * Returns the sum of all integers in the array
	 * @param arr
	 * @return
	 */
	public static int total(int[] arr) {
		int ret = 0;
		for (int x : arr) {
			ret += x;
		}
		
		return ret;
	}
}
