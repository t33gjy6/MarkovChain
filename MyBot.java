package april.twitterbot.java;

import java.util.Scanner;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MyBot {
	public static void main(String[] args) throws TwitterException {
		Scanner input = new Scanner(System.in);

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("")
				.setOAuthConsumerSecret("")
				.setOAuthAccessToken("")
				.setOAuthAccessTokenSecret("");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		twitter.updateStatus(sentenceCheck(input));
		System.out.println("Tweet sent!");
	}

	public static String sentenceCheck(Scanner input) {
		String response, sentence;
		do {
			sentence = MarkovChain.getSentence();
			System.out.println("Your sentence is: " + sentence);
			System.out.print("Confirm (y/n)?: ");
			response = input.next();
		} while (!response.equalsIgnoreCase("y"));

		return sentence;
	}

}
