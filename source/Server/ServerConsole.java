package com.vedfi;

/*
    Author: Ahmet YÜREKLİ / VedFI
 */

import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerConsole extends Thread {

    ArrayList<ServerConnection> cons;
    Scanner input = new Scanner(System.in);
    String title,content;
    String command ="";
    boolean running;
    static Notification notification;

    public ServerConsole(ArrayList<ServerConnection> cons) {
        this.cons = cons;
    }

    public void run() {
        System.err.println("Console Ready!");
        running=true;
        while(running) {
            try {
                sleep(100);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            System.out.print("console> ");
            try {
                command = input.nextLine();
                checkCommand(command);
            }
            catch(Exception e) {
                e.printStackTrace();
                exit();
            }
        }
    }

    public void deleteElement(ServerConnection sc){
        cons.remove(sc);
    }


    public void checkCommand(String command) {
        //if(cons.size()>0) {
            if(command.trim().equalsIgnoreCase("notify")) {
                notifyClients();
            }
            else if(command.trim().equalsIgnoreCase("exit")) {
                exit();
            }
            else if(command.trim().equalsIgnoreCase("list")){
                listConnections();
            }
            else if(command.trim().length()==0) {
                System.err.println("no command.");
            }
            else {
                System.err.println("command '"+ command +"' not found.");
            }
        /**}
        else if(command.trim().equalsIgnoreCase("exit")) {
            exit();
        }
        else {
            System.err.println("There is no client connected to server.");
        }
*/
    }

    public void terminateConnections() {
        System.err.println("Client connections terminating...");
        for (ServerConnection serverConnection : cons) {
            serverConnection.setStop(true);
        }
        cons.clear();
        Main.clearConnections();
    }

    public void listConnections() {
        System.err.println("\n--CLIENT LIST--");
        if(!cons.isEmpty()){
            for (ServerConnection serverConnection : cons) {
                System.err.println(cons.indexOf(serverConnection)+">"+serverConnection.getSocket().toString());
            }
        }
        else{
            System.err.println("There is no client.");
        }
    }

    public void notifyClients() {
        System.out.print("Notification title> ");
        title = input.nextLine();
        System.out.print("Notification content> ");
        content = input.nextLine();
        notification = new Notification(title,content);
        System.err.println("Sending notification...");
        for (ServerConnection serverConnection : cons) {
            serverConnection.setNotify(true);
        }
    }

    public void exit() {
        if(!cons.isEmpty()) {
            terminateConnections();
        }
        System.err.println("Console Exit!");
        Main.stopServer();
        this.running=false;
    }

    public void update(ArrayList<ServerConnection> cons) {
        this.cons = cons;
        printNumOfClients();
    }

    public void printNumOfClients() {
        System.err.println("Number of clients changed: "+cons.size());
        listConnections();
    }

}

