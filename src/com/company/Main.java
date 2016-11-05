package com.company;

import com.company.Controller.Controller;
import com.company.CountOfParameterException.CountOfParameterException;
import com.company.ParseConfigFile.ParseConfigFileException;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException, CountOfParameterException, ClassNotFoundException, InterruptedException,
            ParseConfigFileException {
        Controller.move();
    }
}
