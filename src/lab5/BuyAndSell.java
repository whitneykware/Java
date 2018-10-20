package lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuyAndSell extends JFrame
{

	private static final long serialVersionUID = 9038175598464893714L;
	
	private List<String> inventory = new ArrayList<String>();
	private int money = 1000;
	
	private JTextField textField = new JTextField(); 
	private JButton buyMain = new JButton("Purchase Itrms");
	private JButton sellMain = new JButton("Sell Items");
	private JButton buy = new JButton("Buy");
	private JButton sell = new JButton("Sell");
	private JButton yes = new JButton("Yes");
	private JButton back = new JButton("Back");
	
	// buy menu
	// sell menu 
	
	private void mainMenu()
	{
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What can I do for you today?");

	}
	private void buyMainMenu()
	{
		textField.setText("What items would you like to purchase?");
		
	}
	
	private void sellMainMenu()
	{
		textField.setText("What would be like to sell?");
	}
	
	private JPanel mainBottomPanel()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,2));
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
	}
	
	
	public BuyAndSell()
	{
		super("Belethor's General Goods");

	}
	
	public static void main(String[] args)
	{	
		// implement gui
	}

}
