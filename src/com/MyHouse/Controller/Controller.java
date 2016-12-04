package com.MyHouse.Controller;

import com.MyHouse.CharacterTransferData.CharacterTransferData;
import com.MyHouse.Command.Command;
import com.MyHouse.File.File;
import com.MyHouse.Header.Header;
import com.MyHouse.NameCommand.NameCommand;
import com.MyHouse.Parser.Parser;
import com.MyHouse.StructureOfReturnValue.StructureOfReturnValue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;


public class Controller {
    static String ip = "192.168.57.2";
    static int port = 6000;
    private static ArrayList<byte[]> listOfPackets;
    private static int sizeOfPackage = 1024;
    public static void move() throws IOException, InterruptedException {

            SocketChannel channel = SocketChannel.open();

            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ip, port));
            channel.socket().setSendBufferSize(10000*sizeOfPackage);

            while (!channel.finishConnect()) {
                 System.out.println("still connecting");
                Thread.sleep(909);
            }
        Command command ;
            while (true) {




                System.out.println("Enter command ");
              String readCommand = readCommand();
              Parser parser = new Parser();
              command = parser.getCommandWithParameters(readCommand);
               callFunction(command,channel);



            }

    }

    public static void callFunction(Command command, SocketChannel channel) throws IOException {
        switch(command.getNameCommand()){
            case "loadFile" : loadFile(command.getListParameters().get(0), channel,0);
                break;
        }
    }

    public static void loadFile(String nameOfFile, SocketChannel channel, int number) throws IOException {
        System.out.println("loadFile");

        File file=new File(nameOfFile);
        int length = Controller.sizeOfPackage - Header.sizeOfHeader;
        byte[] arrayToServer=new byte[Controller.sizeOfPackage-Header.sizeOfHeader];

        int jo = number;
        long position = number*(Controller.sizeOfPackage-Header.sizeOfHeader);

        createListPackets(nameOfFile.getBytes(),Controller.sizeOfPackage,CharacterTransferData.setConnection,NameCommand.loadFile,
                countOfPackets(file.getSizeOfFile()),number);


        channel.write(ByteBuffer.wrap(listOfPackets.get(0)));
        listOfPackets.clear();
        StructureOfReturnValue structureOfReturnValue=new StructureOfReturnValue (false,0);
        int count=countOfPackets(file.getSizeOfFile());
        System.out.println(count);
        while(!structureOfReturnValue.isSignalOfEndFile()) {
            structureOfReturnValue= file.readInfoFromFile(nameOfFile, length, position,arrayToServer);

            createListPackets(arrayToServer, Controller.sizeOfPackage,CharacterTransferData.transferData,
                    NameCommand.loadFile,count,jo, structureOfReturnValue.getLengthOfReadCharacter());

            for (byte[] listOfPacket : listOfPackets) {


                channel.write(ByteBuffer.wrap(listOfPacket));

            }


          //  ByteBuffer byteBuffer = ByteBuffer.wrap(listOfPackets.get(0));
         //   long l = byteBuffer.getLong(0);
          //  System.out.println(l==Header.protocolID);
           // handler.setNumberOfPackage(jo);
            jo++;
            listOfPackets.clear();
            position+=length;
        }
        System.out.println("End function load");




    }

    public static String readCommand(){
        Scanner scanner=new Scanner(System.in);
        String command=null;
        if(scanner.hasNextLine()) {
            command = scanner.nextLine();
        }

        return command;
    }

    private static void createListPackets(byte[] data, int sizeOfPacket, CharacterTransferData characterTransferData, NameCommand nameCommand,
                                  int countOfPacket, int numberOfPacket ) throws IOException {


        listOfPackets=new ArrayList<>();
        Header header=new Header();
        int i=0;
        header.setInformation(characterTransferData, nameCommand,i,sizeOfPacket,0);

        while(true) {
            byte[] newData=new byte[sizeOfPacket];
            header.setNumberOfPacket(i);

            if((data.length-i*(sizeOfPacket- header.getSizeOfHeader()))>sizeOfPacket-header.getSizeOfHeader()) {
                //  System.out.println(header.getSizeOfHeader());
                header.setSizeOfMessage(sizeOfPacket-header.getSizeOfHeader());
                header.setCountOfPacket(countOfPacket);
                header.setNumberOfPacket(numberOfPacket+i);
                System.arraycopy(Header.headerToArrayOfBytes(header), 0, newData, 0, header.getSizeOfHeader());
                System.arraycopy(data, i * (sizeOfPacket-header.getSizeOfHeader()), newData,header.getSizeOfHeader() ,
                        sizeOfPacket-header.getSizeOfHeader());
                header.setSizeOfMessage(sizeOfPacket-header.getSizeOfHeader());
                listOfPackets.add(newData);
                numberOfPacket++;

            }else  {
                header.setSizeOfMessage(data.length-i*(sizeOfPacket-header.getSizeOfHeader()));
                header.setNumberOfPacket(numberOfPacket+i);
                header.setCountOfPacket(countOfPacket);
                System.arraycopy(Header.headerToArrayOfBytes(header),0,newData,0,header.getSizeOfHeader());

                System.arraycopy(data, i * (sizeOfPacket-header.getSizeOfHeader()),newData,header.getSizeOfHeader() ,
                        data.length-i*(sizeOfPacket-header.getSizeOfHeader()) );
                header.setSizeOfMessage(data.length-i*(sizeOfPacket-header.getSizeOfHeader()));
                listOfPackets.add(newData);
                break;
            }
            i++;
        }
    }

    private static void createListPackets(byte[] data,int sizeOfPacket, CharacterTransferData characterTransferData,NameCommand nameCommand,
                                  int countOfPacket,int numberOfPacket,int lengthS ) throws IOException {


        listOfPackets=new ArrayList<>();
        Header header=new Header();
        int i=0;
        header.setInformation(characterTransferData, nameCommand,i,sizeOfPacket,0);

        while(true) {
            byte[] newData=new byte[sizeOfPacket];
            header.setNumberOfPacket(i);

            if((data.length-i*(sizeOfPacket- header.getSizeOfHeader()))>sizeOfPacket-header.getSizeOfHeader()) {
                //  System.out.println(header.getSizeOfHeader());
                header.setSizeOfMessage(lengthS);
                header.setCountOfPacket(countOfPacket);
                header.setNumberOfPacket(numberOfPacket+i);
                System.arraycopy(Header.headerToArrayOfBytes(header), 0, newData, 0, header.getSizeOfHeader());
                System.arraycopy(data, i * (sizeOfPacket-header.getSizeOfHeader()), newData,header.getSizeOfHeader() ,
                        sizeOfPacket-header.getSizeOfHeader());
                header.setSizeOfMessage(sizeOfPacket-header.getSizeOfHeader());
                listOfPackets.add(newData);
                numberOfPacket++;

            }else  {
                header.setSizeOfMessage(lengthS);
                header.setNumberOfPacket(numberOfPacket+i);
                header.setCountOfPacket(countOfPacket);
                System.arraycopy(Header.headerToArrayOfBytes(header),0,newData,0,header.getSizeOfHeader());

                System.arraycopy(data, i * (sizeOfPacket-header.getSizeOfHeader()),newData,header.getSizeOfHeader() ,
                        data.length-i*(sizeOfPacket-header.getSizeOfHeader()) );
                header.setSizeOfMessage(data.length-i*(sizeOfPacket-header.getSizeOfHeader()));
                listOfPackets.add(newData);
                break;
            }
            i++;
        }
    }

    static int countOfPackets(long length){
        if(length%(Controller.sizeOfPackage-Header.sizeOfHeader)==0)
            return (int)(length/(long)(Controller.sizeOfPackage-Header.sizeOfHeader));
        else return ((int)(length/(long)(Controller.sizeOfPackage-Header.sizeOfHeader))+1);
    }
}
