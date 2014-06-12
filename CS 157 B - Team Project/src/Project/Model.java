package Project;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Contains the logic for all interactions with the database.
 * @author Dhruv Mevada
 */
public class Model 
{
	private JPanel panel;
	private ChangeListener change;
	private String title;
	
	private String userName = "root";
	private String password = "Everlast$89";
	private String dbName = "grocery";
	private String url = "jdbc:mysql://localhost:3306/";
	private String driver = "com.mysql.jdbc.Driver";
	
	private static Connection conn = null;
	private static Statement statement = null;

	private String timeChoice;
	private String productChoice;
	private String storeChoice;
	
	private JTable result = null;
	
	private ResultSetTableModel tableModel;
	
	public Model()
	{
		panel = new MainView(this);
		title = "Grocery Sales";
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void attach(ChangeListener c)
	{
		change = c;
	}
	
	public void update()
	{
		change.stateChanged(new ChangeEvent(this));
	}
	
	public void showMainView()
	{
		panel = new MainView(this);
		title = "Grocery Sales";
		panel.setSize(400, 400);
		update();
	}
	
	public void showBaseCubeView()
	{
		panel = new BaseCubeView(this);
		title = "Base Cube";
		panel.setSize(400, 400);
		update();
	}
	
	public void showOperationsView()
	{
		panel = new OperationsView(this);
		title = "Operations Menu";
		panel.setSize(400, 400);
		
		JScrollPane area = new JScrollPane(result,
  				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
  				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();
	}
	
	public void showBaseCubeResult()
	{
		panel = new BaseCubeResult(this);
		title = "Base Cube Result";
		panel.setSize(1000, 1000);
		
		JScrollPane area = new JScrollPane(result,
 				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
 				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();
	}
	
	public void baseCubeQuery(String time, String product, String store)
	{
		timeChoice = time;
		productChoice = product;
		storeChoice = store;
		
		try
	   	 {
			String query = query(time, product, store);
			
	   		 tableModel = new ResultSetTableModel( driver,
	   				 url+dbName, userName, password, query );

	   		 result = new JTable(tableModel);
	   		 JScrollPane area = new JScrollPane(result,
	   				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	   				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	   		 panel = new BaseCubeResult(this);
	   		 panel.add(area);
	   		 title = "Base Cube Result";
	   		 panel.setSize(1000, 1000);
	   		 update();
	   	 } 
		
	   	 catch (ClassNotFoundException classNotFound)
	   	 {
	   		 JOptionPane.showMessageDialog( null,
	   				 "MySQL driver not found", "Driver not found",
	   				 JOptionPane.ERROR_MESSAGE );
	   		 
	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 } 
		
	   	 catch (SQLException sqlException)
	   	 {
	   		 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	   				 "Database error", JOptionPane.ERROR_MESSAGE );

	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 }
	}
	
	private String query(String time, String product, String store)
	{
		String query;
		
		query = "Select " + selectClause(time, product, store) 
				+ ", round(sum(dollar_sales), 2) as sales, sum(unit_sales) as units"
				+ " from sales_fact join time on sales_fact.fk_time_key = time.time_key"
				+ " join product on sales_fact.fk_product_key = product.product_key "
				+ " join store on sales_fact.fk_store_key = store.store_key"
				+ " group by " + groupClause(time, product, store)
				+ ";";
		return query;
	}
	
	private String querySlice(String time, String product, String store, String column, String value)
	{
		String query;
		
		query = "Select " + selectClause(time, product, store) 
				+ ", round(sum(dollar_sales), 2) as sales, sum(unit_sales) as units"
				+ " from sales_fact join time on sales_fact.fk_time_key = time.time_key"
				+ " join product on sales_fact.fk_product_key = product.product_key "
				+ " join store on sales_fact.fk_store_key = store.store_key"
				+ " where " + column + " = '" + value +"'"
				+ " group by " + groupClause(time, product, store)
				+ ";";
		return query;
	}
	
	private String selectClause(String time, String product, String store)
	{
		ArrayList<String> dimensions = new ArrayList<>();
		dimensions.add(time);
		dimensions.add(product);
		dimensions.add(store);
		
		String query = " ";
		
		for (int i = 0; i < dimensions.size(); i++)
		{
			if (i == 0)
			{
				if (dimensions.get(i) != null)
				{
					query = time;
				}
			}
			else
			{
				if (dimensions.get(i) == null)
				{
					query = query + " ";
				}
				else
				{
					for (int j = i-1; j >= 0; j--)
					{
						if (dimensions.get(j) != null)
						{
							query = query + ",";
							break;
						}
					}
					
					query = query + dimensions.get(i);
				}
			}
		}
		
		return query;
	}
	
	private String groupClause(String time, String product, String store)
	{
		ArrayList<String> dimensions = new ArrayList<>();
		dimensions.add(time);
		dimensions.add(product);
		dimensions.add(store);
		
		String query = " ";
		
		for (int i = 0; i < dimensions.size(); i++)
		{
			if (i == 0)
			{
				if (dimensions.get(i) != null)
				{
					query = time;
				}
			}
			else
			{
				if (dimensions.get(i) == null)
				{
					query = query + " ";
				}
				else
				{
					for (int j = i-1; j >= 0; j--)
					{
						if (dimensions.get(j) != null)
						{
							query = query + ",";
							break;
						}
					}
					
					query = query + dimensions.get(i);
				}
			}
		}
		
		return query;
	}
	
	private String queryDice(ArrayList<ArrayList<String>> data)
	{
		String timeDColumn = timeChoice;
		String productDColumn = productChoice;
		String storeDColumn = storeChoice;
		
		for (int i=0; i<data.size(); i++)
		{
			if (data.get(i).get(0).equals("time"))
			{
				timeDColumn = data.get(i).get(1);
			}
			else if (data.get(i).get(0).equals("product"))
			{
				productDColumn = data.get(i).get(1);
			}
			else if (data.get(i).get(0).equals("store"))
			{
				storeDColumn = data.get(i).get(1);
			}
		}
		
		
		String query = "";
		
		query = "Select " + selectClause(timeDColumn, productDColumn, storeDColumn) 
		+ ", round(sum(dollar_sales), 2) as sales, sum(unit_sales) as units"
		+ " from sales_fact join time on sales_fact.fk_time_key = time.time_key"
		+ " join product on sales_fact.fk_product_key = product.product_key "
		+ " join store on sales_fact.fk_store_key = store.store_key"
		+ " where " + whereClause(data)
		+ " group by " + groupClause(timeDColumn, productDColumn, storeDColumn)
		+ ";";
		
		System.out.println("Query: " + query);
		
		return query;
	}

	private String whereClause(ArrayList<ArrayList<String>> data)
	{
		ArrayList<String> dimensions = new ArrayList<String>();
		
		
		String query = "";
		for (int i=0; i<data.size(); i++)
		{
			if (data.get(i).get(0) != null)
			{
				
					query += "(";
					
					query += data.get(i).get(1) + " = '" + data.get(i).get(2);
					
					query += "' or ";
					
					query += data.get(i).get(1) + " = '" + data.get(i).get(3);
					
					query += "')";
					
					if (i < data.size()-1)
					{
						query = query + " AND ";
					}
				
			}
		}
		
		System.out.println("query in where: " + query);
		
		return query;
	}
	
	public void rollUpOperation() 
	{
		panel = new RollUpView(this);
		title = "Roll Up Operation";
		panel.setSize(400, 400);
		
		JScrollPane area = new JScrollPane(result,
  				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
  				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();	
	}
	
	public void rollUpResults(String dimension, String value) 
	{
		if (dimension.equals("time"))
		{
			timeChoice = value;
		}
		else if (dimension.equals("product"))
		{
			productChoice = value;
		}
		else if (dimension.equals("store"))
		{
			storeChoice = value;
		}
		
		try
	   	 {
			String query = query(timeChoice, productChoice, storeChoice);
			
			tableModel = new ResultSetTableModel( driver,
	   				 url+dbName, userName, password, query );

	   		 result = new JTable(tableModel);
	   		 JScrollPane area = new JScrollPane(result,
	   				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	   				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	   		panel = new RollUpResults(this);
			title = "Roll Up Results";
			panel.add(area);
	   		panel.setSize(1000, 1000);
	   		update();
	   	 } 
		
	   	 catch (ClassNotFoundException classNotFound)
	   	 {
	   		 JOptionPane.showMessageDialog( null,
	   				 "MySQL driver not found", "Driver not found",
	   				 JOptionPane.ERROR_MESSAGE );
	   		 
	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 } 
		
	   	 catch (SQLException sqlException)
	   	 {
	   		 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	   				 "Database error", JOptionPane.ERROR_MESSAGE );

	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 }	
	}
	
	public void drillDownOperation() 
	{
		panel = new DrillDownView(this);
		title = "Drill Down Operation";
		panel.setSize(400, 400);
		
		JScrollPane area = new JScrollPane(result,
  				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
  				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();	
	}
	
	public void drillDownResults(String dimension, String value) 
	{
		if (dimension.equals("time"))
		{
			timeChoice = value;
		}
		
		else if (dimension.equals("product"))
		{
			productChoice = value;
		}
		
		else if (dimension.equals("store"))
		{
			storeChoice = value;
		}
		
		try
	   	 {
			String query = query(timeChoice, productChoice, storeChoice);
			
			tableModel = new ResultSetTableModel( driver,
	   				 url+dbName, userName, password, query );

	   		 result = new JTable(tableModel);
	   		 JScrollPane area = new JScrollPane(result,
	   				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	   				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	   		panel = new DrillDownResults(this);
			title = "Roll Up Results";
			panel.add(area);
	   		panel.setSize(1000, 1000);
	   		update();
	   	 } 
		
	   	 catch (ClassNotFoundException classNotFound)
	   	 {
	   		 JOptionPane.showMessageDialog( null,
	   				 "MySQL driver not found", "Driver not found",
	   				 JOptionPane.ERROR_MESSAGE );
	   		 
	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 } 
		
	   	 catch (SQLException sqlException)
	   	 {
	   		 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	   				 "Database error", JOptionPane.ERROR_MESSAGE );

	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 }	
	}
	
	public void sliceOperation() 
	{
		panel = new SliceView(this);
		title = "Slice Operation";
		panel.setSize(400, 400);
		
		JScrollPane area = new JScrollPane(result,
  				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
  				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();	
	}
	
	public ArrayList<String> getValues(String dimension, String column)
	{
		ArrayList<String> entries = new ArrayList<>();
		
		if (dimension.equals("time"))
		{
			timeChoice = column;
		}
		else if (dimension.equals("product"))
		{
			productChoice = column;
		}
		else if (dimension.equals("store"))
		{
			storeChoice = column;
		}
		
		try 
		{
			Class.forName(driver); // Register JDBC Driver
			conn = DriverManager.getConnection(url+dbName, userName, password);
			statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT " + column		
					+ " from sales_fact join time on sales_fact.fk_time_key = time.time_key"
					+ " join product on sales_fact.fk_product_key = product.product_key "
					+ " join store on sales_fact.fk_store_key = store.store_key"
					+ " group by " + column
					+ ";"
					);
			
			while (rs.next()) 
			{		
				String str = rs.getString(column);
				entries.add(str);
			}
		} 
		catch (SQLException se) 
		{
			se.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (statement != null)
					statement.close();
			} 
			catch (SQLException se2) 
			{
			}// nothing we can do

			try 
			{
				if (conn != null)
					conn.close();
			} 
			catch (SQLException se) 
			{
				se.printStackTrace();
			}
		}
		return entries;
	}
	
	public void sliceResults(String dimension, String column, String value)
	{
		if (dimension.equals("time"))
		{
			timeChoice = column;
		}
		else if (dimension.equals("product"))
		{
			productChoice = column;
		}
		else if (dimension.equals("store"))
		{
			storeChoice = column;
		}
		
		try
	   	 {
			String query = querySlice(timeChoice, productChoice, storeChoice, column, value);
			
			tableModel = new ResultSetTableModel( driver,
	   				 url+dbName, userName, password, query );

	   		 result = new JTable(tableModel);
	   		 JScrollPane area = new JScrollPane(result,
	   				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	   				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	   		panel = new SliceResults(this);
			title = "Slice Results";
			panel.add(area);
	   		panel.setSize(1000, 1000);
	   		update();
	   	 } 
		
	   	 catch (ClassNotFoundException classNotFound)
	   	 {
	   		 JOptionPane.showMessageDialog( null,
	   				 "MySQL driver not found", "Driver not found",
	   				 JOptionPane.ERROR_MESSAGE );
	   		 
	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 } 
		
	   	 catch (SQLException sqlException)
	   	 {
	   		 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	   				 "Database error", JOptionPane.ERROR_MESSAGE );

	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 }	
	}
	
	public void diceOperation() 
	{
		panel = new DiceView(this);
		title = "Dice Operation";
		panel.setSize(400, 400);
		
		JScrollPane area = new JScrollPane(result,
  				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
  				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(area);
		update();	
	}
	
	public void diceResults(ArrayList<ArrayList<String>> data)
	{
		try
	   	 {
			String query = queryDice(data);
			
			tableModel = new ResultSetTableModel( driver,
	   				 url+dbName, userName, password, query );

	   		 result = new JTable(tableModel);
	   		 JScrollPane area = new JScrollPane(result,
	   				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	   				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	   		panel = new DiceResults(this);
			title = "Dice Results";
			panel.add(area);
	   		panel.setSize(1000, 1000);
	   		update();
	   	 } 
		
	   	 catch (ClassNotFoundException classNotFound)
	   	 {
	   		 JOptionPane.showMessageDialog( null,
	   				 "MySQL driver not found", "Driver not found",
	   				 JOptionPane.ERROR_MESSAGE );
	   		 
	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 } 
		
	   	 catch (SQLException sqlException)
	   	 {
	   		 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	   				 "Database error", JOptionPane.ERROR_MESSAGE );

	   		 tableModel.disconnectFromDatabase();
	   		 System.exit(1); 
	   	 }	
		
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
}