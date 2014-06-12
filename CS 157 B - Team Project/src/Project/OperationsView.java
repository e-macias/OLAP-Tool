package Project;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OperationsView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	
	public OperationsView(Model model)
	{
		m = model;
		
		JButton roll = new JButton("Roll Up");
		JButton drill = new JButton("Drill down");
		JButton slice = new JButton("Slice");
		JButton dice = new JButton("Dice"); 
		JButton back = new JButton("Back");
		
		roll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.rollUpOperation();
			}
		});
		
		drill.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.drillDownOperation();
			}
		});
		
		slice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.sliceOperation();
			}
		});
		
		dice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.diceOperation();
			}
		});
		
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showBaseCubeResult();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.add(back);
		buttonPanel.add(roll);
		buttonPanel.add(drill);
		buttonPanel.add(slice);
		buttonPanel.add(dice);		
		
		this.add(buttonPanel); 
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
}