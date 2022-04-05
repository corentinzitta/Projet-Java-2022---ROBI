package serv;

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
import serv.Message;
public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("SERVEUR");
		//InputStream is;
		//OutputStream ps;
		String ligne2;
		ServerSocket serveurSoc = new ServerSocket(8000);
		Socket socket = serveurSoc.accept();
		BufferedReader Lire2   = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        BufferedWriter Ecrire2 = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
		while(true) {
			try {
				
        	       ligne2 = Lire2.readLine() ;
        	       if (ligne2 != null)                    
    	    	   System.out.println (ligne2) ;               
                   if (ligne2 != null)
                    {
                    	System.out.println("le client a transmis: " + ligne2);                          
                    } 

                    Ecrire2.write("ACCUSE DE RECEPTION") ; 
                    Ecrire2.newLine();
                    Ecrire2.flush();
                     
              
					
                
				//System.out.println("Recu en JSON" + text);
				Message m = Message.fromJson(ligne2);
				System.out.println("Type : " + m.getType() + " \nMess : " +m.getMess());
				//ps = socket.getOutputStream();
		    	//byte[] bytes = "recu".getBytes();
		    	//System.out.println("Accusé de réception envoyé : ");
		    	//ps.write(bytes);
				//serveurSoc.close();
				//socket.close();
				if(m.getMess() == "exit");
					break;
		
                }
			
		
			catch (Exception e ) {
				System.out.println("Serveur deco: ");
				}        
					

		}
		Lire2.close();
		Ecrire2.close();
		socket.close();
		serveurSoc.close();
	}
}
