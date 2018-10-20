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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuyAndSell extends JFrame
{
	private static final long serialVersionUID = 9038175598464893714L;
	
	private Map inventory;
	private long money;
	private long total = 0;
	
	private Map<String, Integer> weapons = new HashMap<String, Integer>();	

	private JTextField textField = new JTextField(); 
	
	private void mainMenu()
	{
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What can I do for you today?");
		setJMenuBar(getMainMenuBar());
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
		setJMenuBar(getBuyMenuBar());
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
				mainMenu();
			}
		});
		return sell;
	}
	// Menu - inventory - money - save inventory to log
	private JMenuBar getMainMenuBar()
	{
		JMenuBar mainMenuBar = new JMenuBar();
		
		JMenu invMenu = new JMenu("Inventory");
		mainMenuBar.add(invMenu);
		
		JMenu moneyMenu = new JMenu("Money");
		mainMenuBar.add(moneyMenu);
		JMenuItem amount = new JMenuItem(String.format("Current amount : %d", this.money));
		moneyMenu.add(amount);
		
		JMenu save = new JMenu("Save Inventory");
		mainMenuBar.add(save);
		
		return mainMenuBar;

	}
	
	private JMenuBar getBuyMenuBar()
	{
		JMenuBar buyMenuBar = new JMenuBar();
		
		JMenu weapons = new JMenu("Weapons");
		buyMenuBar.add(weapons);
		
		JCheckBox sword = new JCheckBox("Sword");
		weapons.add(sword);

		JCheckBox axe = new JCheckBox("Axe");
		weapons.add(axe);

		JMenu health = new JMenu("Health");
		buyMenuBar.add(health);
		
		JCheckBox healer = new JCheckBox("50+ Health");
		health.add(healer);

		JCheckBox superHealer = new JCheckBox("100+ Health");
		health.add(superHealer);
		
		return buyMenuBar;
	}
	
	
	//private int purchase()

	
	
	public BuyAndSell(Map inventory, long money)
	{
		super("Belethor's General Goods");
		this.inventory = inventory;
		this.money = money;
		setLocationRelativeTo(null);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{	
		Map<String, Integer> inventory = new HashMap<String, Integer>();
		long money = 1000;
		
		new BuyAndSell(inventory, money);
	}

}
