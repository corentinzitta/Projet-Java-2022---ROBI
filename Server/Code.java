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

import Server.Message;

public class Code extends Thread {
	Message m;
		Code(Message m){
			this.m = m;
		}
	public void run() {
		System.out.println("Thread lancé"); // lancement du thread
		new Interpreter(m.getMess());// lancement de l'interpreteur
	          try {
	        	System.out.println("Thread a fonctionné"); // si fonctionne, le thread a bien executé l'interpreteur
	            Code.sleep(10) ;
	         }  catch (InterruptedException e) {
	        	 Code.currentThread().interrupt();
	             // gestion de l'erreur
	         }
	      }
	   }
