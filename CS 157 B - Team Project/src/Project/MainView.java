package Project;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Model m;
	
	MainView(Model model)
	{
		m = model;
		JLabel welcome = new JLabel("Welcome to the Grocery sales!");
		JLabel blank = new JLabel();
		//blank.setPreferredSize(new Dimension(400, 50));
		JButton start = new JButton("Start!");
		JButton exit = new JButton("Exit");
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.setPreferredSize(new Dimension(385, 120));
		topPanel.add(new JPanel().add(welcome));
		
		
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setPreferredSize(new Dimension(400, 200));
		start.setSize(100, 100);
		exit.setSize(100, 100);
		buttonPanel.add(start);
		buttonPanel.add(exit);
		
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				m.showBaseCubeView();
			}
		});
		
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		this.add(blank);
		this.add(topPanel);
		this.add(buttonPanel);
		this.setLayout(new GridLayout(3,1));
		this.setSize(400, 400);
	}
}