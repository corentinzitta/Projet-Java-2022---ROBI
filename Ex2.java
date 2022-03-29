package exercice2;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;


public class Exercice2_1_0 
{
	GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100)); //Conteneur global
	GRect robi = new GRect(); //Seul élément présent dans le conteneur global
	String script = "(space color yellow) (robi translate 0 50) (robi color red) "; //Un script est représenté comme une expression parenthésée appelée S-expression.

	public Exercice2_1_0() 
	{
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() 
	{
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try {
			rootNodes = parser.parse(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}
	
	/*
	 *  analyse le script et construit une instance de List<SNode> (rootNodes) qui 
	 *  représente la liste des S-expression racines du script.
	 */
	
	private void run(SNode expr) 
	{
		// INSERT CODE BELOW
		// SNode expr: peut être soit une feuille, soit un nœud intermédiaire.
		
		int sizeExpr = expr.size();
		
		String contenuNom = expr.get(0).contents(); //rubi ou space
		String contenuCommande = expr.get(1).contents(); //commande
		String argCmd1 = expr.get(2).contents(); //argCommande
		String argCmd2 = "";
		
		if(sizeExpr > 3)
		{
			argCmd2 = expr.get(3).contents();
		}
		
		if(contenuNom.equals("robi"))
		{
			if(contenuCommande.equals("color"))
			{
				robi.setColor(Tools.getColorByName(argCmd1));
			}
			else if(contenuCommande.equals("translate"))
			{
				Point point = new Point(Integer.parseInt(argCmd2), Integer.parseInt(argCmd2));
				robi.translate(point);
			}
			else if(contenuCommande.equals("setColor"))
			{
				robi.setColor(Tools.getColorByName(argCmd1));
			}
			
			
		}
		else if(contenuNom.equals("space"))
		{
			if(contenuCommande.equals("color"))
			{
				space.setColor(Tools.getColorByName(argCmd1));
			}
			else if(contenuCommande.equals("setColor"))
			{
				space.setColor(Tools.getColorByName(argCmd1));
			}
			else if(contenuCommande.equals("sleep"))
			{
				Tools.sleep(Integer.parseInt(argCmd1));
			}
		}
		
	}

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}
