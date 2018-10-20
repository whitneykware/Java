package lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuyAndSell extends JFrame
{
	private static final long serialVersionUID = 9038175598464893714L;
	
	private Map<String, Integer> inventory = new HashMap<String, Integer>();
	private long money = 1000;
	
	private JTextField textField = new JTextField(); 

	
	private void mainMenu()
	{
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What can I do for you today?");
		revalidate();
		repaint();
	}
	
	private void buyMainMenu()
	{
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buyBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What items would you like to purchase?");
		revalidate();
		repaint();
	}
	
	private void sellMainMenu()
	{
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(sellBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What items would you like to sell?");
		revalidate();
		repaint();
	}
	
	private JButton returnToMain()
	{
		JButton main = new JButton("Back to Main");
		main.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mainMenu();
			}
		});
		return main;
	}
	
	private JPanel mainBottomPanel()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,2));
		JButton buyMain = new JButton("Purchase Items");
		JButton sellMain = new JButton("Sell Items");
		mainPanel.add(buyMain);
		mainPanel.add(sellMain);
		
		buyMain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buyMainMenu();
			}
		});
		
		sellMain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sellMainMenu();
			}
		});
		
		return mainPanel;
	}
	
	private JPanel buyBottomPanel()
	{
		JPanel buy = new JPanel();
		buy.setLayout(new GridLayout(0,2));
		JButton buyButton = new JButton("Buy");
		buy.add(buyButton);
		buy.add(returnToMain());
		
		buyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mainMenu();
			}
		});
		return buy;
	}

	private JPanel sellBottomPanel()
	{
		JPanel sell = new JPanel();
		JButton sellButton = new JButton("Sell");
		sell.setLayout(new GridLayout(0,2));
		sell.add(sellButton);
		sell.add(returnToMain());

		sellButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buyMainMenu();
			}
		});
		return sell;
	}
	
	//private int purchase()

	
	
	public BuyAndSell()
	{
		super("Belethor's General Goods");
		setLocationRelativeTo(null);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{	
		new BuyAndSell();
	}

}
