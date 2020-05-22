package com.company.network;

import java.io.IOException;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveString(TCPConnection tcpConnection,String message);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, IOException e);
}
