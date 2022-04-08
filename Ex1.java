package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice1_0 
{
	GSpace space = new GSpace("Exercice 1", new Dimension(200, 150));
	GRect robi = new GRect();

	public Exercice1_0() 
	{
		space.addElement(robi);
		space.open();
		
		while(true) //Boucle pour balader le robi à l'infini
		{
			robi.setX(0);
			robi.setY(0);
			
			for(int i = 0; i < space.getWidth() - robi.getWidth() ; i ++)  //Déplacement coin supérieur gauche vers supérieur droit
			{
				robi.setX(i);
				robi.setY(0);
				try { 
					Thread.sleep(10); //Pause de 10ms pour la fluidité de mouvement et pour ne pas boucler trop vite
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(int j = 0; j < space.getHeight() - robi.getHeight() ; j ++) //Déplacement coin supérieur droit vers inférieur droit
			{
				robi.setY(j);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(int i = robi.getX(); i>0; i --) //Déplacement coin inférieur droit vers inférieur gauche
			{
				robi.setX(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(int j = robi.getY(); j > 0 ; j --) //Déplacement coin inférieur gauche vers supérieur gauche
			{
				robi.setX(0);
				robi.setY(j);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			robi.setColor(new Color((int) (Math.random() * 0x1000000)));
		}
	}

	public static void main(String[] args) 
	{
		new Exercice1_0();
	}

}
