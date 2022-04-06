package exercice4;

// 
//	(space setColor black)  
//	(robi setColor yellow) 
//	(space sleep 2000) 
//	(space setColor white)  
//	(space sleep 1000) 	
//	(robi setColor red)		  
//	(space sleep 1000)
//	(robi translate 100 0)
//	(space sleep 1000)
//	(robi translate 0 50)
//	(space sleep 1000)
//	(robi translate -100 0)
//	(space sleep 1000)
//	(robi translate 0 -40) ) 
//

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;



public class Exercice4_1_0 
{
	public interface Command 
	{
		// receiver : objet qui va executer method (space ou robi)
		// method : la s-expression resultat de la compilation du code source a executer : "(space setColor black)"
		abstract public Reference run(Reference receiver, SNode method);
	}
	
	/*  permet de référencer un objet graphique à un ensemble de commandes nommées */
	public class Reference 
	{
		Object receiver; //space ou robi (objet graphique) : GSpace ou Grect
		Map<String, Command> primitives; //commandes primitives disponibles pour le receiver. (setColor et translate, pour robi)
		
		public Object getReceiver() 
		{
			return receiver;
		}

		public void setReceiver(Object receiver) 
		{
			this.receiver = receiver;
		}
		
		public Reference(Object receiver) 
		{ 
			this.receiver = receiver;
			primitives = new HashMap<String, Command>();
		} 
		
		public Command getCommandByName(String selector) //selector : commande à éxecuter (setColor)
		{
			return primitives.get(selector);
		}
		
		public void addCommand(String selector, Command primitive)
		{
			primitives.put(selector, primitive);
		}
		
		public Reference run(SNode method) //SNode method :  (space setColor black)
		{	
			String command = method.get(1).contents();
			
			Command var = getCommandByName(command);
			
			return var.run(this, method);
		}
		
	}
	
	//c’est l’environnement qui contient l’ensemble des références (deux jusqu’à présent, space et robi sont les seuls objets référencés).
	public class Environment 
	{
		HashMap<String, Reference> variables;
		
		public Environment() 
		{
			variables = new HashMap<String, Reference>();
		} 
		
		public void addReference(String name, Reference objet)
		{
			variables.put(name, objet);
		}
		
		public Reference getReferenceByName(String name)
		{
			return variables.get(name);
		}
	}
	
	
	// Une seule variable d'instance
	Environment environment = new Environment();

	public Exercice4_1_0() 
	{
		// space et robi sont temporaires ici
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		GRect robi = new GRect();

		space.addElement(robi);
		space.open();

		Reference spaceRef = new Reference(space);
		Reference robiRef = new Reference(robi);

		// Initialisation des references : on leur ajoute les primitives qu'elles
		// comprenent
		//
		// <A VOUS DE CODER>
		
		spaceRef.addCommand("setColor", new Command() {
			public Reference run(Reference receiver, SNode method) 
			{  
				((GSpace)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
				return null;
			} 
		});
		
		spaceRef.addCommand("sleep", new Command() {
			public Reference run(Reference receiver, SNode method) 
			{  
				Tools.sleep(Integer.parseInt(method.get(2).contents()));
				return null;
			} 
		});
		
		robiRef.addCommand("setColor", new Command() {
			public Reference run(Reference receiver, SNode method) 
			{  
				((GRect)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
				return null;
			} 
		});
		
		robiRef.addCommand("translate", new Command() {
			public Reference run(Reference receiver, SNode method) 
			{  
				Point newPoint = new Point();
				
				newPoint.x = Integer.parseInt(method.get(2).contents());
				newPoint.x = Integer.parseInt(method.get(3).contents());
				
				((GRect)receiver.getReceiver()).translate(newPoint);
				return null;
			} 
		});
		
		// Enrigestrement des references dans l'environement par leur nom
		environment.addReference("space", spaceRef);
		environment.addReference("robi", robiRef);

		this.mainLoop();
	}

	private void mainLoop() 
	{
		while (true) {
			// prompt
			System.out.print("> ");
			// lecture d'une serie de s-expressions au clavier (return = fin de la serie)
			String input = Tools.readKeyboard();
			// creation du parser
			SParser<SNode> parser = new SParser<>();
			// compilation
			List<SNode> compiled = null;
			try {
				compiled = parser.parse(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// execution des s-expressions compilees
			Iterator<SNode> itor = compiled.iterator();
			while (itor.hasNext()) {
				this.run((SNode) itor.next());
			}
		}
	}

	private void run(SNode expr) {
		// quel est le nom du receiver
		String receiverName = expr.get(0).contents();
		// quel est le receiver
		Reference receiver = environment.getReferenceByName(receiverName);
		// demande au receiver d'executer la s-expression compilee
		receiver.run(expr);
	}

	public static void main(String[] args) {
		new Exercice4_1_0();
	}

}
