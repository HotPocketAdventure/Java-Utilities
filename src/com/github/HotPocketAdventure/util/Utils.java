package com.github.HotPocketAdventure.util;


/**
 * A miscellaneous utilities class. 
 * <br>It includes methods to get various random values, String utilities, etc.
 * 
 * @version 1-2
 * @author Michael Bradley
 */
public class Utils {

	public static final char[] vowels = {'a', 'e', 'i', 'o', 'u'};
	
	/**
	 * Ends the program with a specified error message.
	 * @param msg A {@link String} to be appended to "Error: "
	 */
	public static void die(String msg) {
		System.err.println("Error: " + msg);
		System.exit(1);
	}

	/**
	 * @param low Inclusive
	 * @param high Inclusive
	 * @return A randomly generated {@code int} in the range [low, high]
	 */
	public static int randomInt(int low, int high) {
		if (low < 0) { low--; }
		if (high < 0) { high--; }
		return (int)((high - low + 1) * Math.random() + low);
	}
	
	/**
	 * @param min The minimum index. Inclusive
	 * @param length The length of an array. Exclusive
	 * @return A randomly generated {@code int} in the range [min, length) 
	 */
	public static int randomIndex(int min, int length) {
		return randomInt(min, length - 1);
	}
	
	/**
	 * @param length The length of an array. Exclusive
	 * @return A randomly generated {@code int} in the range [0, length) 
	 */
	public static int randomIndex(int length) {
		return randomIndex(0, length);
	}

	/**
	 * @param low Inclusive
	 * @param high Inclusive
	 * @return A randomly generated {@code long} in the range [low, high]
	 */
	public static long randomLong(long low, long high) {
		return (long)((high - low + 1) * Math.random() + low);
	}

	/**
	 * 
	 * @param low Inclusive
	 * @param high Inclusive
	 * @param sigfig The number of decimal places the returned value will be randomized to
	 * @return A randomly generated {@code double} in the range [low, high]
	 */
	public static double randomDub(double low, double high, int sigfig) {
		double mult = Math.pow(10, sigfig);
		double nLow = low * mult;
		double nHigh = high * mult;
		double ret = randomInt((int)nLow, (int)nHigh);

		return ret / mult;
	}
	
	/**
	 * @param low Inclusive
	 * @param high Inclusive
	 * @param sigfig The number of decimal places the returned value will be randomized to
	 * @return A randomly generated {@code float} in the range [low, high]
	 */
	public static float randomFloat(float low, float high, int sigfig) {
		return (float)randomDub(low, high, sigfig);
	}
	
	/**
	 * @return A random {@code boolean} value.
	 */
	public static boolean randomBool() {
		if (randomInt(0, 1) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * @return A random {@code char} in the range of [A, Z].
	 */
	public static char randomUpperLetter() {
		int low = 'A';
		int high = 'Z';

		return (char)randomInt(low, high);
	}

	/**
	 * @return A random {@code char} in the range of [a, z].
	 */
	public static char randomLowerLetter() {
		int low = 'a';
		int high = 'z';

		return (char)randomInt(low, high);
	}

	/**
	 * @return A random {@code char} in the range of [A, Z] or [a, z].
	 */
	public static char randomLetter() {
		if (randomBool()) {
			return randomLowerLetter();
		}
		
		return randomUpperLetter();
	}
	
	/**
	 * @return A random lower case vowel.
	 */
	public static char randomVowel() {
		return vowels[randomInt(0, vowels.length - 1)];
	}
	
	/**
	 * @return A random lower case consonant.
	 */
	public static char randomConsonant() {
		char ret;
		
		//WARNING: Unoptimized code ahead.
		do {
			ret = randomLowerLetter();
		} while (isVowel(ret));
		
		return ret;
	}

	/**
	 * @return A pronounceable name generated randomly piece by piece that is at least two characters long.
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
	 * Case insensitive.
	 * @param c The character to test
	 * @return {@code true} if c is a vowel, {@code false} otherwise
	 */
	public static boolean isVowel(char c) {
		for (char ch : vowels) {
			if (c == ch || c == Character.toUpperCase(ch)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * @param times Number of times to repeat
	 * @param c The character to repeat
	 * @return A {@link String} of repeated characters.
	 */
	public static String repeatCharacter(int times, char c) {
		return new String(new char[times]).replace('\0', c);
	}
	
	/**
	 * @param size The length of the returned array
	 * @param max The value all elements should add up to
	 * @return A random array of {@code int}s totaling max.
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
	 * @param values An {@code int} array of weights
	 * @return The index of the element that was chosen within the range [0, {@code values.length})
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
	 * @param arr An array of {@code int}s
	 * @return The sum of all integers in the array.
	 */
	public static int total(int[] arr) {
		int ret = 0;
		for (int x : arr) {
			ret += x;
		}
		
		return ret;
	}
	
	/**
	 * Converts a {@code boolean} value to a more user friendly String.
	 * @param value The {@code boolean} value to be converted
	 * @return "Yes" or "No" depending on value.
	 */
	public static String readableBool(boolean value) {
		return value ? "Yes" : "No";
	}
	
	/**
	 * Convenience method.
	 * <br>See: {@link #appendTo(String[], String)
	 * @param strs The {@link String} array to be appended 
	 * @param toApp The {@code char} that will be appended to each element
	 * @return The appended String array.
	 */
	public static String[] appendTo(String[] strs, char toApp) {
		return appendTo(strs, String.valueOf(toApp));
	}
	
	/**
	 * Appends specified string to each element of an array.
	 * @param strs The {@link String} array to be appended 
	 * @param toApp The String that will be appended to each element
	 * @return The appended String array.
	 */
	public static String[] appendTo(String[] strs, String toApp) {
		String[] ret = new String[strs.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = strs[i] + toApp;
		}
		
		return ret;
	}
	
	/**
	 * Appends each element of the toApp array to the strs array respectively.
	 * @param strs The {@link String} array to be appended 
	 * @param toApp A parallel array of Strings that will be appended
	 * @return The appended String array.
	 * @throws IllegalArgumentException If the lengths of strs and toApp are not equal.
	 */
	public static String[] appendTo(String[] strs, String[] toApp) throws IllegalArgumentException {
		if (strs.length != toApp.length){
			throw new IllegalArgumentException("The length of strs (" + strs.length + ") is not equal to the length"
					+ " of toApp (" + toApp.length + ").");
		}
		
		String[] ret = new String[strs.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = strs[i] + toApp[i];
		}
		
		return ret;
	}
}
