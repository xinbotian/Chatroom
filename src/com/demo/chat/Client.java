package com.demo.chat;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Chatroom: client
 * Goal: to make a client that can send and receive message
 *
 */

public class Client {
    public static void main(String[]args) throws UnknownHostException, IOException{
        System.out.println("-----Client-----");
        // Establish connection: use socket to crate client and server interface
        Socket client = new Socket("Localhost",8888);
        // Client send the message
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String msg = console.readLine();
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();
        // Receive message
        DataInputStream dis = new DataInputStream(client.getInputStream());
        msg = dis.readUTF();
        System.out.println(msg);
        dos.close();
        dis.close();
        client.close();
    }
}
