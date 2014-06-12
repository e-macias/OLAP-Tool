package Project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MasterView extends JFrame implements ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private Model model;
	private int width = 400;
	private int height = 400;
	   
	 /**
	  * The main constructor for MasterView.
	  * It loads JPanels from the model.
	  * @param model The main model that gets passed into every other class.
	  */
	MasterView(Model model)
	{
		this.model = model;
		panel = new MainView(model);
		
		this.setTitle("Grocery Sales");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setSize(width, height);
		this.setVisible(true);
	}
	   
	/**
	 * The listener to change events from the updated model.
	 * This updates the current panel.
	 */
	public void stateChanged(ChangeEvent arg0)
	{
		this.remove(panel);
		panel = model.getPanel();
		this.setTitle(model.getTitle());
		this.add(panel);
		this.pack();
	}
}