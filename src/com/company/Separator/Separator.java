package com.company.Separator;


import com.company.Header.Header;

import java.io.IOException;
import java.nio.charset.Charset;

public class Separator {
    Header header;
    byte[] message;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public Separator(Header header, byte[] message) {
        this.header = header;
        this.message = message;
    }

    public Separator(){

    }

    public void parce(byte[] array) throws IOException, ClassNotFoundException {
        header=Header.arrayOfBytesToHeader(array);
        if(header.getSizeOfMessage()>0)
        message=new byte[header.getSizeOfMessage()];
        System.out.println("SizeOfMessage" + header.getSizeOfMessage());
        System.arraycopy(array,Header.sizeOfHeader,message,0,message.length);

    }
}
