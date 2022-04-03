package exercice4;

 /*
	(space setColor black)  
	(robi setColor yellow) 
	(space sleep 2000) 
	(space setColor white)  
	(space sleep 1000) 	
	(space add robi (GRect new))
	(robi setColor green)
	(robi translate 100 50)
	(space del robi)
	(robi setColor red)		  
	(space sleep 1000)
	(robi translate 100 0)
	(space sleep 1000)
	(robi translate 0 50)
	(space sleep 1000)
	(robi translate -100 0)
	(space sleep 1000)
	(robi translate 0 -40) ) 
	
	
(space add robi (rect.class new))
(robi translate 130 50)
(robi setColor yellow)
(space add momo (oval.class new))
(momo setColor red)
(momo translate 80 80)
(space add pif (image.class new alien.gif))
(pif translate 100 0)
(space add hello (label.class new "Hello world"))
(hello translate 10 10)
(hello setColor black)

*/


import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exercice4.Exercice4_1_0.Command;
import exercice4.Exercice4_1_0.Reference;
import graphicLayer.GElement;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;


public class Exercice4_2_0 
{
	// Une seule variable d'instance
	Environment environment = new Environment();
	
	public interface Command 
	{
		// receiver : objet qui va executer method (space ou robi)
		// method : la s-expression resultat de la compilation du code source a executer : "(space setColor black)"
		abstract public Reference run(Reference receiver, SNode method);
	}
	
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
		
		public void addCommand(String selector, SetColor cmd)
		{
			//...
		}
		
		public void addCommand(String selector, Translate cmd)
		{
			//...
		}
		
		public void addCommand(String selector, SetDim cmd)
		{
			//...
		}
		
		public void addCommand(String selector, Sleep cmd)
		{
			//...
		}
		
		public void addCommand(String selector, AddElement cmd)
		{
			//...
		}
		
		public void addCommand(String selector, DelElement cmd)
		{
			//...
		}
		
		public void addCommand(String selector, NewElement cmd)
		{
			//...
		}
		
		public void addCommand(String selector, NewImage cmd)
		{
			//...
		}
		
		public void addCommand(String selector, NewString cmd)
		{
			//...
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
		
	public class SetColor
	{
		//...
	}
		
	public class Translate
	{			
		//...
	}
		
	public class SetDim
	{
		//...
	}
	
	public class Sleep
	{
		//...
	}
	
	public class AddElement
	{
		//...
	}
	
	public class DelElement
	{
		//...
	}
	
	public class NewImage
	{
		//...
	}
	
	public class NewString
	{
		//...
	}
	

	
	class NewElement implements Command 
	{
		public Reference run(Reference reference, SNode method) 
		{
			try 
			{
				@SuppressWarnings("unchecked")
				GElement e = ((Class<GElement>) reference.getReceiver()).getDeclaredConstructor().newInstance();
				Reference ref = new Reference(e);
				ref.addCommand("setColor", new SetColor());
				ref.addCommand("translate", new Translate());
				ref.addCommand("setDim", new SetDim());
				return ref;
			} 
			catch (Exception e) 
			{
			      e.printStackTrace();
		    }
			
			return null;
		}
	}

	public Exercice4_2_0() 
	{
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());

		spaceRef.addCommand("add", new AddElement());
		spaceRef.addCommand("del", new DelElement());
		
		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);
		
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
				new Interpreter().compute(environment, itor.next());
			}
		}
	}

	public static void main(String[] args) 
	{
		new Exercice4_2_0();
	}

}
