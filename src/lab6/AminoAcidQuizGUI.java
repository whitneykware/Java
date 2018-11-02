package lab6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import lab6.timerThread.CountdownListener;

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
	
	private static int correct = 0;
	private static int incorrect = 0;
	private static String aChar;
	Random rnd = new Random();
	
	private static JTextArea textField = new JTextArea(); 
	private static JTextArea resultsField = new JTextArea();
	private static JTextArea timeField = new JTextArea();
	private static JTextField inputField = new JTextField(16); 
	
	private static JButton startButton = new JButton("Start Quiz");
	private static JButton cancelButton = new JButton("Cancel");

	private volatile boolean cancel;
	static Timer myTimer;

	// main screen 
	private void quizStart()
	{
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(buttonPanel());
		getContentPane().add(timeField);
		getContentPane().add(resultsField);
		getContentPane().add(textField);
		getContentPane().add(inputPanel());
		//textField.setEditable(false);
		timeField.setEditable(false);
		//resultsField.setEditable(false);
		textField.setText("Please enter the one letter code for each amino acid given. "
				+ "\nQuiz ends after 30 seconds.");
		timeField.setText("Time Remaining: ");
		resultsField.setText("Correct: \nIncorrect: " );
	}
	
	// button panel with start and cancel buttons
	private JPanel buttonPanel()
	{
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,2));
		buttons.add(startButton);
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel = false;
				//startButton.setEnabled(true);
				//cancelButton.setEnabled(false);
				
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
						
					if (aChar.equals(SHORT_NAMES[a]))
					{
						textField.setText(("Correct!")); 
						correct++;
					}
				
					else
					{
						textField.setText(("Incorrect.")); 
						incorrect++;
					}
					
					resultsField.setText("Correct: " + correct + "\n" + "Incorrect: "
							+ incorrect);
				}
				
				textField.setText("Times up!");
				resultsField.setText("Final Score: Correct: " + correct + "\n" + "Incorrect: "
						+ incorrect);
				
			}
		});
		
		buttons.add(cancelButton);
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel = true;
			}
		});
		return buttons;
	}
	
	// user input, enter button
	private JPanel inputPanel()
	{
		JPanel in = new JPanel();
		in.add(inputField);
	    JButton enter = new JButton("Enter"); 
	    in.add(enter);
	    enter.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{ 
	    		String s = e.getActionCommand(); 
	    		if (s.equals("Enter")) 
	    		{ 
	    			String answer = inputField.getText().toUpperCase();
	    			aChar = "" + answer.charAt(0);
	    			inputField.setText("  "); 
	    		}
	    	}
	    });
	    return in;
	}
	
	private void setupTimer(int numSecondsToCountDown) 
	{
	      timeField.setText("Time: " + Integer.toString(numSecondsToCountDown));
	      myTimer = new Timer(1000, new CountdownListener(numSecondsToCountDown));
	      myTimer.start();
	}

	class CountdownListener implements ActionListener 
	{
		private int secondsCount;

		public CountdownListener(int startingSeconds) 
		{ 
			secondsCount = startingSeconds; 
		}	

		public void actionPerformed(ActionEvent evt) 
		{
	    	 timeField.setText(Integer.toString(secondsCount));
	         secondsCount--;

	         if (secondsCount <= 0) 
	         { 
	            myTimer.stop();
	            cancel = true;
	         }
	    }
	 }
	
/////////////////////////////////////////////////////////////////////////////////   
	
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
