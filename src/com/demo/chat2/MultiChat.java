package com.demo.chat2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server of chatroom
 * Goal: to make a server where client can send and receive multiple message
 */

public class MultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("-----Server-----");
        // init server socket
        ServerSocket server = new ServerSocket(8888);
        // blocking IO for connection, waiting for accept
        while (true) {
            Socket client = server.accept();
            System.out.println("A client has been connected");
            // receive message
            DataInputStream dis = new DataInputStream(client.getInputStream());
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            boolean isRunning = true;
            while (isRunning) {
                String msg = dis.readUTF();
                // return message
                dos.writeUTF(msg);
                // release
                dos.flush();
            }
            dos.close();
            dis.close();
            client.close();
        }
    }
}


