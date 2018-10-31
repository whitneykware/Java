package lab6;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	
	private int correct = 0;
	private int incorrect = 0;
	private String aChar;
	Random rnd = new Random();
	private boolean newAnswer;
	
	private JTextArea textField = new JTextArea(); 
	private JTextArea resultsField = new JTextArea();
	private JTextArea timeField = new JTextArea();
	private JTextField inputField = new JTextField(16); 
	
	private JButton startButton = new JButton("Start Quiz");
	private JButton cancelButton = new JButton("Cancel");

	private volatile boolean cancel;

	// main screen 
	private void quizStart()
	{
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(buttonPanel());
		getContentPane().add(timeField);
		getContentPane().add(resultsField);
		getContentPane().add(textField);
		getContentPane().add(inputPanel());
		textField.setEditable(false);
		timeField.setEditable(false);
		resultsField.setEditable(false);
		textField.setText("Please enter the one letter code for each amino acid given. "
				+ "\nQuiz ends after 30 seconds.");
		timeField.setText("Time Remaining: ");
		resultsField.setText("Results: ");
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
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);
				quiz();
				// timer in different thread
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
	    			newAnswer = true;
	    			inputField.setText("  "); 
	    		}
	    	}
	    });
	    return in;
	}
	
	// quiz
	private void quiz()
	{		
		while (cancel == false)
		{
			int a = rnd.nextInt(20);
			textField.setText((FULL_NAMES[a])); 
				
			if (newAnswer == true && aChar.equals(SHORT_NAMES[a]))
			{
				textField.setText(("Correct!")); 
				correct++;
			}
		
			else
			{
				textField.setText(("Incorrect.")); 
				incorrect++;
			}
			
			newAnswer = false;
			resultsField.setText("Correct: " + correct + "\n" + "Incorrect: "
					+ incorrect);
		}
		textField.setText("Times up!");
		resultsField.setText("Final Score: Correct: " + correct + "\n" + "Incorrect: "
				+ incorrect);
	}
	
	// 30 second timer, linked to cancel button
	class QuizTimer implements Runnable 
	{
		public void run()
		{
			try
			{
				int secondsRemaining = 30;
		    
				while ( ! cancel && secondsRemaining >= 0)
				{
					secondsRemaining--;
					timeField.setText("Time remaining: " + secondsRemaining);
					Thread.sleep(1000);
				}
			}
			catch(Exception ex)
			{
				timeField.setText(ex.getMessage());
				ex.printStackTrace();
			}
			try
			{
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						startButton.setEnabled(true);
						cancelButton.setEnabled(false);
					}
				});
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
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
