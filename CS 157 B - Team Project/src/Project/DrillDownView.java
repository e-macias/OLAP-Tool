package Project;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DrillDownView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	private JPanel cardPanel;
	private String dimensions[] = {"time", "product", "store"};
	
	private BaseCubeView bcv;
	private String choice = null;
	private String dimension = null;
	private JComboBox<String> choiceColumn;
	private JComboBox<String> timeDown;
	private JComboBox<String> productDown;
	private JComboBox<String> storeDown;
	
	public DrillDownView(Model model)
	{
		cardPanel = new JPanel(new CardLayout(50, 50));
		
		m = model;
		bcv = new BaseCubeView(m);
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));
        radioPanel.add(Box.createRigidArea(new Dimension(10,10)));
        
		final ButtonGroup btnGroup = new ButtonGroup();
		
		JPanel timePanel = null;
		JPanel productPanel = null;
		JPanel storePanel = null;
		JRadioButton timeButton = null;
		JRadioButton producButton = null;
		JRadioButton storeButton = null;		
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showOperationsView();
			}
		});
		
		JButton drillDown = new JButton("Drill Down");
		drillDown.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("choice: " + dimension);
				String value = choiceColumn.getSelectedItem().toString();
				System.out.println("choice column: " + value);
				m.drillDownResults(dimension, value);
			}
		});
		
		choice = m.getTimeChoice();
        if (choice != null)
        {
        	timePanel = new JPanel();
            timePanel.setPreferredSize(new Dimension(200, 120));
        	timeDown = getAttributes(bcv.getTimeColumn(), choice);
        	timePanel.add(timeDown);
        	cardPanel.add(timePanel, dimensions[0]);
        	
        	timeButton = new JRadioButton(dimensions[0]);
    		timeButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[0]);
    		        
    		        choiceColumn = timeDown;
    		        dimension = dimensions[0];
    			}
    		});
    		timeButton.setSelected(true);
    		btnGroup.add(timeButton);
    		radioPanel.add(timeButton);
        }
        
        radioPanel.add(Box.createHorizontalGlue());
        
        choice = m.getProductChoice();
        if (choice != null)
        {
        	productPanel = new JPanel();
            productPanel.setPreferredSize(new Dimension(200, 120));
        	productDown = getAttributes(bcv.getProductColumn(), choice);
            productPanel.add(productDown);
            cardPanel.add(productPanel, dimensions[1]);
            
            producButton = new JRadioButton(dimensions[1]);
    		producButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[1]);
    		        
    		        choiceColumn = productDown;
    		        dimension = dimensions[1];
    			}
    		});
    		producButton.setSelected(true);
    		btnGroup.add(producButton);
    		radioPanel.add(producButton);
        }
		
        radioPanel.add(Box.createHorizontalGlue());
        
        choice = m.getStoreChoice();
        if (choice != null)
        {
        	storePanel = new JPanel();
            storePanel.setPreferredSize(new Dimension(200, 120));
        	storeDown = getAttributes(bcv.getStoreColumn(), choice);
            storePanel.add(storeDown);
            cardPanel.add(storePanel, dimensions[2]); 
            
            storeButton = new JRadioButton(dimensions[2]);
    		storeButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[2]);
    		        
    		        choiceColumn = storeDown;
    		        dimension = dimensions[2];
    			}
    		});
    		storeButton.setSelected(true);
    		btnGroup.add(storeButton);
    		radioPanel.add(storeButton);
        }
		
        radioPanel.add(Box.createRigidArea(new Dimension(10,10)));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.add(back);
        buttonPanel.add(drillDown);
        buttonPanel.add(radioPanel);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        
        bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(20,0)));
        bottomPanel.add(cardPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		this.add(bottomPanel); 
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	
	private JComboBox<String> getAttributes(JComboBox<String> cBox, String selected)
	{
		JComboBox<String> temp = null;
        
		if (selected != null)
		{
			temp = new JComboBox<String>();
	        boolean get = false;
	        
	        for (int i = cBox.getItemCount() - 1; i >= 0; i--)
	        {
	        	if (cBox.getItemAt(i).equals(selected))
	        	{
	        		get = true;
	        	}
	        	
	        	if (get)
	        	{
	        		temp.addItem(cBox.getItemAt(i));
	        	}
	        }
		}
        
        return temp;
	}
}