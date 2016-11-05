package com.company.Command;

import java.util.ArrayList;

/**
 * Created by apava on 12.09.2016.
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

    public String[] getTypesParameters() {
        return typesParameters;
    }

    public void setTypesParameters(String[] typesParameters) {
        this.typesParameters = typesParameters;
    }

    public int[] getCountParameters() {
        return countParameters;
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
