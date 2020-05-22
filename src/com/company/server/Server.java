package com.company.server;

import com.company.network.TCPConnection;
import com.company.network.TCPConnectionListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements TCPConnectionListener {
    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private List<TCPConnection> connections = new ArrayList<>();
    public static void main(String[] args) {
        new Server();
    }
    private Server(){
        System.out.println("Server running....");
        try (ServerSocket socket = new ServerSocket(9291)){
            while (true){
                try{
                    new TCPConnection(this, socket.accept());
                }catch (IOException e){
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAll("Client connected: " + tcpConnection);
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String message) {
        sendToAll(message);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAll("Client disconnect: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exception: " + e);
    }
    private synchronized void sendToAll(String message){
        System.out.println(message);
        for(TCPConnection connection: connections){
            connection.sendString(message);
        }
    }
}
