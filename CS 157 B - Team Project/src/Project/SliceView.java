package Project;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SliceView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	private JPanel cardPanel;
	private String dimensions[] = {"time", "product", "store"};
	
	private BaseCubeView bcv;
	private String choice = null;
	private String dimension = null;
	private JComboBox<String> choiceColumn;
	private JComboBox<String> timeS;
	private JComboBox<String> productS;
	private JComboBox<String> storeS;
	
	public SliceView(Model model)
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
		
		JButton slice = new JButton("Slice");
		slice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("dimension: " + dimension);
				System.out.println("choice: " + choice);
				String column = choiceColumn.getSelectedItem().toString();
				System.out.println("choice column: " + column);
				m.sliceResults(dimension, choice, column);
			}
		});
		
		choice = m.getTimeChoice();
        if (choice != null)
        {
        	timePanel = new JPanel();
            timePanel.setPreferredSize(new Dimension(200, 120));
        	timeS = getAttributes(bcv.getTimeColumn(), choice, dimensions[0]);
        	timePanel.add(timeS);
        	cardPanel.add(timePanel, dimensions[0]);
        	
        	timeButton = new JRadioButton(dimensions[0]);
    		timeButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[0]);
    		        
    		        choice = m.getTimeChoice();
    		        choiceColumn = timeS;
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
        	productS = getAttributes(bcv.getProductColumn(), choice, dimensions[1]);
            productPanel.add(productS);
            cardPanel.add(productPanel, dimensions[1]);
            
            producButton = new JRadioButton(dimensions[1]);
    		producButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[1]);
    		        
    		        choice = m.getProductChoice();
    		        choiceColumn = productS;
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
        	storeS = getAttributes(bcv.getStoreColumn(), choice, dimensions[2]);
            storePanel.add(storeS);
            cardPanel.add(storePanel, dimensions[2]); 
            
            storeButton = new JRadioButton(dimensions[2]);
    		storeButton.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent e)
    			{
    				CardLayout cl = (CardLayout) (cardPanel.getLayout());

    		        cl.show(cardPanel, dimensions[2]);
    		        
    		        choice = m.getStoreChoice();
    		        choiceColumn = storeS;
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
        buttonPanel.add(slice);
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
}