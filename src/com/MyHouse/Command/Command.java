package com.MyHouse.Command;

import java.util.ArrayList;

/**
 * Created by andrei on 02.12.16.
 */
public class Command {
    String nameCommand;
    int[] countParameters;
    String[] typesParameters;
    ArrayList<String> listParameters;



    public Command() {
    }

    public ArrayList<String> getListParameters() {
        return listParameters;
    }

    public void setListParameters(ArrayList<String> listParameters) {
        this.listParameters = listParameters;
    }

    public void setCountParameters(int[] countParameters) {
        this.countParameters = countParameters;
    }

    public String getNameCommand() {
        return nameCommand;
    }

    public void setNameCommand(String nameCommand) {
        this.nameCommand = nameCommand;
    }

}
