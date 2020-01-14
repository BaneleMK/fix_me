package brokergroupid;
import java.net.*; //Socket
import java.io.*; //Exception
import java.util.Scanner;
import brokergroupid.fix_message.fix_message;

public class Main{
    public static Scanner scanner;
    public static Socket socket;
    public static String uniqueID;


    public static void main(String[] args){
        try{
            socket = new Socket("localhost", 5000);
            uniqueID = convertToUniqueID(socket.toString());
            uniqueID = takeOutUnnecessaryChars(uniqueID);
            System.out.println(socket.toString());
            System.out.println(uniqueID);
            scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("I am a broker"); printWriter.flush();

            while(true){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputStreamLine = bufferedReader.readLine();
                if(inputStreamLine == null){System.out.println("\nServer disconnected\n");break;}
                System.out.println("\nServer\n" + inputStreamLine + "\n");
                printWriter = new PrintWriter(socket.getOutputStream());
                //System.out.println("Press 1 for NYSE instruments\nPress 2 for LSE instruments\nPress 3 for JSE instruments");
                System.out.println("Press number to buy stocks\nNYSE:\n1) 100 shares of Berkshire Hathaway\n2) 100 shares of JP Morgan Chase\n3) 100 shares of Exxon Mobil\n\nLSE:\n4) 100 shares of Royal Dutch Shell\n5) 100 shares of HSBC Holdings\n6) 100 shares of BP\n\nJSE:\n7) 100 shares of Investec\n8) 100 shares of Sasol\n9) 100 shares of Naspers\n");
                String brokerInput = scanner.nextLine();
                
                // id;instrument;quantity;market;price
                if (!brokerInput.isBlank()){
                    int instrument = (Integer.parseInt(brokerInput) % 3);
                    if (instrument == 0)
                        instrument = 3;
                    printWriter.println(fix_message.makefix(uniqueID+";"+ Integer.toString(instrument)+";100;"+brokerInput+";0")); printWriter.flush();
                } else
                    printWriter.println(fix_message.makefix(uniqueID+";1;100;0;0")); printWriter.flush();
                
                // THIS IS WHERE THE INPUT NEEDS TO BE TRANSFORMED INTO A FIX MESSAGE AND SENT TO THE ROUTER
                //printWriter.println(brokerInput); printWriter.flush();
            }
        } catch (IOException e){System.err.println("broker: "+e.getMessage());}
    }
    public static String convertToUniqueID(String unflippedString1){
        
        StringBuilder stringBuilder = new StringBuilder();
        String unflippedString = unflippedString1.split("]")[0];
        String string1 = unflippedString.split(",")[0];
        String string2 = unflippedString.split(",")[1];
        String string3 = unflippedString.split(",")[2];
        String string8 = string1.split("/")[1];
        String string4 = string2.split("=")[0];
        String string5 = string2.split("=")[1];
        String string6 = string3.split("=")[0];
        String string7 = string3.split("=")[1];
        stringBuilder.append("Socket[addr=/");
        stringBuilder.append(string8);
        stringBuilder.append(",");
        stringBuilder.append(string4);
        stringBuilder.append("=");
        stringBuilder.append(string7);
        stringBuilder.append(",");
        stringBuilder.append(string6);
        stringBuilder.append("=");
        stringBuilder.append(string5);
        stringBuilder.append("]");
        return(stringBuilder.toString());
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
