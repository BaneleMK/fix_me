package routergroupid;
import java.net.*;

import routergroupid.fix_message.fix_message;

import java.io.*;

public class Main{
    public static Socket nyseSocket;
    public static Socket lseSocket;
    public static Socket jseSocket;
    public static void main(String[] args){
        try{
            Thread brokerThread = new Thread(new SuperHandler(new ServerSocket(5000)));brokerThread.start();
            Thread marketThread = new Thread(new SuperHandler(new ServerSocket(5001)));marketThread.start();
        } catch(IOException e){System.err.println(e.getMessage());}
    }
}
class SuperHandler implements Runnable{
    ServerSocket serverSocket;
    SuperHandler(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        System.out.println("Listener started on: " + serverSocket.getLocalPort());
    }
    @Override
    public void run() {
        try{
            while(true){        
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new SubHandler(socket));
                thread.start();
            }
        } catch (IOException e){System.err.println(e.getMessage());}
    }
}
class SubHandler implements Runnable{
    private Socket socket;
    private String uniqueID;
    SubHandler(Socket socket){
        this.socket = socket;
        this.uniqueID = socket.toString();
        this.uniqueID = takeOutUnnecessaryChars(this.uniqueID);
    }
    @Override
    public void run(){

        try{
            System.out.println("\nClient connected:\n" + uniqueID + "\n");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputStreamLine = bufferedReader.readLine();
            if(inputStreamLine == null){System.out.println("\nClient disconnected:\n" + uniqueID + "\n");}
            if(socket.getLocalPort() == 5001){
                switch (inputStreamLine){
                    case "NYSE": Main.nyseSocket = this.socket; break;
                    case "LSE": Main.lseSocket = this.socket; break;
                    case "JSE": Main.jseSocket = this.socket; break;
                    default: break;
                }
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Hit enter to refresh");printWriter.flush();

            while(true){

                //READING
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                inputStreamLine = bufferedReader.readLine();
                if(inputStreamLine == null){System.out.println("\nClient disconnected:\n" + uniqueID + "\n");break;} //this breaks the loop
                System.out.println("\n" + uniqueID + "\n" + inputStreamLine + "\n");

                //WRITING
                printWriter = new PrintWriter(socket.getOutputStream());
                if(socket.getLocalPort() == 5000){
                    switch(get_Market(inputStreamLine)){
                    //THIS IS WHERE THE FIX MESSAGE NEEDS TO BE PARSED AND SENT TO THE MARKET
                        case "1":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "2":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "3":   
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.nyseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.nyseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "4":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "5":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "6":   
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.lseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.lseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "7":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("1");printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            printWriter.println(inputStreamLine);printWriter.flush(); //THIS WILL BE A FIX MESSAGE
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        case "8":
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("2");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break; 
                        case "9":   
                            System.out.println("Broker message: "+inputStreamLine);           
                            printWriter = new PrintWriter(Main.jseSocket.getOutputStream());
                            // printWriter.println("3");printWriter.flush();
                            printWriter.println(inputStreamLine);printWriter.flush();
                            bufferedReader = new BufferedReader(new InputStreamReader(Main.jseSocket.getInputStream()));
                            inputStreamLine = bufferedReader.readLine();
                            printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.println(inputStreamLine);printWriter.flush();
                            break;
                        default:    printWriter.println("Press 1 to view available markets");printWriter.flush(); break;
                    }
                    
                }else if(socket.getLocalPort() == 5001){
                    printWriter.println("");printWriter.flush();
                }
            }
        } catch(IOException e){System.err.println(e.getMessage());}
    }

    private static long ascii_sum(String data){
        long tempsum = 0;
        int length = data.length();
        for (int i = 0; i < length ; i++) {
            // the '[space]' and '|' represent the start of header ascii which has a value of 1
            if (data.charAt(i) == ' ' || data.charAt(i) == '|')
                tempsum += 1;
            else
                tempsum += data.charAt(i);
        }
        return tempsum;
    }

    // the message_elements are as follows "ID;iNSTRUMENT;QUANTITY;MARKET;PRICE"
    // example of message_elements "0001;guitar;4;rando;10000"
    // example output "ID=0001|Instr=guitar|Quant=4|Market=rando|Price=10000|10=185"
    public String makefix(String message_elements){
        String elements [] = message_elements.split(";");
        String fixmessage = 
        "ID="+elements[0]+
        "|Instr="+elements[1]+
        "|Quant="+elements[2]+
        "|Market="+elements[3]+
        "|Price="+elements[4]+"|";
        fixmessage += "10="+ascii_checksum(fixmessage);
        return fixmessage;
    }

    public String makefix_market(String message_elements){
        String elements [] = message_elements.split(";");
        String fixmessage = 
        "Broker_ID="+elements[0]+
        "|status="+elements[1]+"|";
        fixmessage += "10="+ascii_checksum(fixmessage);
        return fixmessage;
    }

    // This is just taking the full fixmessage recieved
    // it checking if the checksum matches up or not to the data contained
    public boolean checksum_verification(String message){
        String elements [] = message.split("10=");
        if (ascii_checksum(elements[0]) != Integer.valueOf(elements[1]))
            return false;
        else 
            return true;
    }

    public String get_ID(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String ID = elem[0].split("=")[1];
            return ID;
        }
        return "0";
    }

    public String get_Instrument(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String instr = elem[1].split("=")[1];
            return instr;
        }
        return "0";
    }

    public int get_Quantity(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            int quant = Integer.parseInt(elem[2].split("=")[1]);
            return quant;
        } 
        return 0;
    }

    public String get_Market(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            String market = elem[3].split("=")[1];
            return market;
        } 
        return "0";
    }

    public int get_Price(String fixmessage){
        if (!fixmessage.isBlank()){
            String[] elem = fixmessage.split("\\|");
            int price = Integer.parseInt(elem[4].split("=")[1]);
            return price;
        }
        return 0;
    }

    // this takes the data without the checksum and counts the ascii to come up with the checksum number
    private long ascii_checksum(String data){
        long temp_sum = ascii_sum(data);
        long temp_checksum = 0;
        temp_checksum = temp_sum % 256;
        return temp_checksum;
    }
    public static String takeOutUnnecessaryChars(String uniqueID){
        StringBuilder stringBuilder = new StringBuilder();
        String s0 = uniqueID.split("=")[0];
        String s1 = uniqueID.split("=")[1];
        String s2 = uniqueID.split("=")[2];
        String s3 = uniqueID.split("=")[3];
        stringBuilder.append(s0);
        stringBuilder.append(s1);
        stringBuilder.append(s2);
        stringBuilder.append(s3);
        return(stringBuilder.toString());
    }
}

/*
System.out.println("Listener started on: \n" + 
serverSocket.getLocalPort() + "\n" +
serverSocket.getInetAddress() + "\n" +
serverSocket.getLocalSocketAddress() + "\n");

System.out.println("Client connected: \n" +
socket.getInetAddress() + "\n" +
socket.getLocalAddress() + "\n" +
socket.getLocalPort() + "\n" +
socket.getLocalSocketAddress() + "\n" +
socket.getPort() + "\n" +
socket.getRemoteSocketAddress() + "\n" +
socket.isBound() + "\n" +
socket.isClosed() + "\n" +
socket.isConnected() + "\n" +
socket.isInputShutdown() + "\n" +
socket.isOutputShutdown());
 */


// 0000;1;6;0