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
		"Aspartic acid", "Aysteine",
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
		System.out.println("Please enter the one letter abbreviation for each amino acid given. "
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
					System.out.println("Correct!\n");
					score+=1;
				}
				
				else 
				{
					System.out.printf("Incorrect. Correct abbreviation for %s is %s.%n", FULL_NAMES[a], SHORT_NAMES[a]);
					System.out.printf("Score: %d%n", score);
					break;
				}
			}	
	}
}
