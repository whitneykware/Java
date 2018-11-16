package lab7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PrimeNumGen extends JFrame
{
	private static final long serialVersionUID = 6179840045609037911L;
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final PrimeNumGen thisFrame;
	
	private static long startTime;
	private static final int numThreads = Runtime.getRuntime().availableProcessors();
	private static List<Integer> primes = Collections.synchronizedList(new ArrayList<Integer>());

	
	public static void main(String[] args)
	{
		PrimeNumGen png = new PrimeNumGen("Primer Number Generator");
		
		// don't add the action listener from the constructor
		png.addActionListeners();
		png.setVisible(true);
	}
	
	private PrimeNumGen(String title)
	{
		super(title);
		this.thisFrame = this;
		cancelButton.setEnabled(false);
		aTextField.setEditable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		//kill java VM on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(primeButton,  BorderLayout.SOUTH);
		getContentPane().add(cancelButton,  BorderLayout.EAST);
		getContentPane().add( new JScrollPane(aTextField),  BorderLayout.CENTER);
	}
	
	private class CancelOption implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			cancel = true;
		}
	}
	
	private void addActionListeners()
	{
		cancelButton.addActionListener(new CancelOption());
	
		primeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					
					String num = JOptionPane.showInputDialog("Enter a large integer");
					Integer max =null;
					
					try
					{
						max = Integer.parseInt(num);
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(
								thisFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					
					if( max != null)
					{
						aTextField.setText("");
						primeButton.setEnabled(false);
						cancelButton.setEnabled(true);
						cancel = false;
						new Thread(new StartThreads(max)).start();
					}
				}});
		}
	
	// starts threads (determined by # of available processors), acquires all permits then updates final results
	private class StartThreads implements Runnable
	{
		private final int max;
		private Semaphore semaphore = new Semaphore(numThreads);
		
		private StartThreads(int max)
		{
			this.max = max;
		}
		
		public void run()
		{
			startTime = System.currentTimeMillis();
			for ( int x = 0; x < numThreads; x++)
			{
				try
				{
					semaphore.acquire();
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				FindPrimeNumbers primeThread = new FindPrimeNumbers(x+1, max, semaphore);
				new Thread(primeThread).start();
			}
			
			for ( int x = 0; x < numThreads; x++ )
			{
				try
				{
					semaphore.acquire();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			finalUpdate();
		}
	}
	
	// finds prime numbers, adds to list, releases semaphore 
	private class FindPrimeNumbers implements Runnable
	{
		private final int start;
		private final int max;
		private final Semaphore semaphore;
		
		private FindPrimeNumbers(int start, int max, Semaphore s)
		{
			this.start = start;
			this.max = max;
			this.semaphore = s;
		}
		
		public void run()
		{
			long lastUpdate = System.currentTimeMillis();
			for (int n = start; n < max && ! cancel; n = n + numThreads) 
			{
				if( isPrime(n))
				{
					primes.add(n);
					
					if( System.currentTimeMillis() - lastUpdate > 500)
					{
						float time = (System.currentTimeMillis() -startTime )/1000f;
						final String outString= "Found " + primes.size() + " in " + n + " of " + max + " " 
								+ time + " seconds ";
						
						SwingUtilities.invokeLater( new Runnable()
						{
							@Override
							public void run()
							{
								aTextField.setText(outString);
							}
						});	
						lastUpdate = System.currentTimeMillis();	
					}
				}
			}
			semaphore.release();
		}
	}
	
	private boolean isPrime( int i)
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		
		return true;
	}
	
	// sorts finished list and displays results and time
	private void finalUpdate()
	{
		final StringBuffer buff = new StringBuffer();
		synchronized(primes)
		{
			Collections.sort(primes);
		
			for( Integer n : primes)
			{
				buff.append(n + "\n");
			}
		}
		
		if (cancel)
		{
			buff.append("cancelled\n");
		}
		
		float time = (System.currentTimeMillis() - startTime )/1000f;
		buff.append("Time = " + time + " seconds " );
		
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{	
				cancel = false;
				primeButton.setEnabled(true);
				cancelButton.setEnabled(false);
				aTextField.setText( (cancel ? "cancelled " : "") +  buff.toString());
			}
		});	
	}
}  
	
	