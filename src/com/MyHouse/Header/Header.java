package com.MyHouse.Header;

import com.MyHouse.CharacterTransferData.CharacterTransferData;
import com.MyHouse.NameCommand.NameCommand;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Header {
    static long protocolID=1958869254;
    CharacterTransferData characterTransferData;
    NameCommand nameCommand;
    int numberOfPacket;
    int sizeOfPacket;
    int sizeOfMessage;
    int countOfPacket;


    public static int sizeOfHeader=32;

    public int getCountOfPacket() {
        return countOfPacket;
    }

    public void setCountOfPacket(int countOfPacket) {
        this.countOfPacket = countOfPacket;
    }

    public int getSizeOfMessage() {
        return sizeOfMessage;
    }

    public Header(){

    }

    public int getSizeOfHeader() {
        return sizeOfHeader;
    }

    public void setSizeOfMessage(int sizeOfMessage) {
        this.sizeOfMessage = sizeOfMessage;
    }

    public void setInformation(CharacterTransferData characterTransferData,NameCommand nameCommand,int numberOfPacket,int sizeOfPacket, int sizeOfMessage){

        this.characterTransferData=characterTransferData;
        this.nameCommand=nameCommand;
        this.numberOfPacket=numberOfPacket;
        this.sizeOfPacket=sizeOfPacket;
        this.sizeOfMessage=sizeOfMessage;

    }

    public int getNumberOfPacket() {
        return numberOfPacket;
    }

    public void setNumberOfPacket(int numberOfPacket) {
        this.numberOfPacket = numberOfPacket;
    }


    static public byte[] headerToArrayOfBytes(Header header) throws IOException {


        byte[] array=new byte[sizeOfHeader];
        System.arraycopy(ByteBuffer.allocate(8).putLong(Header.protocolID).array(),0,array,0,8);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.characterTransferData.ordinal()).array(),0,array,8,4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.nameCommand.ordinal()).array(),0,array,12,4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.numberOfPacket).array(),0,array,16,4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.sizeOfMessage).array(),0,array,20,4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.sizeOfPacket).array(),0,array,24,4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(header.countOfPacket).array(),0,array,28,4);
        return array;
    }

    static public Header arrayOfBytesToHeader (byte[] arrayOfByte) throws IOException, ClassNotFoundException {
        Header header=new Header();
        byte[] array=new byte[4];

        ByteBuffer byteBuffer=ByteBuffer.wrap(arrayOfByte);
        int intCount = byteBuffer.getInt(8);

        switch (intCount) {
            case 0:
                header.characterTransferData = CharacterTransferData.setConnection;
                break;
            case 1:
                header.characterTransferData = CharacterTransferData.transferData;
                break;
            case 2:
                header.characterTransferData = CharacterTransferData.closingConnection;
                break;
            case 3:
                header.characterTransferData = CharacterTransferData.command;
        }

        //    byteBuffer.wrap(array);
        intCount = byteBuffer.getInt(12);
        switch (intCount) {
            case 0:
                header.nameCommand = NameCommand.noCommand;
                break;
            case 1:
                header.nameCommand = NameCommand.echo;
                break;
            case 2:
                header.nameCommand = NameCommand.getTime;
                break;
            case 3:
                header.nameCommand=NameCommand.loadFile;
                break;
            case 4:
                header.nameCommand=NameCommand.downloadFile;
                break;
            case 5:
                header.nameCommand=NameCommand.shutDown;
                break;
            case 6:
                header.nameCommand=NameCommand.loadFileUdp;
                break;
            case 7:
                header.nameCommand=NameCommand.downloadFileUdp;
                break;
        }

        header.numberOfPacket = byteBuffer.getInt(16);
        header.sizeOfMessage = byteBuffer.getInt(20);
        header.sizeOfPacket = byteBuffer.getInt(24);
        header.countOfPacket = byteBuffer.getInt(28);
        return header;

    }

    public Header(CharacterTransferData characterTransferData, NameCommand nameCommand, int numberOfPacket, int sizeOfPacket, int sizeOfMessage,
                  int countOfPacket) {
        this.characterTransferData = characterTransferData;
        this.nameCommand = nameCommand;
        this.numberOfPacket = numberOfPacket;
        this.sizeOfPacket = sizeOfPacket;
        this.sizeOfMessage = sizeOfMessage;
        this.countOfPacket = countOfPacket;
    }

    static boolean isHeader(byte[] array){
        if(array.length<Header.sizeOfHeader)
            return false;
        ByteBuffer byteBuffer=ByteBuffer.wrap(array);
        long longCount = byteBuffer.getLong(0);
        if(longCount==Header.protocolID){
            return true;
        }
        else
        {
            return false;
        }
    }
}
