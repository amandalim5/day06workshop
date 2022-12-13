package myapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Random;

public class ListServer {
  public static void main(String[] args) {
    if(args.length == 0){
      System.out.println("Please input a port number\n");
      return;
    }
    String stringPort = args[0];
    int port;
    try{
      port = Integer.parseInt(stringPort);
      StartServer(port);
    } catch (Exception e){
      System.out.println("Not a valid port number");
    }

    
    
  }

  public static void StartServer(int port){
    ServerSocket server;
    System.out.println("Starting Server on port 8080");
    try{
      server = new ServerSocket(port);
      System.out.println("Listening on port " + port);

      while(true){
        Socket socket = server.accept();
        System.out.printf("New connection on port %d\n", socket.getPort());
        InputStream is = socket.getInputStream();
        BufferedInputStream bs = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bs);
        System.out.println("data input stream created");

        String stringFromClient = dis.readUTF();
        String[] arg = stringFromClient.split(" ");
        System.out.printf("info from client %s or nothing", stringFromClient);

        int numOfNumbers = Integer.parseInt(arg[0]);
        int upperLimit = Integer.parseInt(arg[1]);
        System.out.printf("Server going to get %d numbers from 0 to %d\n", numOfNumbers, upperLimit);

        Integer[] numArray = new Integer[numOfNumbers];
        for( int i=0; i<numOfNumbers; i++){
          Random rnd = new SecureRandom();
          numArray[i] = rnd.nextInt(upperLimit);
          System.out.println(numArray[i]);
        }

        // dis.close();
        // bs.close();
        // is.close();

        System.out.println("opening output stream");
        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        // buffer: writing a short byte is inefficient. it knows the best size to write, buffers it
        // and flushes it for us.
        DataOutputStream dos = new DataOutputStream(bos);
        System.out.println("output stream open for biz");
        for(int i=0; i<numOfNumbers; i++){
          Integer num = numArray[i];
          String stringnumber = num.toString();
          dos.writeUTF(stringnumber);
          System.out.println("wrote to output stream" + stringnumber);

        }
        dos.writeUTF("-1");

        dos.flush();
        dis.close();
        bs.close();
        is.close();

        dos.close();
        bos.close();
        os.close();

        socket.close();
        server.close();


      }
      
  
    } catch(IOException e){
      e.printStackTrace();
    }
  }

  
}
