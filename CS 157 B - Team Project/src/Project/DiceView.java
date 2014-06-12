package Project;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DiceView extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Model m;
	private JPanel selectionPanel;
	private String[] dimensions = {"time", "product", "store"};
	
	private BaseCubeView bcv;
	
	private String timeChoice = null;
	private String productChoice = null;
	private String storeChoice = null;
	
	private String dimension = null;
	
	private JComboBox<String> choiceColumn;
	
	private JComboBox<String> timeDiceOp1;
	private JComboBox<String> productDiceOp1;
	private JComboBox<String> storeDiceOp1;
	
	private JComboBox<String> timeDiceOp2;
	private JComboBox<String> productDiceOp2;
	private JComboBox<String> storeDiceOp2;
	
	private String timeop1 = null;
	private String timeop2 = null;
	private String productop1 = null;
	private String productop2 = null;
	private String storeop1 = null;
	private String storeop2 = null;
	
	private JPanel timePanel = null;
	private JPanel productPanel = null;
	private JPanel storePanel = null;
	
	private JCheckBox timeBox = null;
	private JCheckBox productBox = null;
	private JCheckBox storeBox = null;
	
	private JButton refreshButton = null;
	
	private ArrayList<String> dimensionsSelected = null;
	private ArrayList<ArrayList<String>> dimeAndValues = null;
	
	public DiceView(Model model)
	{
		selectionPanel = new JPanel();
		m = model;
		bcv = new BaseCubeView(m);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));
        radioPanel.add(Box.createRigidArea(new Dimension(10,10)));
        
        JPanel checkBoxGroup = new JPanel();
        
        dimensionsSelected = new ArrayList<String>();
        dimeAndValues = new ArrayList<ArrayList<String>>();
        
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showOperationsView();
			}
		});
		
		JButton dice = new JButton("Dice");
		dice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				ArrayList<String> times = new ArrayList<String>();
				ArrayList<String> products = new ArrayList<String>();
				ArrayList<String> stores = new ArrayList<String>();
				
				if (dimensionsSelected.contains(dimensions[0]))
				{
					timeop1 = timeDiceOp1.getSelectedItem().toString();
					timeop2 = timeDiceOp2.getSelectedItem().toString();
					
					times.add(dimensions[0]);
					times.add(timeChoice);
					times.add(timeop1);
					times.add(timeop2);
					
					dimeAndValues.add(times);
					
				}
				if (dimensionsSelected.contains(dimensions[1]))
				{
					productop1 = productDiceOp1.getSelectedItem().toString();
					productop2 = productDiceOp2.getSelectedItem().toString();
					
					products.add(dimensions[1]);
					products.add(productChoice);
					products.add(productop1);
					products.add(productop2);
					
					dimeAndValues.add(products);
				}
				if (dimensionsSelected.contains(dimensions[2]))
				{
					storeop1 = storeDiceOp1.getSelectedItem().toString();
					storeop2 = storeDiceOp2.getSelectedItem().toString();
					
					stores.add(dimensions[2]);
					stores.add(storeChoice);
					stores.add(storeop1);
					stores.add(storeop2);
					
					dimeAndValues.add(stores);
				}
				
				for (ArrayList<String> al: dimeAndValues)
				{
					for (String str: al)
					{
						System.out.print(str + " - ");
					}
					System.out.println();
				}
				
				
				m.diceResults(dimeAndValues);				
			}
		});
		
		timeChoice = m.getTimeChoice();
		if (timeChoice != null)
        {
			timeBox = new JCheckBox("time");
			checkBoxGroup.add(timeBox);
			
        	timePanel = new JPanel();
            timePanel.setPreferredSize(new Dimension(200, 120));
            timeDiceOp1 = getAttributes(bcv.getTimeColumn(), timeChoice, dimensions[0]);
            timeDiceOp2 = getAttributes(bcv.getTimeColumn(), timeChoice, dimensions[0]);
        	timePanel.add(timeDiceOp1);
        	timePanel.add(timeDiceOp2);
        	timePanel.setVisible(false);
        	
        	selectionPanel.add(timePanel);        	
        }
		
		productChoice = m.getProductChoice();
		if (productChoice != null)
        {
			productBox = new JCheckBox("product");
			checkBoxGroup.add(productBox);
			
        	productPanel = new JPanel();
        	productPanel.setPreferredSize(new Dimension(200, 120));
            productDiceOp1 = getAttributes(bcv.getProductColumn(), productChoice, dimensions[1]);
            productDiceOp2 = getAttributes(bcv.getProductColumn(), productChoice, dimensions[1]);
            productPanel.add(productDiceOp1);
            productPanel.add(productDiceOp2);
            productPanel.setVisible(false);
        	
        	selectionPanel.add(productPanel);        	
        }
		
		storeChoice = m.getStoreChoice();
		if (storeChoice != null)
        {
			storeBox = new JCheckBox("store");
			checkBoxGroup.add(storeBox);
			
        	storePanel = new JPanel();
        	storePanel.setPreferredSize(new Dimension(200, 120));
            storeDiceOp1 = getAttributes(bcv.getStoreColumn(), storeChoice, dimensions[2]);
            storeDiceOp2 = getAttributes(bcv.getStoreColumn(), storeChoice, dimensions[2]);
            storePanel.add(storeDiceOp1);
            storePanel.add(storeDiceOp2);
            storePanel.setVisible(false);
        	
        	selectionPanel.add(storePanel);        	
        }
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.add(back);
        buttonPanel.add(dice);
        buttonPanel.add(refreshButton);
        
        this.add(buttonPanel);
        this.add(checkBoxGroup);	
        this.add(selectionPanel);
	}
	
	
	private JComboBox<String> getAttributes(JComboBox<String> cBox, String choice, String table_dimension)
	{
		JComboBox<String> temp = null;
		
		ArrayList<String> values = m.getValues(table_dimension, choice);
        
		if (choice != null)
		{
			temp = new JComboBox<String>();
	        for (int i = 0; i < values.size(); i++)
	        {
	        	temp.addItem(values.get(i));
	        }
		}
        
        return temp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		boolean visible = false;
		
		if(e.getSource() == refreshButton)
		{
			if (timeChoice != null && timeBox.isSelected())
			{
				visible = true;
				timePanel.setVisible(visible);
				
				if(!(dimensionsSelected.contains(dimensions[0])))
				{
					dimensionsSelected.add(dimensions[0]);
				}
			}
			else
			{
				if (timeChoice != null)
				{
					visible = false;
					timePanel.setVisible(visible);
				
					dimensionsSelected.remove(dimensions[0]);
				}
			}
			
			if (productChoice != null && productBox.isSelected())
			{
				visible = true;
				productPanel.setVisible(visible);
				
				if(!(dimensionsSelected.contains(dimensions[1])))
				{
					dimensionsSelected.add(dimensions[1]);
				}
			}
			else
			{
				if (productChoice != null)
				{
					visible = false;
					productPanel.setVisible(visible);
				
					dimensionsSelected.remove(dimensions[1]);
				}
			}
			
			if (storeChoice != null && storeBox.isSelected())
			{
				visible = true;
				storePanel.setVisible(visible);
				
				if(!(dimensionsSelected.contains(dimensions[2])))
				{
					dimensionsSelected.add(dimensions[2]);
				}
			}
			else
			{
				if (storeChoice != null)
				{
					visible = false;
					storePanel.setVisible(visible);
				
					dimensionsSelected.remove(dimensions[2]);
				}
			}
		}
		
		for (String str: dimensionsSelected)
		{
			System.out.println("Dimensions --- " + str);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}