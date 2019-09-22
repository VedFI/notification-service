package com.vedfi;

/*
    Author: Ahmet YÜREKLİ / VedFI
 */

import com.google.gson.Gson;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Main {

    static ArrayList<ServerConnection> clientList = new ArrayList<ServerConnection>();
    public static Gson gson = new Gson();
    public static ServerConsole console;

    public static void main(String[] args) {
        startServer(clientList);
    }

    public static void startServer(ArrayList<ServerConnection> array) {
        try(ServerSocket server = new ServerSocket(9899)){
            System.err.println("Server Start!");
            console = new ServerConsole(array);
            ServerConnection temp;
            console.start();
            while(true) {
                //System.out.println("Waiting for client..");
                Socket connection = server.accept();
                temp = new ServerConnection(connection);
                temp.start();
                array.add(temp);
                console.update(array);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        System.err.println("Server Stop!");
        System.exit(0);
    }

    public static void clearConnections() {
        clientList.clear();
    }
}

