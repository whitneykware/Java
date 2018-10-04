package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FastaSequence
{
	private final String header;
	private final String sequence;
	
	public FastaSequence(String header, String sequence)
	{
		this.header = header;
		this.sequence = sequence;
	}
	
	public String getHeader()
	{
		return this.header;
	}
	
	public String getSequence()
	{
		return this.sequence;
	}
	
	public float getGCRatio()
	{
		String dnaSeq = this.sequence.toUpperCase();
		int seqLen = dnaSeq.length();
		double GC = 0;
		
		for(int i = 0; i < seqLen; i++)
		{
			if(dnaSeq.charAt(i)== 'G' || dnaSeq.charAt(i)=='C') 
			{
				GC++;
			}
		}
		return (float) (GC / seqLen);
	}
	

	//static factory method that returns list of FastaSequence objects
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{	
		BufferedReader fasta = new BufferedReader(new FileReader(new File(filepath)));
		
		List<FastaSequence> fastaList = new ArrayList<FastaSequence>();
		
		List<String> header = new ArrayList<String>();
		List<String> sequence = new ArrayList<String>();
		
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
                    	sequence.add(seq.toString());
                    	seq.delete(0, seq.length());
                    }
                    header.add(line.substring(1));
                } 
                else 
                {
                	seq.append(line);
                }
            }
    	sequence.add(seq.toString());
    	seq.delete(0, seq.length());
    	
    	fasta.close();
    	
    	for(int x = 0; x < header.size(); x++)
    	{
    		FastaSequence temp = new FastaSequence(header.get(x), sequence.get(x));
    		fastaList.add(temp);
    	}
    	return fastaList;
    	
	}
 
	public static void writeUnique(String inFile) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(inFile);		
        //BufferedWriter out = new BufferedWriter(new FileWriter(new File(outFile)));
        
        List<String> allSeqs = new ArrayList<String>();
        
		for( FastaSequence fs : fastaList)
		{
			allSeqs.add(fs.getSequence().toUpperCase());
		}
		
		Set<String> uniqueSeqs = new HashSet<String>(allSeqs);
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		
		for( String u : uniqueSeqs)
		{
			int count = Collections.frequency(allSeqs, u);
			countMap.put(u, count);
		}
		
		System.out.println(countMap);
		System.out.println(allSeqs);
		System.out.println(uniqueSeqs);
		
	}
	
	public static void main(String[] args) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/whitneyware/test.fasta");

		for( FastaSequence fs : fastaList)
		{
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGCRatio());
			System.out.println();
		}
		FastaSequence.writeUnique("/Users/whitneyware/test.fasta");

	}

}
