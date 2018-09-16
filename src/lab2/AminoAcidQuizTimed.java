// Amino acid quiz where the user answers as many questions as possible within 30 seconds.
// Quiz exits after 30 seconds or if answer is incorrect.

package lab2;

import java.util.Random;

public class AminoAcidQuizTimed
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
	
		System.out.println("Please enter the one letter code for each amino acid given.\n"
				+ "Quiz ends after 30 seconds or one incorrect answer.\n");
		
		int score = 0;
		long start = System.currentTimeMillis();
		long end = start + 30000;
		
		while(System.currentTimeMillis() < end)
		{			
			int a = rnd.nextInt(20);
			System.out.println(FULL_NAMES[a]);
			String aString = System.console().readLine().toUpperCase();
			String aChar = "" + aString.charAt(0);
					
			if(System.currentTimeMillis() >= end)
			{
				System.out.printf("Out of time. Final score: %d%n", score);
				break;
			}
			
			if (aChar.equals(SHORT_NAMES[a]))
			{
				System.out.println("Correct!\n");
				score+=1;
			}
			
			else
			{
				System.out.printf("Incorrect. The correct one letter code for %s is %s.%n"
						+ "Final score: %d%n", FULL_NAMES[a], SHORT_NAMES[a], score);
				break;
			}

		}
	
	}		
}

