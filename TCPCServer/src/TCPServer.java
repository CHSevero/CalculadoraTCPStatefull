import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TCPServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
}
class Connection extends Thread {

    private static Map<String, String> mySocket = new ConcurrentHashMap<>();

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
    public void run(){
        try {			                 // an echo server

            String data = in.readUTF();// read a line of data from the stream
            String reply;
            if(mySocket.get(clientSocket.toString()) == null){
                if(data.split(" ").length == 3){

                    reply = calculadora(data);
                    mySocket.put(clientSocket.toString(), reply);
                    out.writeUTF(reply);
                }else{

                    mySocket.put(clientSocket.toString(), data);
                    out.writeUTF(data+" foi armazenado no servidor");
                }
            }else{
                if(data.split(" ").length == 3){

                    reply = calculadora(data);
                    mySocket.put(clientSocket.toString(), reply);
                    out.writeUTF(reply);
                }else if((mySocket.get(clientSocket.toString())+" "+data).split(" ").length == 3){


                    System.out.println((mySocket.get(clientSocket.toString())+" "+data));
                    reply = calculadora(((mySocket.get(clientSocket.toString())+" "+data)));
                    mySocket.put(clientSocket.toString(), reply);
                    out.writeUTF(reply);
                }else{

                    reply = (mySocket.get(clientSocket.toString())+" "+data);
                    mySocket.put(clientSocket.toString(), reply);
                    out.writeUTF(reply);
                }
            }




        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}


    }

    public  String calculadora(String conta){

        String lista[] = conta.split(" ");
        try{
            if(lista[0].equals("+") || lista[0].equals("-")  || lista[0].equals("*") || lista[0].equals("/")){

                switch (lista[0]){
                    case "+":
                        return String.valueOf(Double.parseDouble(lista[1]) + Double.parseDouble(lista[2]));
                    case "-":
                        return String.valueOf(Double.parseDouble(lista[1]) - Double.parseDouble(lista[2]));
                    case "*":
                        return String.valueOf(Double.parseDouble(lista[1]) * Double.parseDouble(lista[2]));
                    case "/":
                        if(lista[2]=="0"){
                            return "Impossível dividir por 0!";
                        }
                        return String.valueOf(Double.parseDouble(lista[1]) / Double.parseDouble(lista[2]));
                    default:
                        return "Operação não encontrada!";
                }
            }
            if(lista[1].equals("+") || lista[1].equals("-")  || lista[1].equals("*") || lista[1].equals("/")){

                switch (lista[1]){
                    case "+":
                        return String.valueOf(Double.parseDouble(lista[0]) + Double.parseDouble(lista[2]));
                    case "-":
                        return String.valueOf(Double.parseDouble(lista[0]) - Double.parseDouble(lista[2]));
                    case "*":
                        return String.valueOf(Double.parseDouble(lista[0]) * Double.parseDouble(lista[2]));
                    case "/":
                        if(lista[2]=="0"){
                            return "Impossível dividir por 0!";
                        }
                        return String.valueOf(Double.parseDouble(lista[0]) / Double.parseDouble(lista[2]));
                    default:
                        return "Operação não encontrada!";
                }
            }
            if(lista[2].equals("+") || lista[2].equals("-")  || lista[2].equals("*") || lista[2].equals("/")){

                switch (lista[2]){
                    case "+":
                        return String.valueOf(Double.parseDouble(lista[0]) + Double.parseDouble(lista[1]));
                    case "-":
                        return String.valueOf(Double.parseDouble(lista[0]) - Double.parseDouble(lista[1]));
                    case "*":
                        return String.valueOf(Double.parseDouble(lista[0]) * Double.parseDouble(lista[1]));
                    case "/":
                        if(lista[1]=="0"){
                            return "Impossível dividir por 0!";
                        }
                        return String.valueOf(Double.parseDouble(lista[0]) / Double.parseDouble(lista[1]));
                    default:
                        return "Operação não encontrada!";
                }
            }

            return "Expreção fora do padrão.";
        }catch (Exception e){
            return "Expreção fora do padrão.";
        }

    }
}

