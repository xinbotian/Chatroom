package com.demo.chat5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Chatroom: client
 * Goal: to make a client that can send and receive message multiple time
 *
 */

public class GroupClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        System.out.println("-----Client-----");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your name");
        String name = br.readLine();
        // Establish connection: use socket to crate client and server interface
        Socket client = new Socket("localhost", 8888);
        // Client send the message
        new Thread(new Send(client,name)).start();
        new Thread(new Receive(client)).start();
        }
    }

