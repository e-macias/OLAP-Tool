package Project;

public class MVC 
{
	public static void main (String args[]) 
	{
		Model model = new Model();
		MasterView mv = new MasterView(model);
		model.attach(mv);
	}
}
