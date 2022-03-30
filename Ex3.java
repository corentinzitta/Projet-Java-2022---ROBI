package exercice3;

import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

public class Exercice3_0 {
	GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "" +
	"   (space setColor black) " +
	"   (robi setColor yellow)" +
	"   (space sleep 1000)" +
	"   (space setColor white)\n" + 
	"   (space sleep 1000)" +
	"	(robi setColor red) \n" + 
	"   (space sleep 1000)" +
	"	(robi translate 100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 50)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate -100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 -40)";

	public Exercice3_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() 
	{
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try 
		{
			rootNodes = parser.parse(script);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}

	private void run(SNode expr) {
		Command cmd = getCommandFromExpr(expr);
		if (cmd == null)
			throw new Error("unable to get command for: " + expr);
		cmd.run();
	}

	Command getCommandFromExpr(SNode expr) 
	{
		Command commandeRetour = null;
		
		int sizeExpr = expr.size();
		
		String contenuNom = expr.get(0).contents(); //rubi ou space
		String contenuCommande = expr.get(1).contents(); //commande
		String argCmd1 = expr.get(2).contents(); //argCommande
		String argCmd2 = "";
		
		if(sizeExpr > 3)
		{
			argCmd2 = expr.get(3).contents();
		}
		
		if(contenuNom.equals("space"))
		{
			if(contenuCommande.equals("setColor"))
			{
				commandeRetour = new SpaceChangeColor(Tools.getColorByName(argCmd1));
			}
			else if(contenuCommande.equals("sleep"))
			{
				commandeRetour = new SpaceSleep(Integer.parseInt(argCmd1));
			}
		}
		else if(contenuNom.equals("robi"))
		{
			if(contenuCommande.equals("setColor"))
			{
				commandeRetour = new RobiChangeColor(Tools.getColorByName(argCmd1));
			}
			else if(contenuCommande.equals("translate"))
			{
				Point newPoint = new Point(Integer.parseInt(argCmd1), Integer.parseInt(argCmd2));
				commandeRetour = new RobiTranslate(newPoint);
			}
			
		}
		
		return commandeRetour;
	}

	public static void main(String[] args) {
		new Exercice3_0();
	}

	public interface Command {
		abstract public void run();
	}

	public class SpaceChangeColor implements Command 
	{
		Color newColor;

		public SpaceChangeColor(Color newColor) {
			this.newColor = newColor;
		}

		@Override
		public void run() {
			space.setColor(newColor);
		}

	}
	
	public class SpaceSleep implements Command 
	{
		int millis;

		public SpaceSleep(int newMillis) {
			this.millis = newMillis;
		}

		@Override
		public void run() {
			Tools.sleep(millis);
		}

	}
	
	public class RobiChangeColor implements Command 
	{
		Color newColor;

		public RobiChangeColor(Color newColor) {
			this.newColor = newColor;
		}

		@Override
		public void run() {
			robi.setColor(newColor);
		}
	}
	
	public class RobiTranslate implements Command 
	{
		Point newPoint;

		public RobiTranslate(Point newPoint) 
		{
			this.newPoint = newPoint;
		}
		
		public RobiTranslate(int x, int y)
		{
			this.newPoint.x = x;
			this.newPoint.y = y;
		}
		
		public RobiTranslate(String x, String y)
		{
			this.newPoint.x = Integer.parseInt(x);
			this.newPoint.y = Integer.parseInt(y);
		}

		@Override
		public void run() {
			robi.translate(newPoint);
		}

	}
	
	
}
