package april.twitterbot.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MarkovChain {

	static StringBuilder sentence = new StringBuilder();

	public static String getSentence() {
		sentence.delete(0, sentence.length());
		try {
			markovChain(addFileToArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String print = sentence.toString();

		return print;

	}

	public static ArrayList<String> addFileToArray() throws Exception {
		String line = null;
		ArrayList<String> arraylist = new ArrayList<String>();

		BufferedReader bufferedreader = instantiateFileReader();

		while ((line = bufferedreader.readLine()) != null) {
			int stringArrayIndex = line.length();
			String[] words = new String[stringArrayIndex];
			
			words = line.split(" ");
			generateArrayList(words, arraylist);
		}
		
		bufferedreader.close();
		return arraylist;
	}

	public static BufferedReader instantiateFileReader() throws FileNotFoundException {
		FileReader reader = new FileReader("/Users/AJ/Desktop/Folder/markovLib.txt");
		BufferedReader bufferedreader = new BufferedReader(reader);
		return bufferedreader;
	}

	private static void generateArrayList(String[] words, ArrayList<String> arraylist) {
		for (int j = 0; j < words.length; j++) {
			if (!words[j].equalsIgnoreCase(""))
				arraylist.add(words[j]);
		}
	}

	public static void markovChain(ArrayList<String> words) {
		HashMap<String, ArrayList<String>> wordProbability = new HashMap<String, ArrayList<String>>();

		String key = "";
		for (int i = 0; i < words.size() - 2; i++) {
			key = words.get(i) + " " + words.get(i + 1);

			if (wordProbability.containsKey(key)) {
				wordProbability.get(key).add(words.get(i + 2));
			} else {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(words.get(i + 2));
				wordProbability.put(key, temp);
			}
		}

		sentenceGenerator(words, wordProbability);
	}

	public static void sentenceGenerator(ArrayList<String> words, HashMap<String, ArrayList<String>> wordProbability) {
		
		Random random = generateSeed();
		
		int j = random.nextInt(1000);
		
		String seedText = words.get(j) + " " + words.get(j + 1);
		String seedTwo = words.get(j + 1);
		String randomWord;

		int randomNumber, arrayMax;

		int i = 1, charCount = 0;
		while ((i < 2) & (charCount < 140)) {

			ArrayList<String> temp = wordProbability.get(seedText);
			arrayMax = temp.size();

			randomNumber = random.nextInt(arrayMax);
			randomWord = temp.get(randomNumber);
			sentence.append(randomWord + " ");

			seedText = seedTwo + " " + randomWord;
			seedTwo = randomWord;
			charCount += seedTwo.length() + 1;

			char end = seedTwo.charAt(seedTwo.length() - 1);
			if (end == '!' || end == '?' || end == '.') {
				System.out.print("\n");
				i++;
			}

		}
		System.out.println(charCount);
	}

	public static Random generateSeed() {
		Calendar calendar = Calendar.getInstance();
		long seed = calendar.getTimeInMillis();
		Random random = new Random(seed);
		return random;
	}

}
