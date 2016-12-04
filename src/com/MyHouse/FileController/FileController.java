package com.MyHouse.FileController;

import com.MyHouse.StructureOfReturnValue.StructureOfReturnValue;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by andrei on 27.11.16.
 */
public class FileController {
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
