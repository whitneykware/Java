package lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PivotFasta {
	
	public static int getCount(String s, CharSequence c) 
	{
		int count = s.length() - s.replace(c, "").length();
		return count;
	}
 
    public static void main(String[] args) throws Exception {
 
		List<String> seqIDs = new ArrayList<String>();
		List<String> sequences = new ArrayList<String>();
		StringBuilder seq = new StringBuilder();
		
        boolean id = true;
 
        Scanner fasta = new Scanner(new File("/Users/whitneyware/test.fasta"));
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("/Users/whitneyware/test_fasta_out.txt")));
        
            while (fasta.hasNextLine()) 
            {
                String line = fasta.nextLine().trim();
                if (line.charAt(0) == '>') 
                {
                    if (id)
                    {
                        id = false;
                    }
                    else
                    {
                    	sequences.add(seq.toString());
                    	seq.delete(0, seq.length());
                    }
                    seqIDs.add(line.substring(1));
                } 
                else 
                {
                	seq.append(line);
                }
            }
        
    	sequences.add(seq.toString());
    	seq.delete(0, seq.length());
    	
    	out.write("seqID\tNumA\tNumC\tNumG\tNumT\tSequence\n");
		for(int x = 0; x < seqIDs.size(); x++)
				out.write(seqIDs.get(x) + "\t" + getCount(sequences.get(x), "A") + "\t" + 
			getCount(sequences.get(x), "C") + "\t" + getCount(sequences.get(x), "G") + "\t" + 
			getCount(sequences.get(x), "T") + "\t" + sequences.get(x) + "\n");
		
		fasta.close(); out.close();
    }
}