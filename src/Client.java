/*
Filename: Client.java         
Author: Kaylin Moodley
Created: 25/09/2020
Operating System: Windows 10
*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client  
{
    
    //Variable Declaration
    private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input;// input stream from server
    private Socket socket; // socket to communicate with server
    private String welcomeMessage;
    private String problem;
    private String clientAnswer;//clients solution
    private int serverAnswer;
    private String result;
    private String incorrectMessage;
    private String correctMessage;
    
    public void runClient() throws ClassNotFoundException
    {
        try 
        {
            Scanner scanInput=new Scanner(System.in);
  
            socket = new Socket("Localhost", 8000 );
            output = new ObjectOutputStream( socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream( socket.getInputStream());

            this.welcomeMessage= (String) input.readObject();//receive welcome message
            System.out.println(this.welcomeMessage);
            
            while(true)
            {
                this.problem= (String) input.readObject();//receive problem
                System.out.println(this.problem);

                this.clientAnswer=scanInput.next();
                output.writeObject(this.clientAnswer);
                output.flush();//flush clients solution to client

                this.serverAnswer=(int) input.readObject();

                if(this.clientAnswer.equals("stop"))
                {
                        socket.close();//close socket
                        output.close();
                        input.close();  
                        System.out.println("Thank you for playing, Good bye!");
                        break;
                }
                    int intClientAnswer=Integer.parseInt(this.clientAnswer);
                    
                            
                    if(Math.round(intClientAnswer)==this.serverAnswer)
                    {
                        this.correctMessage="Welldone, that's correct!";
           ;            System.out.println(this.correctMessage);   
                    }
                    else
                    {
                         this.incorrectMessage="Wrong Answer! the correct answer is:"+this.serverAnswer;  
                         System.out.println(this.incorrectMessage);
                    }
                
            }

        }
        catch ( IOException ioe )
        {
                ioe.printStackTrace();
        } 
     } 
    
    public static void main(String[] args) throws ClassNotFoundException 
    {
	Client client=new Client();
	client.runClient();
    }
}
