package com.vedfi;
/*
    Author: Ahmet YÜREKLİ / VedFI
 */
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static Gson gson = new Gson();
    public static Notification notification;

    public static void main(String[] args) {
        connectToServer();
    }

    public static void connectToServer() {

        try(Socket clientSocket = new Socket("localhost", 9899)){
            InputStream inputToClient = clientSocket.getInputStream();
            Scanner sc = new Scanner(inputToClient);
            String line="";
            System.err.println("--- CLIENT START ---");
            while(clientSocket.isConnected()) {
                try{
                    line = sc.nextLine();
                }
                catch (Exception e){
                    System.err.println("--Server Terminated Connection--");
                    System.err.println("--- CLIENT STOP ---");
                    System.exit(0);
                }
                System.err.println("--Notification Received From The Server--");
                notification = gson.fromJson(line,Notification.class);
                System.out.println("Title: "+notification.title);
                System.out.println("Content: "+notification.content);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

}