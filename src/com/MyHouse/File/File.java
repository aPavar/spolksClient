package com.MyHouse.File;

import com.MyHouse.StructureOfReturnValue.StructureOfReturnValue;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by andrei on 03.12.16.
 */
public class File {
    String nameOfFile;
    long sizeOfFile;
    long getSizeOfFile(String nameOfFile) throws IOException {
        RandomAccessFile file=new RandomAccessFile(nameOfFile,"r");
        long length=file.length();
        file.close();
        return length;

    }

    public File(String nameOfFile) throws IOException {
        this.nameOfFile = nameOfFile;
        RandomAccessFile file=new RandomAccessFile(nameOfFile,"r");
        sizeOfFile= file.length();
        file.close();
    }
    public File() {

    }

    public String getNameOfFile() {

        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public long getSizeOfFile() {
        return sizeOfFile;
    }

    public void setSizeOfFile(long sizeOfFile) {
        this.sizeOfFile = sizeOfFile;
    }

    public StructureOfReturnValue readInfoFromFile(String nameOfFile, int sizeOfInfo, long position,
                                                   byte[] array) throws IOException {

        try( RandomAccessFile file=new RandomAccessFile(nameOfFile,"r")) {
            file.seek(position);

            long fileLength = file.length();
            for (int i = 0; i < sizeOfInfo; i++) {
                if (fileLength == position + i) {
                    file.close();
                    return new StructureOfReturnValue(true, i);
                }
                array[i] = file.readByte();
            }
        }
        return new StructureOfReturnValue(false,sizeOfInfo);
    }
}
