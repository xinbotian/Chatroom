package com.demo.chat2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Chatroom: client
 * Goal: to make a client that can send and receive message multiple time
 *
 */

public class MultiClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        System.out.println("-----Client-----");
        // Establish connection: use socket to crate client and server interface
        Socket client = new Socket("Localhost", 8888);
        // Client send the message
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        boolean isRunning = true;
        while (isRunning) {
            String msg = console.readLine();
            dos.writeUTF(msg);
            dos.flush();
            // Receive message
            msg = dis.readUTF();
            System.out.println(msg);
        }
            dos.close();
            dis.close();
            client.close();
        }
    }

