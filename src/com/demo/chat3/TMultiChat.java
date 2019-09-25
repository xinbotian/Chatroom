package com.demo.chat3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server of chatroom
 * Goal: to make a server where multi clients can send and receive multiple message using multi threading
 */

public class TMultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("-----Server-----");
        // init server socket
        ServerSocket server = new ServerSocket(8888);
        // blocking IO for connection, waiting for accept
        while (true) {
            Socket client = server.accept();
            System.out.println("A client has connected");
            new Thread(new Channel(client)).start();
        }
    }
    // one instance of channel represent a client
    static class Channel implements  Runnable{
        private DataInputStream dis;
        private DataOutputStream dos;
        private  Socket client;
        private boolean isRunning;

        public Channel(Socket client){
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
            }catch(IOException e){
                System.out.println("-----1-----");
                release();
            }
        }
        //receive message
        private String receive(){
            String msg ="";
            try {
                msg = dis.readUTF();
            }catch(IOException e){
                System.out.println("-----2-----");
                release();
            }
            return msg;
        }
        //send message
        private void send(String msg){
            try{
                dos.writeUTF(msg);
                dos.flush();
                }catch(IOException e){
                System.out.println("-----3-----");
                release();
            }
        }
        private  void release(){
            this.isRunning = false;
            ChatUtils.close(dis,dos,client);
        }
        @Override
        public void run(){
            while(isRunning){
                String msg = receive();
                if(!msg.equals("")){
                    send(msg);
                }
            }
        }
    }
}


