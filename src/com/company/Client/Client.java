package com.company.Client;

import com.company.CharacterTransferData.CharacterTransferData;
import com.company.Controller.Controller;
import com.company.File.File;
import com.company.Handler.Handler;
import com.company.Header.Header;
import com.company.HeaderUdp.HeaderUdp;
import com.company.HelperFindTimeout.HelperFindTimeout;
import com.company.KitOfHeaders.KitOfHeaders;
import com.company.NameCommand.NameCommand;
import com.company.ParseConfigFile.ParseConfigFile;
import com.company.Parser.Parser;
import com.company.Separator.Separator;
import com.company.SpecialData.SpecialData;
import com.company.StructureOfReturnValue.StructureOfReturnValue;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apava on 02.09.2016.
 */
public class Client {




    int numberOfPacketException;

    private ArrayList<byte[]> listOfPackets;

    public ArrayList<byte[]> getListOfPackets() {
        return listOfPackets;
    }

    public void createListPackets(byte[] data,int sizeOfPacket, CharacterTransferData characterTransferData,NameCommand nameCommand,
                                   int countOfPacket,int numberOfPacket ) throws IOException {


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



    public void createListPackets(byte[] data,int sizeOfPacket, CharacterTransferData characterTransferData,NameCommand nameCommand,
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



    public void createListPackets(byte[] data,int sizeOfPacket, CharacterTransferData characterTransferData,NameCommand nameCommand)
            throws IOException {


        listOfPackets=new ArrayList<byte[]>();
        Header header=new Header();
        int i=0;
        header.setInformation(characterTransferData,nameCommand,i,sizeOfPacket,0);
        while(true) {
            byte[] newData=new byte[sizeOfPacket];
            header.setNumberOfPacket(i);

            if((data.length-i*(sizeOfPacket- header.getSizeOfHeader()))>sizeOfPacket-header.getSizeOfHeader()) {
                //  System.out.println(header.getSizeOfHeader());
                header.setSizeOfMessage(sizeOfPacket-header.getSizeOfHeader());
                System.arraycopy( Header.headerToArrayOfBytes(header), 0,newData, 0, header.getSizeOfHeader());
                System.arraycopy(data, i * (sizeOfPacket-header.getSizeOfHeader()), newData,header.getSizeOfHeader() ,
                        sizeOfPacket-header.getSizeOfHeader());
                header.setSizeOfMessage(sizeOfPacket-header.getSizeOfHeader());
                listOfPackets.add(newData);

            }else  {
                header.setSizeOfMessage(data.length-i*(sizeOfPacket-header.getSizeOfHeader()));
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


    int countOfPackets(long length){
        if(length%(Controller.sizeOfPackage-Header.sizeOfHeader)==0)
            return (int)(length/(long)(Controller.sizeOfPackage-Header.sizeOfHeader));
        else return ((int)(length/(long)(Controller.sizeOfPackage-Header.sizeOfHeader))+1);
    }

    int countOfPacketsUdp(long length){
        if(length%(Controller.sizeOfPackage-HeaderUdp.getSizeOfHeaderUdp())==0)
            return (int)(length/(long )(Controller.sizeOfPackage-HeaderUdp.getSizeOfHeaderUdp()));
        else return((int)(length/(long)(Controller.sizeOfPackage-HeaderUdp.getSizeOfHeaderUdp()))+1);
    }

    public void loadFile(String nameOfFile,Socket socket,DataOutputStream toServer, BufferedReader fromServer, int number
    , Handler handler) throws IOException, InterruptedException, ClassNotFoundException {
        long time= System.currentTimeMillis();
        handler.setIsLoad(true);
        handler.setNumberOfPackage(number);
        handler.setNameOfFile(nameOfFile);
        File file=new File(nameOfFile);
        int length=Controller.sizeOfPackage-Header.sizeOfHeader;
        byte[] arrayToServer=new byte[Controller.sizeOfPackage-Header.sizeOfHeader];
        byte[] arrayFromServer=new byte[Controller.sizeOfPackage];
        Separator separator=new Separator();

        if(number-550<0)
             number=0;
         else{
            number=number-550;
         }
        int jo=number;
        long position=number*(Controller.sizeOfPackage-Header.sizeOfHeader);

        createListPackets(nameOfFile.getBytes(),Controller.sizeOfPackage,CharacterTransferData.setConnection,NameCommand.loadFile,
                countOfPackets(file.getSizeOfFile()),number);


        sendPackage(listOfPackets.get(0),toServer);
     // int aaaaaa=  socket.getInputStream().read(arrayFromServer);
        listOfPackets.clear();

        StructureOfReturnValue structureOfReturnValue=new StructureOfReturnValue (false,0);
        int count=countOfPackets(file.getSizeOfFile());

        while(!structureOfReturnValue.isSignalOfEndFile()) {
            structureOfReturnValue= file.readInfoFromFile(nameOfFile, length, position,arrayToServer);
            createListPackets(arrayToServer, Controller.sizeOfPackage,CharacterTransferData.transferData,NameCommand.loadFile,count,jo,
                    structureOfReturnValue.getLengthOfReadCharacter());

            for (byte[] listOfPacket : listOfPackets) {

                sendPackage(listOfPacket, toServer);
             //   aaaaaa=  socket.getInputStream().read(arrayFromServer);
                separator.parce(listOfPacket);
                System.out.println("numberOfPacket send "+ separator.getHeader().getNumberOfPacket());

            }
            handler.setNumberOfPackage(jo);
            jo++;
            listOfPackets.clear();
            position+=length;
        }
        handler.setIsLoad(false);
        System.out.println("End function load ");
        long time2 =System.currentTimeMillis();
        System.out.println(time2-time);
    }

    public void readPackForFile(Socket s,byte[] array) throws IOException {

        DataInputStream dataInputStream=new DataInputStream(s.getInputStream());
        int a=dataInputStream.read(array);
       // String sss=new String(array,Charset.defaultCharset());
       // System.out.println(sss);
       // System.out.println(array.length);


    }

    public void downloadFile(String nameOfFile,Socket socket,DataOutputStream toServer, BufferedReader fromServer, int number,
                             Handler handler) throws IOException, ClassNotFoundException {

        int pokRead=0;
       // int mul = 50;
      //  int j=0;
       // byte[] cache=new byte [ mul*Controller.sizeOfPackage];
      //  int sm = 0;
      //  int size = 0;
        if(number<550)
            number=0;
        else number=number-550;


        handler.setIsDownLoad(true);
        handler.setNumberOfPackage(number);
        handler.setNameOfFile(nameOfFile);


        createListPackets(nameOfFile.getBytes(),Controller.sizeOfPackage,CharacterTransferData.setConnection,NameCommand.downloadFile,
               0,number);

        sendPackage(listOfPackets.get(0),toServer);
        byte[] arrayFromServer=new byte[Controller.sizeOfPackage];

        // java.io.File folder=new java.io.File(folderToLoad);
        //  boolean signal=folder.mkdirs();
        File file=new File();
        Separator separator=new Separator();
        readPackForFile(socket,arrayFromServer);
      //  sendPackage("lol".getBytes(),toServer);
        separator.parce(arrayFromServer);

        int a = separator.getHeader().getNumberOfPacket();





        long pos=a*(Controller.sizeOfPackage-Header.sizeOfHeader);



        for(int i=0;i<separator.getHeader().getCountOfPacket()-number;i++){
            arrayFromServer=new byte[Controller.sizeOfPackage];


            readPackFull(socket, arrayFromServer);

            separator.parce(arrayFromServer);
            System.out.println("Received number packet is " + separator.getHeader().getNumberOfPacket());
            ////////////////////////////////////
          /*

          //  if(separator.getHeader().getNameCommand()!=NameCommand.downloadFile)
            //    throw new SocketException();
            if(separator.getHeader().getNumberOfPacket()<i){
                i = separator.getHeader().getNumberOfPacket();
                file.writeInfoInFileSpec(nameOfFile,cache,pos,size);
                pos = i*(Controller.sizeOfPackage - Header.sizeOfHeader);
                sm = 0;
                j = 0;
                size = 0;
            }
            System.arraycopy(separator.getMessage(),0,cache, sm, separator.getHeader().getSizeOfMessage());
            sm += separator.getHeader().getSizeOfMessage();

            size += separator.getMessage().length;
            j++;

            if(j%mul==0 && i!=0)
            {
                sm = 0;
                j=0;
            }
            if(j==0 && i!=0)
            {
                file.writeInfoInFileSpec(nameOfFile, cache, pos,size);
                pos += size;
                size = 0;
            }
            if(separator.getHeader().getSizeOfMessage()!=Controller.sizeOfPackage-Header.sizeOfHeader)
                file.writeInfoInFileSpec(nameOfFile,cache,pos,size);


            */

            ///////////////////////////////////////////////
            file.writeInfoInFile(nameOfFile,separator.getMessage(),separator.getHeader().getNumberOfPacket()*
                   (Controller.sizeOfPackage-Header.sizeOfHeader));


            handler.setNumberOfPackage(number+i);

        }
        handler.setIsDownLoad(false);
    }


    public void readPackFull(Socket s, byte[] array) throws IOException {
        DataInputStream dataInputStream=new DataInputStream(s.getInputStream());

        dataInputStream.readFully(array);
    }

    public void sendPackage(byte[] array, DataOutputStream toServer) throws IOException {
        toServer.write(array);

    }

    public void readPackage(byte[] array,BufferedReader fromServer) throws IOException {
        char[] arrayRead=new char[Controller.sizeOfPackage/2];
        fromServer.read(arrayRead);
        System.arraycopy(charArrayToByteArray(arrayRead),0,array,0,charArrayToByteArray(arrayRead).length);

    }

    public byte[] charArrayToByteArray(char[] beginArray){
        String string=new String(beginArray);
        return string.getBytes();
    }

    public byte[] getMessage(byte[] arrayFrom, KitOfHeaders kitOfHeaders)
    {
        int sizeOfMessage=0;
        int pos=0;
        if(kitOfHeaders.getTypeHeader()==0){
            sizeOfMessage=kitOfHeaders.getHeader().getSizeOfMessage();
            pos=Header.sizeOfHeader;

        }else{
            if(kitOfHeaders.getTypeHeader()==1){
                sizeOfMessage=kitOfHeaders.getSpecialData().getSizeOfMessage();
                pos=SpecialData.size;
            }else{
                return null;
            }
        }
        byte[] retArray=new byte[sizeOfMessage];
        System.arraycopy(arrayFrom,pos,retArray,0,sizeOfMessage);
        return retArray;
    }

    public String getTime(DataOutputStream toServer, BufferedReader fromServer,Socket socket) throws IOException, ClassNotFoundException {
        Header header=new Header(CharacterTransferData.setConnection,NameCommand.getTime,0,Controller.sizeOfPackage,
                0,0);
     //   socket.sendUrgentData(5);
        sendPackage(Header.headerToArrayOfBytes(header),toServer);
        byte[] arrayFromServer=new byte[Controller.sizeOfPackage];
        readPackForFile(socket, arrayFromServer);
        Separator separator=new Separator();
        separator.parce(arrayFromServer);
        return new String(separator.getMessage(), Charset.defaultCharset()) ;
    }

    public String echo(DataOutputStream toServer, BufferedReader fromServer, String stringRet, Socket socket)
            throws IOException, ClassNotFoundException {
        if(listOfPackets!=null)
            listOfPackets.clear();
        createListPackets(stringRet.getBytes(),Controller.sizeOfPackage,CharacterTransferData.setConnection,NameCommand.echo);
        sendPackage(listOfPackets.get(0), toServer);
        byte[] arrayFromServer=new byte[Controller.sizeOfPackage];
        readPackForFile(socket, arrayFromServer);
        Separator separator=new Separator();
        separator.parce(arrayFromServer);
        return new String(separator.getMessage(),Charset.defaultCharset());
    }

    public void shutDown(DataOutputStream toServer) throws IOException {
        Header header=new Header(CharacterTransferData.command,NameCommand.shutDown,0,Controller.sizeOfPackage,0,0);
        sendPackage(Header.headerToArrayOfBytes(header),toServer);
    }



    public void createListPacketsUdp(byte[] data, int sizeOfPacket,int numberOfPacket){


        listOfPackets=new ArrayList<>();
        HeaderUdp headerUdp=new HeaderUdp();
        int i=0;


        while(true) {
            byte[] newData=new byte[sizeOfPacket];
            headerUdp.setNumberOfPacket(i);

            if((data.length-i*(sizeOfPacket- HeaderUdp.getSizeOfHeaderUdp()))>sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()){

                headerUdp.setSizeOfMessage(sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp());
                headerUdp.setNumberOfPacket(numberOfPacket+i);
                System.arraycopy(HeaderUdp.headerUdpToArrayOfBytes(headerUdp), 0, newData, 0, HeaderUdp.getSizeOfHeaderUdp());
                System.arraycopy(data, i * (sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()), newData,HeaderUdp.getSizeOfHeaderUdp() ,
                        sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp());
                headerUdp.setSizeOfMessage(sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp());
                listOfPackets.add(newData);
                numberOfPacket++;

            }else  {
                headerUdp.setSizeOfMessage(data.length-i*(sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()));
                headerUdp.setNumberOfPacket(numberOfPacket+i);
                System.arraycopy(HeaderUdp.headerUdpToArrayOfBytes(headerUdp),0,newData,0, HeaderUdp.getSizeOfHeaderUdp());

                System.arraycopy(data, i * (sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()),newData,HeaderUdp.getSizeOfHeaderUdp() ,
                        data.length-i*(sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()) );
                headerUdp.setSizeOfMessage(data.length-i*(sizeOfPacket-HeaderUdp.getSizeOfHeaderUdp()));
                listOfPackets.add(newData);
                break;
            }
            i++;
        }
    }

    public void loadFileUdp(String nameOfFile, DataOutputStream toServer,int number, Handler handler)
            throws IOException {
        System.out.println("udpLoadFile");
        handler.setLoadUdp(true);
        handler.setNumberOfPackage(number);
        handler.setNameOfFile(nameOfFile);
        File file=new File(nameOfFile);
        int length=Controller.sizeOfPackage-HeaderUdp.getSizeOfHeaderUdp();

        byte[] arrayToServer=new byte[Controller.sizeOfPackage-HeaderUdp.getSizeOfHeaderUdp()];
        byte[] arrayFromServer=new byte[Controller.sizeOfPackage];
        long position=number*(Controller.sizeOfPackage-Header.sizeOfHeader);

        createListPackets(nameOfFile.getBytes(),Controller.sizeOfPackage,CharacterTransferData.setConnection,NameCommand.loadFileUdp,
                countOfPackets(file.getSizeOfFile()),handler.getNumberOfPackage());
        sendPackage(listOfPackets.get(0),toServer);

        DatagramSocket dataGramSocket=new DatagramSocket();
        dataGramSocket.setSoTimeout(22000);
        InetAddress inetAddress= InetAddress.getByName(Controller.nameHost);

        DatagramPacket datagramPacketSend=new DatagramPacket(listOfPackets.get(0),listOfPackets.get(0).length,inetAddress,Controller.port);
      //  dataGramSocket.send(datagramPacketSend);
        DatagramPacket datagramPacketReceive=new DatagramPacket(arrayFromServer,0,arrayFromServer.length);
      //  dataGramSocket.receive(datagramPacketReceive);

        StructureOfReturnValue structureOfReturnValue=new StructureOfReturnValue (false,0);

        int count=countOfPackets(file.getSizeOfFile());
        int jo=number;

        while(!structureOfReturnValue.isSignalOfEndFile()) {
            structureOfReturnValue= file.readInfoFromFile(nameOfFile, length, position,arrayToServer);
            for (int i = 0; i < listOfPackets.size(); i++) {
                //    readPackage(arrayFromServer,fromServer);

                datagramPacketSend.setData(arrayToServer);
                dataGramSocket.send(datagramPacketSend);
                dataGramSocket.receive(datagramPacketReceive);
                handler.setNumberOfPackage(jo);
                //
            }
            jo++;
            listOfPackets.clear();
            position+=length;
        }
        handler.setLoadUdp(false);
    }

    public void downloadFileUdp(){

    }


}
