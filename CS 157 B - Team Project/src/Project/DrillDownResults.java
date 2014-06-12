package Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DrillDownResults extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	
	DrillDownResults(Model model)
	{
		m = model;
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showBaseCubeView();
			}
		});
		
		JButton operations = new JButton("OLAP");
		operations.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showOperationsView();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(back);
		buttonPanel.add(operations);		
		
		this.add(buttonPanel);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
}