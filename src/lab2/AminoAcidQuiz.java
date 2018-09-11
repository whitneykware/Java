package lab2;

import java.util.Random;

public class AminoAcidQuiz 
{

	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	
	public static void main(String[] args) 
	{	
		Random rnd = new Random();
		
		System.out.println("Number of rounds: ");
		String s = System.console().readLine();
		int questions = Integer.parseInt(s);
		
		int score = 0;
		
		System.out.println("Please enter the one letter abbreviation for each amino acid given.");
		
		
		for(int x = 0; x < questions; x++)
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
				System.out.println("Correct!");
				score+=1;
			}
			else 
			{
				System.out.printf("Incorrect. Correct abbreviation for %s is %s", FULL_NAMES[a], SHORT_NAMES[a]);
				System.out.format("Score: %d", score);
				break;
			}
		}
		

	}

}
