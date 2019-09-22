package com.vedfi;

/*
    Author: Ahmet YÜREKLİ / VedFI
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ServerConnection extends Thread {

    private Socket client_socket;
    public boolean notify;
    public boolean stop;
    public boolean client_disconnected;
    public Notification notification;


    public ServerConnection(Socket client_socket) {
        this.client_socket = client_socket;
        //System.out.println("connected to client:"+this.client_socket.getPort());
    }

    public Socket getSocket() {
        return this.client_socket;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public boolean getNotify() {
        return this.notify;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean getStop() {
        return this.stop;
    }

    public String notificationToJSON() {
        notification = ServerConsole.notification;
        return Main.gson.toJson(notification);
    }

    public void run() {
        try {
            InputStream inputToServer = this.client_socket.getInputStream();
            OutputStream outputFromServer = this.client_socket.getOutputStream();
            //Scanner scanner = new Scanner(inputToServer, "UTF-8");
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
            //Scanner svKB = new Scanner(System.in);
            boolean done = false;
            //String line="",line2="";
            while(!done) {
                //System.out.print(this.client_socket.getPort()+">");
                //line = svKB.nextLine();
                /**
                 System.out.println("XXX: "+notify);
                 if(line.trim().equalsIgnoreCase("stop")) {
                 serverPrintOut.println("CONNECTION TERMINATED.");
                 this.client_socket.close();
                 done = true;
                 }*/
                try {
                    sleep(1000);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                if(getNotify()) {
                    serverPrintOut.println(notificationToJSON());
                    setNotify(false);
                }
                if(getStop()) {
                    serverPrintOut.println("Connection Terminating...");
                    this.client_socket.close();
                    done = true;
                }
                try {
                    client_socket.setSoTimeout(100);
                    inputToServer.read();
                }
                catch(SocketException e) {
                    System.err.println("Client Disconnected!");
                    Main.console.cons.remove(this);
                    Main.clientList.remove(this);
                    Main.console.update(Main.clientList);
                    done=true;

                }
                catch (SocketTimeoutException e){
                    //do nothing
                }

            }
            // scanner.close();
            //svKB.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
