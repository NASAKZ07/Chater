package com.company.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection extends Thread {
    private final Socket socket;
    private final BufferedReader in; // read
    private final BufferedWriter out;  // write
    private final TCPConnectionListener connectionListener;



    public TCPConnection(TCPConnectionListener connectionListener, String ipAddress, int port) throws IOException {
        this(connectionListener, new Socket(ipAddress,port));
    }

    public TCPConnection(TCPConnectionListener connectionListener,Socket socket) throws IOException {
        this.socket = socket;
        this.connectionListener = connectionListener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

        start();
        }

    @Override
    public void run() {
            try {
                connectionListener.onConnectionReady(TCPConnection.this);
                while (!isInterrupted()) {
                    connectionListener.onReceiveString(TCPConnection.this, in.readLine());
                }
            } catch (IOException e) {
                connectionListener.onException(TCPConnection.this,e);
            } finally {
                connectionListener.onDisconnect(TCPConnection.this);
            }
    }

    public synchronized void sendString(String msg){
            try {
                out.write(msg + "\r\n");
                out.flush();//sent
            } catch (IOException e) {
                connectionListener.onException(TCPConnection.this, e);
                disconnect();
            }
        }
        public synchronized void disconnect(){
            interrupt();
            try {
                socket.close();
            }catch (IOException e){
                connectionListener.onException(TCPConnection.this, e);
            }
        }
    @Override
    public String toString() {
        return "connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }

}
