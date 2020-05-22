package com.company.client;

import com.company.file.ReadFileImpl;
import com.company.jdbcConnection.Connector;
import com.company.network.TCPConnection;
import com.company.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class ClientWindow extends JFrame implements ActionListener,TCPConnectionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private Connector jdbcConnector;
    private TCPConnection connection;
    private User user;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
                new ClientWindow();
            }
        });


    }
    private final JTextArea textArea = new JTextArea();
    private final JTextField fName = new JTextField("Sabina");
    private final JTextField fInput = new JTextField();
    private ClientWindow(){
        ReadFileImpl readFile = new ReadFileImpl();
        HashMap<String, String> properties = readFile.getProperties();
        jdbcConnector = new Connector();
        user = new User();
        textArea.setText(jdbcConnector.selectFromTable());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(textArea,BorderLayout.CENTER);
        add(fInput, BorderLayout.SOUTH);
        fInput.addActionListener(this);
        add(fName, BorderLayout.NORTH);
        try{
            connection = new TCPConnection(this,properties.get("ip"),Integer.parseInt(properties.get("port")));
        }catch (Exception e){

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        user.setMessage(fInput.getText());
        user.setName(fName.getText());
        if(user.getMessage().equals("")){
            return;
        }
        fInput.setText(null);
        jdbcConnector.insertIntoTable(user);
        connection.sendString(user.toString());
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage("Client connected: " + tcpConnection);
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String message) {
        printMessage(message);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, IOException e) {
        printMessage("Connection exception " + e);
    }
    private synchronized void printMessage(String message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(message + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }
}
