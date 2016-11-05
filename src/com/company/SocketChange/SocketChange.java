package com.company.SocketChange;

import com.company.Controller.Controller;
import com.company.ParseConfigFile.ParseConfigFile;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by apava on 01.10.2016.
 */
public class SocketChange {
    Socket socket;


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void newSocket(ParseConfigFile parseConfigFile) throws IOException {
        socket=new Socket(Controller.nameHost,Controller.port);

    }

}
