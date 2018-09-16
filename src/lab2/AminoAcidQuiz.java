// Amino acid quiz that allows user to choose number of rounds they would like. 
// Quiz ends when rounds are over, user enters 'quit', or if answer is incorrect.

package lab2;

import java.util.Random;

public class AminoAcidQuiz 
{

	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{"Alanine","Arginine", "Asparagine", 
		"Aspartic acid", "Cysteine",
		"Glutamine",  "Glutamic acid",
		"Glycine" ,"Histidine","Isoleucine",
		"Leucine",  "Lysine", "Methionine", 
		"Phenylalanine", "Proline", 
		"Serine","Threonine","Tryptophan", 
		"Tyrosine", "Valine"};
	
	public static void main(String[] args) 
	{	
		Random rnd = new Random();
		
		int score = 0;
		int rounds = Integer.parseInt(System.console().readLine("Number of rounds: "));
		System.out.println("Please enter the one letter code for each amino acid given.\n"
				+ "Enter 'Quit' to exit the quiz.\n");
		

		for(int x = 0; x < rounds; x++)
		{
			int a = rnd.nextInt(20);
				
			System.out.println(FULL_NAMES[a]);
			String aString = System.console().readLine().toUpperCase();
			String aChar = "" + aString.charAt(0);
				
			if (aString.equals("QUIT"))
			{
				System.out.println("Exiting Quiz.");
				break;
			}
				
			if (aChar.equals(SHORT_NAMES[a]))
			{
				score+=1;
				System.out.printf("Correct!\nScore: %d%n%n", score);
			}
				
			else 
			{
				System.out.printf("Incorrect. The correct one letter code for %s is %s.%n", FULL_NAMES[a], SHORT_NAMES[a]);
				System.out.printf("Final score: %d%n", score);
				break;
			}
		}	
	}
}
