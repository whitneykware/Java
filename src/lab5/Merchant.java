package lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Merchant extends JFrame
{
	private static final long serialVersionUID = 9038175598464893714L;
	
	private int money;
	private List<String> inventory = new ArrayList<String>();
	private JTextField textField = new JTextField(); 
	
	// items for sale
	private final static Map<String, Integer> items;
	static
	{	
		Map<String, Integer> allItems = new LinkedHashMap<String, Integer>();
		allItems.put("Sword", 20);
		allItems.put("Axe", 15);	
		allItems.put("10+ Health", 10);
		allItems.put("50+ Health", 50);
		items = Collections.unmodifiableMap(allItems);	
	}
	
	// main screen
	private void buyMainMenu()
	{
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buyBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(textField, BorderLayout.CENTER);
		textField.setText("What items would you like to purchase?");
		textField.setEditable(false);
		setJMenuBar(getBuyMenuBar());
		revalidate();
		repaint();
	}
	
	// panel of items
	private JPanel buyBottomPanel()
	{
		JPanel buy = new JPanel();
		buy.setLayout(new GridLayout(items.size()/2, items.size()));
		
		for ( String key : items.keySet())
		{
			JButton it = new JButton(key + ", " + items.get(key));
			buy.add(it);
			it.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (money - items.get(key) < 0)
					{
						getContentPane().removeAll();
						getContentPane().setLayout(new BorderLayout());
						getContentPane().add(backBottomPanel(), BorderLayout.SOUTH);
						getContentPane().add(textField, BorderLayout.CENTER);
						textField.setText("Sorry, you do not have enough money for this item.");
						textField.setEditable(false);
						setJMenuBar(getBuyMenuBar());
						revalidate();
						repaint();
					}
					else
					{
					money = money - items.get(key);
					inventory.add(key);
					updateMoney();
					}
				}
			});
		}
		return buy;
	}
	
	// returns to main screen
	private JPanel backBottomPanel()
	{
		JPanel back = new JPanel();
		back.setLayout(new GridLayout(0,1));
		JButton main = new JButton("Back to Main");
		back.add(main);
		main.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buyMainMenu();
			}
		});
		return back;
	}

	private void updateMoney()
	{
		textField.setText(String.format("Money remaining: %d, ", this.money) + '\n' + 
				"Would you like to buy anything else?");
		validate();
	}
	
	// menus
	private JMenuBar getBuyMenuBar()
	{
		JMenuBar buyMenuBar = new JMenuBar();
			
		JMenu fileMenu = new JMenu("File");
		buyMenuBar.add(fileMenu);
		
		JMenuItem save = new JMenuItem("Save Inventory");
		fileMenu.add(save);
		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveToFile();
			}
		});
		
		JMenu viewMenu = new JMenu("View");
		buyMenuBar.add(viewMenu);
		
		JMenuItem view = new JMenuItem("View Inventory");
		viewMenu.add(view);
		view.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				getContentPane().removeAll();
				getContentPane().setLayout(new BorderLayout());
				getContentPane().add(backBottomPanel(), BorderLayout.SOUTH);
				getContentPane().add(textField, BorderLayout.CENTER);
				textField.setText(inventory.toString());
				textField.setEditable(false);
				setJMenuBar(getBuyMenuBar());
				revalidate();
				repaint();
			}
		});

		return buyMenuBar;
	}
	
	// save to file
	private void saveToFile()
	{
		JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		if (fc.getSelectedFile() == null)
			return;
		File choosenFile = fc.getSelectedFile();
		
		if (fc.getSelectedFile().exists())
		{
			String message = "File " + fc.getSelectedFile().getName() + " exists. Overwrite?";
			
			if (JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
				return;
		}
		try
		{
			BufferedWriter write = new BufferedWriter(new FileWriter(choosenFile));
			for (String item : inventory)
			{
				write.write(item + '\n');
			}
			write.flush(); write.close();
		}
		catch ( Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Could not write to file.", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Merchant(int money)
	{
		super("Belethor's General Goods");
		this.money = money;
		setLocationRelativeTo(null);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buyMainMenu();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{	
		int money = 100;
		new Merchant(money);
	}

}
