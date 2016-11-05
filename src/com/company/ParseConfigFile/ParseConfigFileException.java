package com.company.ParseConfigFile;

/**
 * Created by apava on 01.10.2016.
 */
public class ParseConfigFileException extends Exception {
    public ParseConfigFileException(int number, String key){
        System.out.println("Error in string number = " + number + " Format string is not right or value " +key+" is not correct");
    }

}
