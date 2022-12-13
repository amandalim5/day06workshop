package myapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ListClient {
  public static void main(String[] args) {
    if(args.length < 3){
      System.out.println("Please input an integer, ip address and port number\n");
      return;
    }
    String stringNum = args[0];
    String stringIP = args[1];
    String stringPort = args[2];
    int port;
    int num;
    try{
      port = Integer.parseInt(stringPort);
      num = Integer.parseInt(stringNum);
      startClient(port, num, stringIP);
    } catch (Exception e){
      System.out.println("Not a valid entry");
    }

  }

  public static void startClient(int port, int num, String host){
    System.out.printf("Starting client socket with port %d and num %d\n", port, num);
   
    try{
      Socket socket = new Socket(host, port);
      System.out.printf("Connected to %s:%d on %d\n", host, port, socket.getPort());
      OutputStream os = socket.getOutputStream();
      BufferedOutputStream bos = new BufferedOutputStream(os);
      DataOutputStream dos = new DataOutputStream(bos);

      System.out.println("please write a number and limit:\n");
      Scanner sc = new Scanner(System.in);
      String line = sc.nextLine();
      dos.writeUTF(line);
      dos.flush();
      System.out.println("flushed");
      


      InputStream is = socket.getInputStream();
      BufferedInputStream bs = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bs);
      System.out.println("input streams are created");
      
      String value;
      int theNumber;
      int sum = 0;
      int count = 0;
      
      while(true){
        value = dis.readUTF();
        if(value.equals("-1")){
          break;
        }
        theNumber = Integer.parseInt(value);
        System.out.println(value);
        sum += theNumber;
        count++;
      }
      System.out.println( "Average is " + (sum / count) );

      dos.close();
      bos.close();
      os.close();

      dis.close();
      bs.close();
      is.close();

      socket.close();

    } catch(Exception e){
      System.out.println(e);

    }
  }
}
