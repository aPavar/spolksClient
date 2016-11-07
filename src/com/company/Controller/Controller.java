package com.company.Controller;

import com.company.Client.Client;
import com.company.Command.Command;
import com.company.CountOfParameterException.CountOfParameterException;
import com.company.Handler.Handler;
import com.company.Header.Header;
import com.company.ParseConfigFile.ParseConfigFile;
import com.company.ParseConfigFile.ParseConfigFileException;
import com.company.Parser.NameCommandException;
import com.company.Parser.Parser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;


public class  Controller {
    public static int sizeOfPackage=1024;
    public static int sizeOfPackageUdp = 16384;
    public static int sizeOfSendBuffer;
    public static int sizeOfReceiveBuffer;
    public static String readCommand(){
        Scanner scanner=new Scanner(System.in);
        String command=null;
        if(scanner.hasNextLine()) {
           command = scanner.nextLine();
        }

        return command;
    }
    public static String nameHost="localhost";
    public static int port=6000;
    static  Boolean reset=true;
    public static Client initilizeServer(){
        Client client =new Client();
        return client;
    }
    public static boolean isLoad=false;
    public static boolean isDownLoad=false;
 
    public  static void callFunction(Command command,Client client,Socket socket,DataOutputStream toServer,BufferedReader fromServer,
                                     Parser parser,Handler handler) throws IOException, ClassNotFoundException, InterruptedException {
        switch(command.getNameCommand()) {
            case "exit": socket.close(); System.exit(0);
                break;
            case "loadFile": client.loadFile(command.getListParameters().get(0),socket, toServer, fromServer,0,handler);
                break;
            case "time":
                System.out.println(client.getTime(toServer, fromServer,socket));
                break;
            case "echo":
                System.out.println(client.echo(toServer,fromServer,parser.getAllStringParameter(),socket));
                break;
            case "shutdown": client.shutDown(toServer);
                break;
            case "download": client.downloadFile(command.getListParameters().get(0),socket,toServer,fromServer,0,handler);
                break;
            case "update": reset=true;
                break;

            case "udpLoadFile": client.loadFileUdp(command.getListParameters().get(0),toServer,0,handler);
                break;
            case "udpDownloadFile":client.downloadFileUdp();
                break;

        }
    }

    public static void move() throws IOException, CountOfParameterException, ClassNotFoundException,
            InterruptedException, ParseConfigFileException {
        String nameConfigFile="SpolksConfigure.txt";

        String readingString=null;
        Client client =new Client();
        Command command=null;
        Socket socket=null;
        DataOutputStream toServer=null;
        BufferedReader fromServer=null;
        ParseConfigFile parseConfigFile= new ParseConfigFile(nameConfigFile);

        Handler handler=new Handler();
            do{
                //////exception handling of connection////////



                if(reset)
                {
                    parseConfigFile.parseFile(nameConfigFile);
                    nameHost=parseConfigFile.getIp();
                    port=parseConfigFile.getPort();
                    if(socket!=null)
                    socket.close();
                    socket=null;
                    reset=false;
                }
                Header header=new Header();
                if(socket!=null) {
                    toServer = new DataOutputStream(socket.getOutputStream());
                    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
                if (socket == null) {
               while(true) {

                       try {
                           socket = parseConfigFile.newSocket();
                           toServer=new DataOutputStream(socket.getOutputStream());
                           fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                           //socket.connect(new InetSocketAddress("localhost",6000));


                           break;
                       } catch (SocketException|SocketTimeoutException e) {
                           if(handler.isLoad())
                           {
                               System.out.println("Connection is closed, but your file is load. If you want to continue load, enter something."+
                               " Else enter \"exit\" or \"Exit\"");
                               readingString = readCommand();
                               if (readingString.equals("Exit") || readingString.equals("exit")) {
                                   System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                           "If you want to continue-enter something and press enter.");
                                   if (readingString.equals("Exit") || readingString.equals("exit")) {
                                       return;
                                   }
                               }else{
                                   System.out.println("Controller, 129 ");
                                   isLoad=true;
                                   break;
                               }

                           }else
                           {
                               if(handler.isDownLoad()) {
                                   System.out.println("Connection is closed, but your file is load. If you want to continue load, enter something." +
                                           " Else enter \"exit\" or \"Exit\"");
                                   readingString = readCommand();
                                   if (readingString.equals("Exit") || readingString.equals("exit")) {
                                       System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                               "If you want to continue-enter something and press enter.");
                                       if (readingString.equals("Exit") || readingString.equals("exit")) {
                                           return;
                                       }
                                   } else {
                                       System.out.println("Controller, 147 ");
                                       isDownLoad=true;
                                       break;
                                   }

                               }else {
                                   System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                           "If you want to continue-enter something and press enter.");

                                   readingString = readCommand();

                                   //   Command command= parser.getCommandWithParameters(readingString);
                                   if (readingString.equals("Exit") || readingString.equals("exit")) {
                                       return;
                                   }
                               }
                           }


                       }

                   }
               }
                boolean flagNameCommandValid=true;
                try {
                    if(!isLoad&&!isDownLoad) {
                        boolean nameCommandIsValid=false;
                        Parser parser = new Parser();
                        while(!nameCommandIsValid){
                             System.out.println("Enter command. If you need help enter help.");
                                readingString = readCommand();



                            try {
                              command = parser.getCommandWithParameters(readingString);
                              nameCommandIsValid=true;
                             }catch (NameCommandException eeee) {
                            //  e.printStackTrace();
                                System.out.println("NameCommandException");
                             }
                        }
                        if (command.getNameCommand().equals("echo"))
                            parser.doAllStringParameterForEcho(readingString);
                        callFunction(command, client, socket, toServer, fromServer, parser, handler);
                       // if(socket!=null)
                         //   socket.close();
                        //socket=parseConfigFile.newSocket();

                    }else{
                        if(isLoad) {
                            client.loadFile(handler.getNameOfFile(), socket, toServer, fromServer, handler.getNumberOfPackage(), handler);
                          //  socket=new Socket(nameHost,port);
                            isLoad=false;
                        }
                        else {
                            client.downloadFile(handler.getNameOfFile(), socket, toServer, fromServer, handler.getNumberOfPackage(), handler);
                         //   socket=new Socket(nameHost,port);
                            isLoad=false;
                        }
                    }

                }

