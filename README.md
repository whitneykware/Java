# Advanced Programming Using Java

Java scripts of the assignments completed in 'Advanced Programming for Bioinformatics' course. 

The goals accomplished in this course were:
- Implementing thread safe multi-threaded algorithms in Java.
- Understanding synchronization of data structures.
- Designing software that avoids common problems in multi-core programming including data corruption, thread
starvation and deadlock.
- Applying these implementations to the large datasets of post-genomic biology.

## Lab 1: 
### RandomDNAseq
Create 1,000 random DNA k-mers and count occurance of specific sequence. 

## Lab 2: 
### AminoAcidQuiz
Amino acid quiz that allows user to choose number of rounds they would like. Quiz ends when rounds are over, user enters 'quit', or if answer is incorrect.

### AminoAcidQuizTimed
Amino acid quiz where the user answers as many questions as possible within 30 seconds. Quiz exits after 30 seconds or if answer is incorrect.

## Lab 3:
### PivotFasta
Parses a FASTA file and outputs a file containing the sequence IDs, number of each nucleotides, and the sequences.

## Lab 4:
### FastaSequence
Static factory method that parses a Fasta file and returns a list of FastaSequence objects. Allows to you access headers, sequences, or the ratio of GC content per the sequence. Can also produce a file of all unique sequences listed by the number of occurances from least to greatest. 

## Lab 5:
### Merchant GUI
Merchant GUI using Java Swing.

## Lab 6:
### Timed Amino Acid Quiz GUI
Amino acid quiz with 30 second timer; answers entered after 30 seconds are not counted. Press cancel to stop quiz.

## Lab 7:
### Prime Number Generator
Multi-threaded GUI that allows the user to enter a number and returns sorted list of prime numbers. Optimizes by using the available number of processors. For longer calculations, the GUI will show updates of the number of primes found so far. 
