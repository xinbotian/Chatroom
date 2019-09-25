package com.demo.chat3;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Chatroom: client
 * Goal: to make a client that can send and receive message multiple time
 *
 */

public class TMultiClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        System.out.println("-----Client-----");
        // Establish connection: use socket to crate client and server interface
        Socket client = new Socket("Localhost", 8888);
        // Client send the message
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
        }
    }

