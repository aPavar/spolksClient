package com.company.ParseConfigFile;

import com.company.Controller.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ParseConfigFile {
    String ip;
    int port;
    int tcpNoDelay;
    int tcpTimeOut;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }


    public void parseFile(String nameFile) throws IOException, ParseConfigFileException {
        RandomAccessFile randomAccessFile=new RandomAccessFile(nameFile,"r");
        String string=randomAccessFile.readLine();
        ip=parseString(string,"ip", 1);
        string=randomAccessFile.readLine();
        port=Integer.valueOf(parseString(string,"port", 2));
        string=randomAccessFile.readLine();
        tcpNoDelay=Integer.valueOf(parseString(string,"tcpNoDelay", 3));
        string=randomAccessFile.readLine();
        tcpTimeOut=Integer.valueOf(parseString(string,"tcpTimeOut", 4));


    }
    public String parseString(String parseString, String key,int number) throws ParseConfigFileException {
        int posKey = parseString.indexOf(key);
        boolean signal=false;
        if (posKey == -1) {
            throw new ParseConfigFileException(number, key);
        }
        int posEqual = parseString.indexOf("=");
        if (posEqual == -1) {
            throw new ParseConfigFileException(number, key);
        }
        String value="";
        char[] parseChar=parseString.toCharArray();
        for( int i=posEqual+1; i<parseString.length();i++){
            if(parseChar[i]!=' '&&parseChar[i]!='\t'&&((parseChar[i]>='0'&&parseChar[i]<='9')||
                    ((parseChar[i]>='a'&&parseChar[i]<='z')||(parseChar[i]>='A'&&parseChar[i]<='Z')||(parseChar[i]=='.')))){
                value+=parseChar[i];
                signal=true;
            }
            else{
                if(signal)
                    throw new ParseConfigFileException(number,key);
            }
        }
        return value;
    }

    public Socket newSocket() throws IOException {
        Socket socket=new Socket ();
        socket.setOOBInline(true);
        //socket.setKeepAlive(true);



       // socket.setTrafficClass(10);
        socket.connect(new InetSocketAddress(ip,port),3000);
        socket.setSoTimeout(tcpTimeOut);
        socket.setSendBufferSize(2*Controller.sizeOfPackage);
        socket.setReceiveBufferSize(2000*Controller.sizeOfPackage);

      if(tcpNoDelay==1)
           socket.setTcpNoDelay(true);
        else {
          socket.setTcpNoDelay(false);
        }

        return socket;
    }
    public ParseConfigFile(String nameFile) throws IOException, ParseConfigFileException {
        parseFile(nameFile);
    }
}
