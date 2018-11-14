package lab7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PrimeNumGen extends JFrame
{
	
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final PrimeNumGen thisFrame;
	
	private static long startTime;
	private static final Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();

	
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
					
					Semaphore s = new Semaphore(4);
					if( max != null)
					{
						aTextField.setText("");
						primeButton.setEnabled(false);
						cancelButton.setEnabled(true);
						cancel = false;
						
						startTime = System.currentTimeMillis();
						
						for (int x = 0; x < max; x++)
						{
							new Thread(new UserInput(x,s)).start();
						}
					
					
						for (int x = 0; x < 4; x++)
						{
							try
							{
								s.acquire();
							} catch (InterruptedException e1)
							{
								e1.printStackTrace();
							}
						}
					
						finalUpdate();
					}
				}});
		}
	
	private void finalUpdate()
	{
		final StringBuffer buff = new StringBuffer();
		List<Integer> orderedNums = new ArrayList<Integer>(map.keySet());
		Collections.sort(orderedNums);
		
		for( Integer n : orderedNums)
		{
			buff.append(n + "\n");
		}
		
		if( cancel)
			buff.append("cancelled\n");
		
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
	
	private boolean isPrime( int i)
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		
		return true;
	}
	
	private class UserInput implements Runnable
	{
		private final int num;
		private final Semaphore semaphore;
		
		private UserInput(int num, Semaphore semaphore)
		{
			this.semaphore = semaphore;
			this.num = num;
		}
		
		public void run()
		{
			try
			{
				semaphore.acquire();	
				long lastUpdate = System.currentTimeMillis();
				//List<Integer> list = new ArrayList<Integer>();
				
					if( isPrime(num))
					{
						synchronized(this)
						{
							map.put(num,num);
						}
					}
					
					if( System.currentTimeMillis() - lastUpdate > 500)
					{
						float time = (System.currentTimeMillis() -startTime )/1000f;
						final String outString= "Found " + map.size() + " in " + num + " of " + max + " " 
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
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				semaphore.release();
			}
		}// end run
		
	}  // end UserInput
}
	
	