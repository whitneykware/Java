package lab1;

import java.util.Random;

public class randomDNAseq 
{

	public static void main(String[] args) 
	{	
		String bases = "ACGT";
		int count = 0;
				
		for( int x = 0; x < 1000; x++ )
			{
				String s = "";
				Random rnd = new Random();
				for(int i = 0; i < 3; i++)
				{
					char seq = bases.charAt(rnd.nextInt(4));
					s+=seq;
				}
				if(s.equals("AAA")) 
				{
					count+=1;
				}
				System.out.println(s);
			}
		System.out.println(count);
	}

}