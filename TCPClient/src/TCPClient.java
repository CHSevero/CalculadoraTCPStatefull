import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TCPClient {
    public static void main (String args[]) {
        Scanner leitura = new Scanner(System.in);

        // arguments supply message and hostname
        Socket s = null;
        try{
            int serverPort = 7896;
            s = new Socket("localhost", serverPort,InetAddress.getLocalHost(), 50006);
            DataInputStream in = new DataInputStream( s.getInputStream());
            DataOutputStream out =new DataOutputStream( s.getOutputStream());

            String conta;

            System.out.println("Digite número operador número OU /n");
            System.out.println("Digite número operador OU/n");
            System.out.println("Digite  número OU/n");
            System.out.println("Digite  operador OU/n");

            conta = leitura.nextLine();



            out.writeUTF(conta);      	// UTF is a string encoding see Sn. 4.4
            String data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: "+ data) ;
        }catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){System.out.println("readline:"+e.getMessage());
        }finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
}