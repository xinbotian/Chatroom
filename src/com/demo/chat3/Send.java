package com.demo.chat3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Encapsulated with multithreading: sending
 * 1. sending message
 * 2. receive message from console
 * 3. release resources
 * 4. rebuild run function
 */

public class Send implements Runnable {
    private BufferedReader console;
    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning;
    public Send(Socket client){
        this.client = client;
        console = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
        }catch (IOException e){
            System.out.println("===1===");;
        }
    }
    @Override
    public void run(){
        while(isRunning){
            String msg = getStrFromConsole();
            if(!msg.equals("")){
                send(msg);
            }
        }
    }
    //send message
    private  void send(String msg){
        try{
            dos.writeUTF(msg);
            dos.flush();
        }catch (IOException e){
            System.out.println("===3===");
            release();
        }
    }
    // get message from console
    private String getStrFromConsole(){
        try{
            return console.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }
    private void release(){
        this.isRunning = false;
        ChatUtils.close(dos,client);
    }
}
