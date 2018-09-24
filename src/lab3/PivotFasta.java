package lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PivotFasta 
{
	
	public static int getCount(String s, CharSequence c) 
	{
		int count = s.toUpperCase().length() - s.toUpperCase().replace(c, "").length();
		return count;
	}
 
    public static void main(String[] args) throws Exception 
    {

        BufferedReader fasta = new BufferedReader(new FileReader(new File("/Users/whitneyware/test.fasta")));
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("/Users/whitneyware/test_fasta_out.txt")));
        
		List<String> seqIDs = new ArrayList<String>();
		List<String> sequences = new ArrayList<String>();
		
		StringBuilder seq = new StringBuilder();
		boolean id = true;
        
            for(String line = fasta.readLine().trim(); line != null; line = fasta.readLine())
            {
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
        //adds last sequence from string builder
    	sequences.add(seq.toString());
    	seq.delete(0, seq.length());
    	
    	out.write("seqID\tNumA\tNumC\tNumG\tNumT\tSequence\n");
		for(int x = 0; x < seqIDs.size(); x++)
			out.write(seqIDs.get(x) + "\t" + getCount(sequences.get(x), "A") + "\t" + 
			getCount(sequences.get(x), "C") + "\t" + getCount(sequences.get(x), "G") + "\t" + 
			getCount(sequences.get(x), "T") + "\t" + sequences.get(x) + "\n");
		
		fasta.close(); out.flush(); out.close();
    }
}