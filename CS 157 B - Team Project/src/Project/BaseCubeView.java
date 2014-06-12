package Project;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BaseCubeView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	
	private String timeChoice = "";
	private String productChoice = "";
	private String storeChoice = "";
	
	private final JComboBox<String> timeColumn;
	private final JComboBox<String> productColumn;
	private final JComboBox<String> storeColumn;
	
	BaseCubeView(Model model)
	{
		m = model;
		JLabel time = new JLabel("Time column: ");
		JLabel product = new JLabel("Product column: ");
		JLabel store = new JLabel("Store column: ");
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showMainView();
			}
		});
		
		timeColumn = new JComboBox<String>();
		timeColumn.addItem("Select");
		timeColumn.addItem("Date");
		timeColumn.addItem("Holiday_flag");
		timeColumn.addItem("Day_of_week");
		timeColumn.addItem("Day_number_in_month");
		timeColumn.addItem("Day_number_overall");
		timeColumn.addItem("Week_number_in_year");
		timeColumn.addItem("Week_number_overall");
		timeColumn.addItem("Fiscal_period");
		timeColumn.addItem("Month");
		timeColumn.addItem("Quarter");
		timeColumn.addItem("Year");
		
		
		productColumn = new JComboBox<String>();
		productColumn.addItem("Select");
		productColumn.addItem("SKU_number");
		productColumn.addItem("Brand");
		productColumn.addItem("Subcategory");
		productColumn.addItem("Category");
		productColumn.addItem("Department");
		productColumn.addItem("Full_description");
		productColumn.addItem("Description");
		productColumn.addItem("Shelf_depth_cm");
		productColumn.addItem("Shelf_height_cm");
		productColumn.addItem("Shelf_width_cm");
		productColumn.addItem("Diet_type");
		productColumn.addItem("Package_type");
		productColumn.addItem("Cases_per_pallet");
		productColumn.addItem("Units_per_retail_case");
		productColumn.addItem("Units_per_shipping_case");
		productColumn.addItem("Weight");
		productColumn.addItem("Weight_unit_of_measure");
						
		
		storeColumn = new JComboBox<String>();
		storeColumn.addItem("Select");
		storeColumn.addItem("Store_street_address");
		storeColumn.addItem("Store_zip");
		storeColumn.addItem("City");
		storeColumn.addItem("Sales_district");
		storeColumn.addItem("Sales_region");
		storeColumn.addItem("Store_county");
		storeColumn.addItem("Store_state");
		storeColumn.addItem("Name");
		storeColumn.addItem("Store_manager");
		storeColumn.addItem("Store_number");
		storeColumn.addItem("Store_phone");
		storeColumn.addItem("Store_fax");
		storeColumn.addItem("Photo_processing_type");
		storeColumn.addItem("Finance_services_type");
		storeColumn.addItem("Floor_plan_type");
		storeColumn.addItem("First_opened_date");
		storeColumn.addItem("Last_remodel_date");
		storeColumn.addItem("Frozen_sqft");
		storeColumn.addItem("Grocery_sqft");
		storeColumn.addItem("Meat_sqft");
		storeColumn.addItem("Store_sqft");
		
		
		JButton createCube = new JButton("Create Cube!");
		createCube.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (timeColumn.getSelectedIndex() == 0 &&
						productColumn.getSelectedIndex() == 0 &&
								storeColumn.getSelectedIndex() == 0)
				{
					JOptionPane.showMessageDialog( null,
			   				 "Must select at least one dimension", "Query Error",
			   				 JOptionPane.ERROR_MESSAGE );
				}
				else
				{
					if (timeColumn.getSelectedIndex() == 0)
						timeChoice = null;
					else
						timeChoice = timeColumn.getSelectedItem().toString();
					
					if (productColumn.getSelectedIndex() == 0)
						productChoice = null;
					else
						productChoice = productColumn.getSelectedItem().toString();
					
					if (storeColumn.getSelectedIndex() == 0)
						storeChoice = null;
					else
						storeChoice = storeColumn.getSelectedItem().toString();
					
					m.baseCubeQuery(timeChoice, productChoice, storeChoice);
				}
			}
		});
		
		JPanel timePanel = new JPanel();
		timePanel.add(time);
		timePanel.add(timeColumn);
		
		JPanel productPanel = new JPanel();
		productPanel.add(product);
		productPanel.add(productColumn);
		
		JPanel storePanel = new JPanel();
		storePanel.add(store);
		storePanel.add(storeColumn);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(createCube);
		
		this.add(timePanel);
		this.add(productPanel);
		this.add(storePanel);
		this.add(buttonPanel);
		this.setPreferredSize(new Dimension(400, 400));
		this.setLayout(new GridLayout(4,1));
	}

	public String getTimeChoice()
	{
		return timeChoice;
	}

	public String getProductChoice()	
	{
		return productChoice;
	}

	public String getStoreChoice()
	{
		return storeChoice;
	}

	public JComboBox<String> getTimeColumn() 
	{
		return timeColumn;
	}

	public JComboBox<String> getProductColumn() 
	{
		return productColumn;
	}

	public JComboBox<String> getStoreColumn() 
	{
		return storeColumn;
	}
}