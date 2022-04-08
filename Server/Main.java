package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import Server.Message;

public class Main {
	// fonctionnalités : Possibilités de lancer plusieurs scripts robi en même temps via threads
	//					 Plusieurs clients sur un seul serveur
	//				     script exit qui éteint le serveur et le client
	// 					 Interpreteur de l'exercice 3 
	public static void main(String[] args) throws Exception {
		System.out.println("SERVEUR : EN LIGNE");
		String text2;
		Message m = null; // création objet message
		String stop ="exit";
		Code Th = null;
		// Création d'un objet message null pour être utilisé plus tard
		try {
			ServerSocket serveurSoc = new ServerSocket(8000); // Création de la socket serveur
			 // utilisation d'un label pour faciliter le break
			loop:while(true){
				Socket socket = serveurSoc.accept();// Bloquant , tant qu'aucun client n'est connecté
				System.out.println ("////////////////////////////////////////////////////////");
				System.out.println ("////////////////////////////////////////////////////////");
				System.out.println ("////////////////////////////////////////////////////////");
				BufferedReader bufRead   = new BufferedReader( new InputStreamReader(socket.getInputStream()));// Lecture buffer
		        BufferedWriter bufWrite = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));// buffer d'écriture
		        	       text2 = bufRead.readLine() ;
		        	       if (text2 != null)
		        	    	   System.out.println ("Un client a envoye un message");
		        	       //(space setColor black)(space sleep 1000)(robi setColor yellow)(space sleep 1000)(space setColor white)(space sleep 1000)(robi setColor red)(robi translate 100 0)(space sleep 1000)(robi translate 0 50)(space sleep 1000)(robi translate -100 0)
		                   if (text2 != null)
		                    {
		                    	System.out.println("le client a transmis: " + text2);                          
		                    } 
		
		                    bufWrite.write("ACCUSE DE RECEPTION") ; // envoie l'accuse de reception au client
		                    bufWrite.newLine();
		                    bufWrite.flush();
		                    
						m = Message.fromJson(text2);
						String value = new String();
						value = m.getMess();
						System.out.println("Type : " + m.getType() + " \nMess : " +m.getMess());
						if (Objects.equals(value, stop)) { // si exit est écrit, alors...
							System.out.println("Fermeture du serveur");
							socket.close();
							break loop;// permet de sortir de la boucle while
						}if(Objects.equals(m.getMess(), "stop")) {
							Th.interrupt();// interrompt le programme en cours
							System.out.println("Interruption du programme");
							break;
						}else {
						
							Th = new Code(m); // Ici démarrage du thread avec l'objet M (message) en argument
							Th.start();
							if(Th.isAlive()) {
								System.out.println("Programme en cours");
							}
							
							bufRead.close(); // fermeture des buffers
							bufWrite.close();
						}
					}
			
			serveurSoc.close();
			System.out.println("Serveur ferme");	
		}	
					
					catch (Exception e ) {
						System.out.println("Serveur deco: ");
					
		        }
		
		
	}
}
