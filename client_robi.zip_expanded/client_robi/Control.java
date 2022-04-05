package client_robi;


import client_robi.Message;
import static java.util.logging.Level.SEVERE;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class Control extends Thread{
    private File fic;
    OutputStream ps;
    InputStream is;
    public Button b1;
    public Button b2;
    public TextArea textArea1;
    public TextArea textArea2;
    public String mess;
    String text;
    public String type;
    public String json;
    public void initialize() {
    	System.out.println("Client");
    }

    public void b1_exec(ActionEvent event) throws Exception {
    	System.out.println("Client execution");
    	mess = textArea1.getText();
    	System.out.println("Vous avez écrit :  = "+mess);
    	Message m = new Message("robi",mess);
    	json = Message.toJson(m);
    	System.out.println("Conversion :  = "+json);
    	Socket socket = new Socket("localhost",8000);
    	BufferedWriter Ecrire = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader Lire   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	// creation socket et envoie 
    	try {
	    
	    	//byte[] bytes = json.getBytes();
	    	//System.out.println("Bytes  :  = "+bytes);
	    	Ecrire.write(json);
	    	Ecrire.newLine();
	        Ecrire.flush();
	        while((text = Lire.readLine()) != null) 
			{					
				System.out.println("Recu : " + text);			
			}	
	    	System.out.println("Accuse de reception recu");
	    	
	    	
    	}
    	catch (Exception e) {
  		      System.out.println("Le Serveur a ete deconnecte.");
  		      
  		     }
    	Ecrire.close();
    	Lire.close();
    	socket.close();
    }
    public void b2_exec(ActionEvent event) {
        Platform.exit();
        System.exit(MAX_PRIORITY);
    }
    
    public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //only allow text files to be selected using chooser
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
        );

        //set initial directory somewhere user will recognise
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //let user select file
        File fileToLoad = fileChooser.showOpenDialog(null);
        
        if (fileToLoad != null) {
        	System.out.println("file = "+fileToLoad.getPath());
        }
        else {
        	System.out.println("file = null");
        }
        

     }


    public void saveFile(ActionEvent event) {
    }
}