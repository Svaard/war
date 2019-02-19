/**
 * @author 005463053
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class War {
	private int[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	private List<Integer> deck;
	private List<Integer> playerDeck;
	private List<Integer> aiDeck;
	private Scanner sc;
	private int playerWins;
	private int computerWins;
	private Random rnd = new Random();
	private int tieScore;
	private List<Integer> tiedCards;
	
	/**
	 * Constructor method that will be run with all the default values we want for the game.
	 */
	public War() {		
		System.out.println(" __      ___   ___  ___   _   __  __ ___");
		System.out.println(" \\ \\    / /_\\ | _ \\/ __| /_\\ |  \\/  | __|");
		System.out.println("  \\ \\/\\/ / _ \\|   / (_ |/ _ \\| |\\/| | _|");
		System.out.println("   \\_/\\_/_/ \\_\\_|_\\\\___/_/ \\_\\_|  |_|___| \n");
		System.out.println("You are playing the card game war. Please choose an option: \n");		
		sc = new Scanner(System.in);
		initializeDeck();		
		mainMenu();
	}
	
	/**
	 * Initialize a deck of 52 cards. Use four each of the value 1-13.
	 */	
	public void initializeDeck() {
		tieScore = 0;
		tiedCards = new ArrayList<Integer>();
		deck = new ArrayList<Integer>();
		for (int i = 0; i < cards.length; i++) {			
			deck.add(cards[i]);
			deck.add(cards[i]);
			deck.add(cards[i]);
			deck.add(cards[i]);						
		}		
		splitDeck(shuffleDeck());			
	}
	
	/**
	 * Take newly created deck and shuffle it by randomly selecting two elements and switching them
	 * @return Shuffled deck
	 */
	public List<Integer> shuffleDeck() {
		
		List<Integer> shuffledDeck = new ArrayList<Integer>();
		
		int size = deck.size();
		for (int i = 0; i < size; i++) {
			int r = rnd.nextInt(deck.size());
			shuffledDeck.add(deck.remove(r));
		}
		return shuffledDeck;
	}
	
	/**
	 * Pass in the shuffled deck, remove half of it and place into a new deck
	 */
	public void splitDeck(List<Integer> toSplit) {
		 playerDeck = new ArrayList<Integer>();
//		 playerDeck.addAll(toSplit.subList(0, 26));
		 aiDeck = new ArrayList<Integer>();
//		 aiDeck = toSplit.subList(26,  52);
		 
		 for (int i = 0; i < toSplit.size() / 2; i++) {
			 playerDeck.add(toSplit.get(i));
			 aiDeck.add(toSplit.get(i + toSplit.size() / 2));
		 }
	}
	
	/**
	 * Returns sorted deck that has been passed in using bubble sort.
	 * @param ArrayList is the deck we are making a copy of.
	 * @return playerCopy is the sorted copy of the deck we passed in.
	 */
	public List<Integer> sortDeck(List<Integer> playerDeck2) {
		List<Integer> playerCopy = new ArrayList<Integer>();
		playerCopy.addAll(playerDeck2);
		 int n = playerCopy.size();  
	        int temp = 0;  
	         for (int i = 0; i < n; i++){  
	                 for (int j = 1; j < (n - i); j++){  
	                          if (playerCopy.get(j - 1) > playerCopy.get(j)) {   
	                                 temp = playerCopy.get(j - 1);  
	                                 playerCopy.set(j - 1, playerCopy.get(j));  
	                                 playerCopy.set(j, temp);
	                          }  	                          
	                 }  
	         }
	         return playerCopy;
	}
	
	/**
	 * Prints contents of the deck in ascending order, call sortDeck method in here
	 */
	public void printDeck() {
		System.out.println(playerDeck);
	}
	/**
	 * Places battle cards at bottom of winner's deck. Removes cards from losers hand.
	 * @param card1 Passes in player card to distribute.
	 * @param card2 Passes in computer card to distribute.
	 * @param winnerDeck Passes in the winner's deck that we will be distributing the cards to.
	 */
	public void distributeCards(int card1, int card2, List<Integer> winnerDeck) {
		winnerDeck.add(card1);
		winnerDeck.add(card2);
	}
	
	/**
	 * Plays a single round, compare top cards and determine winner.
	 */
	public void battle() {
		if (playerDeck.size() == 0) {
			System.out.println();
		}
		int playerCard = playerDeck.remove(0);
		int aiCard = aiDeck.remove(0);
		if (playerCard < aiCard) {
			if (tieScore > 0) {
				computerWins += tieScore;
				System.out.println("You were defeated in the tiebreaker and lost a total of " + (tieScore + 1) + " battles. \n");
				tieScore = 0;
				aiDeck.add(aiCard);
				aiDeck.add(playerCard);
				aiDeck.addAll(tiedCards);
				tiedCards.clear();				
			}
			else {
				computerWins++;
				System.out.println("You lost a battle! \n");
				distributeCards(playerCard, aiCard, aiDeck);
			}
		}
		else if (playerCard > aiCard) {
			if (tieScore > 0) {
				playerWins += tieScore;
				System.out.println("You won the tiebreaker and a total of " + (tieScore + 1) + " battles. \n");
				tieScore = 0;
				playerDeck.add(aiCard);
				playerDeck.add(playerCard);
				playerDeck.addAll(tiedCards);
				tiedCards.clear();
			}
			else {
				playerWins++;
				System.out.println("You won a battle! \n");
				distributeCards(playerCard, aiCard, playerDeck);
			}
		}
		else {
			tiedCards.add(aiCard);
			tiedCards.add(playerCard);
			tieScore++;
			battle();
		}
	}
	
	/**
	 * Calculate and display the percentage of player wins.
	 */
	public void displayStatistics() {
		if (playerWins > 0 || computerWins > 0) {
			double percentageWins = (double) playerWins / (playerWins + computerWins);
			System.out.println("You have won " + (percentageWins*100) + "% of the time. \n");
		}
		else {
			System.out.println("You have not played any games yet. \n");
		}
	}
	
	/**
	 * Displays the number of cards in both player and computer decks.
	 */
	public void displayScore() {
		System.out.println(playerDeck.size() + "\n");		
	}
	
	/**
	 * Prints menu options for player to choose from.
	 * 1. Plays one round of war
	 * 2. Displays the player and computer "score" as their deck sizes
	 * 3. Displays the Player's cards in ascending order
	 * 4. Displays the percentage of battles the player has won
	 * 5. Quits the game
	 */
	public void printMenu() {
		System.out.println("1) Play");
		System.out.println("2) Display Scores");
		System.out.println("3) Display Cards");
		System.out.println("4) Win Percentage");
		System.out.println("5) Quit");
	}
	/**
	 * Prints out the main menu and checks to make sure user options are within range.	
	 */
	public void mainMenu() {
		printMenu();
		int chose = -1;
		while (true) {
			chose = sc.nextInt();
			if (chose > 0 && chose < 6) {
				if (chose == 1) {
					if (playerDeck.size() > 0) {
						battle();
					}
					else {
						System.out.println("You have no more cards to play war with. Game Over.\n");
					}
				}
				else if (chose == 2) {
					System.out.println("Your deck size is: " + playerDeck.size());
					System.out.println("The computer's deck size is: " + aiDeck.size());
				}
				else if (chose == 3) {
				/*
				 * This would print out the players deck from top to bottom.
				 * 
					for (int c = playerDeck.size() - 1; c >= 0; c--) {
						System.out.print(playerDeck.get(c) + " ");
					}
				*/
					System.out.println(sortDeck(playerDeck));
				}
				else if (chose == 4) {
					displayStatistics();
				}
				else if (chose == 5) {
					System.out.println("Goodbye.");
					break;
				}				
			}
			else {
				System.out.println("Please choose a valid option: ");
				mainMenu();
			}			
		}
	}
}