                catch(SocketException|SocketTimeoutException e){

                    while(true) {
                        try {
                            System.out.println("Connection with server was damaged");








                            if(handler.isLoad())
                            {
                                System.out.println("Connection is closed, but your file is load. If you want to continue load, enter something."+
                                        " Else enter \"exit\" or \"Exit\"");
                                readingString = readCommand();
                                if (readingString.equals("Exit") || readingString.equals("exit")) {
                                    System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                            "If you want to continue-enter something and press enter.");
                                    if (readingString.equals("Exit") || readingString.equals("exit")) {
                                        return;
                                    }
                                }else{
                                    socket = parseConfigFile.newSocket();
                                    toServer=new DataOutputStream(socket.getOutputStream());
                                    fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    client.loadFile(handler.getNameOfFile(),socket,toServer,fromServer,handler.getNumberOfPackage(),handler);
                                    break;
                                }


                            }else
                            {
                                if(handler.isDownLoad()) {
                                    System.out.println("Connection is closed, but your file is load. If you want to continue load, enter something." +
                                            " Else enter \"exit\" or \"Exit\"");
                                    readingString = readCommand();
                                    if (readingString.equals("Exit") || readingString.equals("exit")) {
                                        System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                                "If you want to continue-enter something and press enter.");
                                        if (readingString.equals("Exit") || readingString.equals("exit")) {
                                            return;
                                        }
                                    } else {
                                        socket = parseConfigFile.newSocket();
                                        toServer=new DataOutputStream(socket.getOutputStream());
                                        fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        client.downloadFile(handler.getNameOfFile(), socket, toServer, fromServer, handler.getNumberOfPackage(), handler);
                                        break;
                                    }

                                }else {
                                    System.out.println("Connection is closed. If you want exit-enter \"exit\" or \"Exit\" and press enter. " +
                                            "If you want to continue-enter something and press enter.");

                                    readingString = readCommand();

                                    //   Command command= parser.getCommandWithParameters(readingString);
                                    if (readingString.equals("Exit") || readingString.equals("exit")) {
                                        return;
                                    }
                                }
                            }

                            if(!isLoad&&!isDownLoad){
                                socket = parseConfigFile.newSocket();
                                toServer=new DataOutputStream(socket.getOutputStream());
                                fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                break;
                            }

                            break;

                        } catch (SocketException| SocketTimeoutException ee ) {
                            System.out.println("SocketEx| SocketTimeoutEx");

                        }



                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while(!(readingString.equals("Exit")||readingString.equals("exit")));


    }
}
