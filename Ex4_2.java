package exercice4;

import java.awt.Color;

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
import java.awt.Font;
import java.io.File;
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

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Exercice4_2_0 
{
	// Une seule variable d'instance
	Environment environment = new Environment();
	
	/* Les classes qui modélisent des commandes devront implémenter cette interface (ex : SPaceChangeColor implements Command) */
	public interface Command 
	{
		// Reference : receiver = ( (GSpace) space, (GRect) robi...), l'objet qui concerné par la commande dans la S-expression
		// SNode : method = la s-expression resultat de la compilation du code source a executer : "(space setColor black)"
		abstract public Reference run(Reference receiver, SNode method);
	}
	
	/* Référencie un objet graphique à un ensemble de commandes nommées*/
	public class Reference 
	{
		Object receiver; //space ou robi (objet graphique) : GSpace, Grect, GOval...
		Map<String, Command> primitives; //commandes primitives disponibles pour le receiver. (setColor, translate, pour robi)
		
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
		
		public void addCommand(String selector, SetColor cmd) // "setColor" -> SetColor
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, Translate cmd) // "translate" -> translate
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, SetDim cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, Sleep cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, AddElement cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, DelElement cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, NewElement cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, NewImage cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public void addCommand(String selector, NewString cmd)
		{
			primitives.put(selector, cmd);
		}
		
		public Reference run(SNode method) //SNode method :  (space setColor black)
		{	
			String command = method.get(1).contents();
			
			Command var = getCommandByName(command);
			
			return var.run(this, method);
		}
		
	}
	
	/* Contient l’ensemble des références ( "space" <-> spaceRef , "rect.class" <-> rectClassRef...) */
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
	
	/* Command : éxecute la commande demandée (setColor) */
	public class SetColor implements Command
	{
		//pas de constructeur (par défaut, ne fait rien)
		
		@Override
		public Reference run(Reference receiver, SNode method) 
		{	
			Object classe = receiver.getReceiver().getClass();
				
			if(classe.equals(GRect.class))
			{
				((GRect)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
			}
			else if(classe.equals(GSpace.class))
			{
				((GSpace)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
			}
			else if(classe.equals(GOval.class))
			{
				((GOval)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
			}
			else if(classe.equals(GImage.class))
			{
				((GImage)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
			}
			else if(classe.equals(GString.class))
			{
				((GString)receiver.getReceiver()).setColor(Tools.getColorByName(method.get(2).contents()));
			}
			
			return null;
		}
	}
	
	public class Translate implements Command //(pif translate 100 0)
	{
		@Override
		public Reference run(Reference receiver, SNode method) 
		{
			Object classe = receiver.getReceiver().getClass();
			
			Point newPoint = new Point();
			
			newPoint.x = Integer.parseInt(method.get(2).contents());
			newPoint.y = Integer.parseInt(method.get(3).contents());
			
			if(classe.equals(GRect.class))
			{
				((GRect)receiver.getReceiver()).translate(newPoint);
			}
			else if(classe.equals(GOval.class))
			{
				((GOval)receiver.getReceiver()).translate(newPoint);
			}
			else if(classe.equals(GImage.class))
			{
				((GImage)receiver.getReceiver()).translate(newPoint);
			}
			else if(classe.equals(GString.class))
			{
				((GString)receiver.getReceiver()).translate(newPoint);
			}
			
			return null;
		}			

	}
	
	/* Redéfinit les dimensions d'un GRect, NE MARCHE QUE SUR UN GRect ET GOval */
	public class SetDim implements Command
	{
		@Override
		public Reference run(Reference receiver, SNode method) {
			receiver= environment.getReferenceByName(method.get(0).contents());
			Dimension dimension;
			Point Point = new Point();
			Point.x = Integer.parseInt(method.get(2).contents());
			Point.y = Integer.parseInt(method.get(3).contents());
			dimension = new Dimension(Point.x,Point.y);
			Object classe = receiver.getReceiver().getClass();
			
			if(classe.equals(GRect.class))
			{
				((GRect)receiver.receiver).setDimension(dimension);
			}
			else if(classe.equals(GOval.class))
			{
				((GOval)receiver.receiver).setDimension(dimension);
			}
			
			return null;
		}
	}
	
	public class Sleep implements Command //(space sleep 10)
	{
		@Override
		public Reference run(Reference receiver, SNode method) 
		{
			Tools.sleep(Integer.parseInt(method.get(2).contents()));
			
			return null;
		}
	}
	
	/*
	 * AddElement() & DelElement() : sont des commandes réferencées seulement par space
	 * 
	 * Reference.receiver => space
	 * Reference.primitives => "add" - AddElement / "del" - DelElement
	 * 
	 */
	
	/* Ajoute à l'environnement la référence (ex : (space add robi (rect.class new)) , (space add momo (oval.class new)), (space add pif (image.class new alien.gif) )) */
	public class AddElement implements Command
	{	
		@Override
        public Reference run(Reference receiver, SNode method) //(space add robi (rect.class new)), (space add momo (oval.class new)), (space add pif (image.class new alien.gif))
		{
            String nameNew = method.get(2).contents(); //robi
            
            //On execute NewElement pour récupérer sa Référence
            Reference RefOfNewObject = new Interpreter().compute(environment, method.get(3)); //method.get(3) = rect.class et sa primitive est new qui a deja été associée à NewElement

            environment.addReference(nameNew, RefOfNewObject);
            
            GSpace space = ((GSpace)receiver.getReceiver());
            space.addElement( (GElement)RefOfNewObject.getReceiver() );
            
           return RefOfNewObject;
        }
	}
	
	/* Retire un objet du GSpace (ne le supprime pas de l'environnement, ni des références) */
	public class DelElement implements Command 
	{
		@Override
		public Reference run(Reference receiver, SNode method) //(space del robi)
		{
			String nameADelete = method.get(2).contents(); //robi
            
            //on récupère l'objet à delete
            Reference RefObjADelete = environment.getReferenceByName(nameADelete);

            //On retire l'objet du GSpace
            GSpace space = ((GSpace)receiver.getReceiver());
            space.removeElement( (GElement)RefObjADelete.getReceiver() );
            
           return RefObjADelete;
		}
	}
	
	/* Méthode similaire à NewElement mais différent pour traiter les images */
	public class NewImage implements Command //(space add pif (image.class new alien.gif))
	{

		@Override
		public Reference run(Reference reference, SNode method) 
		{
			try 
			{				
				File file = new File(method.get(2).contents()); //On ouvre un File vers le fichier que l'on souhaite traiter
				BufferedImage bufferedImage = ImageIO.read(file); //On lit l'image grace à BufferedImage (tableau de pixels en mémoire)
				 
				GImage image = new GImage(bufferedImage);
				Reference ref = new Reference(image);
				
				ref.addCommand("translate", new Translate());
				
				return ref;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	/* Méthode similaire à NewElement mais différent pour traiter les String */
	public class NewString implements Command //(space add txt (image.class new alien.gif))
	{
		/* !!!!!!! A MODIFIER !!!!!!! */
		
		@Override
		public Reference run(Reference receiver, SNode method)
		{
			try 
			{
				GString Chaineaajouter = new GString();
				
				Chaineaajouter.setString(method.get(2).contents());
				Chaineaajouter.setFont(new Font("Impact", Font.ITALIC, 32));
				Chaineaajouter.setColor(Color.blue);
				
				Reference refe = new Reference(Chaineaajouter);
				refe.addCommand("setColor", new SetColor());
				refe.addCommand("translate", new Translate());
				return refe;
			} 
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	/*
	 * Gère la création du nouvel l'objet et créé une réference : 
	 * 
	 * ex : (GSpace/GOval/G...) Object receiver
	 * 		primitives : "setColor" - SetColor(), "translate" - Translate(), "setDim" - SetDim()
	 * 
	 */
	
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
			
			return null; //cas d'erreur
		}
	}

	public Exercice4_2_0() 
	{
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class); // Utilisation de la classe Class, c'est le point d'entrée de l'exploration du contenu d'une classe par introspection
		Reference ovalClassRef = new Reference(GOval.class); // Une des 3 façons d'obtenir une instance de Class, est d'appeler cet objet directement (ex : GRect.class, GOval.class..)
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());
		spaceRef.addCommand("setDim", new SetDim());

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
	
	/* Récupère la référence du script (ex : (rect.class new), soit rect.class) et execute sa méthode run avec la commande (new ici) */
	public class Interpreter
	{
		//pas de constructeur
		
		public Reference compute(Environment environment, SNode method) 
		{
			String stringOfRef = method.get(0).contents(); //method.get(0).contents() = rect.class
			Reference ref = environment.getReferenceByName(stringOfRef); //on recupere cette référence 
			
			ref = ref.run(method); //Execute la commande compris dans le script à partir de la référence (execute "new")
			
			return ref;
		}
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
