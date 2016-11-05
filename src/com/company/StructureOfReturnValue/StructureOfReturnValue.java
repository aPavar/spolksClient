package com.company.StructureOfReturnValue;

/**
 * Created by apava on 23.09.2016.
 */
public class StructureOfReturnValue {
    boolean signalOfEndFile;
    int lengthOfReadCharacter;

    public StructureOfReturnValue(boolean signalOfEndFile, int lengthOfReadCharacter) {
        this.signalOfEndFile = signalOfEndFile;
        this.lengthOfReadCharacter = lengthOfReadCharacter;
    }

    public boolean isSignalOfEndFile() {
        return signalOfEndFile;
    }

    public void setSignalOfEndFile(boolean signalOfEndFile) {
        this.signalOfEndFile = signalOfEndFile;
    }

    public int getLengthOfReadCharacter() {
        return lengthOfReadCharacter;
    }

    public void setLengthOfReadCharacter(int lengthOfReadCharacter) {
        this.lengthOfReadCharacter = lengthOfReadCharacter;
    }
}
