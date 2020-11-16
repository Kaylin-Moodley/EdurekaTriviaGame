/*
Filename: Server.java         
Author: Kaylin Moodley
Created: 25/08/2020
Operating System: Windows 10
*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    //Variable Declaration
    private ServerSocket server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String welcomeMessage;
    private String[] operators={"+","-","*","/"};//String array of operators
    private String randOperator;
    private int arrayLength;
    private int randIndex;
    private int num1;
    private int num2;
    private String problem;
    private String clientAnswer;
    private int serverAnswer;


    public void runServer() throws IOException, Exception
    {
        try
        {   
            server =new ServerSocket(8000);//run on port 8000
            System.out.println("Server is starting...");//display starting message
            socket=server.accept();
            input = new ObjectInputStream( socket.getInputStream()); 
            output = new ObjectOutputStream( socket.getOutputStream());
            output.flush();
            
            this.welcomeMessage="Welcome to Edureka Trivia Game";
            output.writeObject( this.welcomeMessage );//send welcome message
            output.flush();
            
            while(true)
            {
                //generate random numbers
                this.num1=(int) ((Math.random() * (10 - 1 + 1) ) + 1);
                this.num2=(int) ((Math.random() * (10 - 1 + 1) ) + 1);

                this.arrayLength =this.operators.length;
                
                //generate random numbers
                for (int i = 0; i < this.arrayLength; i++)
                {
                    this.randIndex = (int) (Math.random() * this.arrayLength);
                    this.randOperator=this.operators[this.randIndex];
                }

                this.problem="Question from the Server: What is "+this.num1+" "+randOperator+" "+this.num2+" ?";
                output.writeObject( this.problem );//send problem to client
                output.flush();

                if(this.randOperator.equals("+"))
                {
                    this.serverAnswer=this.num1+this.num2;
                }  
                else if(this.randOperator.equals("-"))
                {
                    this.serverAnswer=this.num1-this.num2;
                }
                else if(this.randOperator.equals("*"))
                {
                    this.serverAnswer=this.num1*this.num2;
                }
                else
                {
                    this.serverAnswer=Math.round(this.num1/this.num2);
                }


               this.clientAnswer= (String) input.readObject();
               System.out.println("Answer from Client: "+this.clientAnswer);
               if(this.clientAnswer.equals("stop"))
               {
                        output.close();
                        input.close();
                        socket.close();
                        server.close();
                        break;
               }
               else
               {
                 output.writeObject( this.serverAnswer );
                 output.flush();
               }
            }
          
           
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }   
        catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws IOException, Exception
    {
        Server serverSide = new Server();
        serverSide.runServer();
    }
}
