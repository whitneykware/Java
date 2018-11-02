/*
	Amino acid quiz with 30 second timer; answers entered after 30 seconds are not counted.
	Press cancel to stop quiz.
*/

package lab6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class AminoAcidQuizGUI extends JFrame
{
	private static final long serialVersionUID = -6879355666319807094L;

	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{"Alanine","Arginine", "Asparagine", 
		"Aspartic acid", "Cysteine",
		"Glutamine", "Glutamic acid",
		"Glycine", "Histidine","Isoleucine",
		"Leucine", "Lysine", "Methionine", 
		"Phenylalanine", "Proline", 
		"Serine","Threonine","Tryptophan", 
		"Tyrosine", "Valine"};
	
	private static int correct;
	private static int incorrect;
	Random rnd = new Random();
	
	private JTextArea textField = new JTextArea(); 
	private JTextArea resultsField = new JTextArea();
	private JTextArea timeField = new JTextArea();
	private JButton startButton = new JButton("Start Quiz");

	static volatile boolean cancel;
	static volatile Timer myTimer;

	// main screen 
	private void quizStart()
	{
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(resultsField);
		getContentPane().add(textField);
		getContentPane().add(buttonPanel());
		textField.setEditable(false);
		timeField.setEditable(false);
		resultsField.setEditable(false);
		textField.setText("Please enter the one-letter code for each amino acid given. "
				+ "\nQuiz ends after 30 seconds.");
		timeField.setText("Time : ");
	}
	
	// button panel with start button and time
	private JPanel buttonPanel()
	{
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,2));
		buttons.add(timeField);
		buttons.add(startButton);
		startButton.addActionListener(new ActionListener()
		{
			public synchronized void actionPerformed(ActionEvent e)
			{
				cancel = false;
				correct = 0;
				incorrect = 0;
				resultsField.setText("Correct: " + correct + "\nIncorrect: " + incorrect);
				
				SwingUtilities.invokeLater( new Runnable() 
				{
					public void run()
		            {
						setupTimer(30);
		            }
		        });
				
				while (! cancel)
				{
					int a = rnd.nextInt(20);
					textField.setText((FULL_NAMES[a])); 
				    String aChar = JOptionPane.showInputDialog(textField, "Enter the one-letter code.", 
				    		"Amino Acid Quiz", JOptionPane.PLAIN_MESSAGE);
				    
			    	if( aChar == null )   
			    	{
			    		cancel = true;
			    	}
				    
				    if( ! cancel)
				    {
				    	aChar = aChar.toUpperCase();
				    
				    	if (aChar.equals(SHORT_NAMES[a]))
				    	{
				    		correct++;
				    	}
				    	
				    	else
				    	{
				    		incorrect++;
				    	}
				    }
				    resultsField.setText("Correct: " + correct + "\nIncorrect: " + incorrect);
				}
				timeField.setText(" ");
				textField.setText(" ");
				resultsField.setText("Final Score: \nCorrect: " + correct + "\nIncorrect: " + incorrect);
			}
		});
		return buttons;
	}

	private void setupTimer(int secRemaining) 
	{
	      timeField.setText("Time: " + Integer.toString(secRemaining));
	      myTimer = new Timer(1000, new TimerListener(secRemaining));
	      myTimer.start();
	}

	class TimerListener implements ActionListener 
	{
		private int secondsCount;

		public TimerListener(int seconds) 
		{ 
			secondsCount = seconds; 
		}	

		public synchronized void actionPerformed(ActionEvent evt) 
		{
			timeField.setText("Time: " + Integer.toString(secondsCount));
	        secondsCount--;
	        
	        if (secondsCount <= 0 || cancel ) 
	        { 
		       myTimer.stop();
			   cancel = true;
			   startButton.setText("Restart Quiz");
			   timeField.setText(" ");
	        }
	    }
	 }
		
	public AminoAcidQuizGUI()
	{
		super("Amino Acid Quiz");
		setLocationRelativeTo(null);
		setSize(600,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		quizStart();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new AminoAcidQuizGUI();
	}
}
