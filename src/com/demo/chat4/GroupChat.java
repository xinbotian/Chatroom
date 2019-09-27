package com.demo.chat4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Server of chatroom
 * Goal: to make a server where multi clients can send and receive multiple message using multi threading
 */

public class GroupChat {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();
    public static void main(String[] args) throws IOException {
        System.out.println("-----Server-----");
        // init server socket
        ServerSocket server = new ServerSocket(8888);
        // blocking IO for connection, waiting for accept
        while (true) {
            Socket client = server.accept();
            System.out.println("A client has connected");
            Channel c = new Channel(client);
            all.add(c);//manage all members
            new Thread(c).start();
        }
    }
    // one instance of channel represent a client
    static class Channel implements  Runnable{
        private DataInputStream dis;
        private DataOutputStream dos;
        private  Socket client;
        private boolean isRunning;
        private String name;

        public Channel(Socket client){
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                //get name
                this.name = receive();
                //send Welcome to client
                this.send("Welcome to chatroom");
                sendOthers(this.name + " has joined the chatrom ", true);
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
            }catch (IOException e){
                System.out.println("-----3-----");
                release();
            }
        }
        //get self message, send to others
        private void sendOthers(String msg, boolean isSys) {
            for(Channel other : all) {
                if (other == this) {//self
                    continue;
                }
                if(!isSys){
                    other.send(this.name + " to every one: " + msg);
                }else{
                    other.send(msg);//system message
                }
            }
        }
        private  void release(){
            this.isRunning = false;
            ChatUtils.close(dis,dos,client);
            //exit
            all.remove(this);
            sendOthers(this.name + " has exited the chatroom ", true);
        }
        @Override
        public void run(){
            while(isRunning){
                String msg = receive();
                if(!msg.equals("")){
                    sendOthers(msg, false);
                }
            }
        }
    }
}



