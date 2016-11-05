package com.company.Parser;

import com.company.Command.Command;
import com.company.CountOfParameterException.CountOfParameterException;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by apava on 11.09.2016.
 */
public  class Parser {
    public static Command[] listCommands;
    String allStringParameter;
    public void doAllStringParameterForEcho(String allCommand){
        byte[] b=new byte[allCommand.getBytes().length-5];
        System.arraycopy(allCommand.getBytes(),5,b,0,allCommand.getBytes().length-5);
        allStringParameter=new String(b,Charset.defaultCharset());
    }

    public String getAllStringParameter() {
        return allStringParameter;
    }

    public static void createListCommands(){
        listCommands=new Command[9];
        for(int i=0;i<9;i++)
            listCommands[i]=new Command();
        listCommands[0].setNameCommand("echo");
        listCommands[1].setNameCommand("time");
        listCommands[2].setNameCommand("loadFile");
        listCommands[3].setNameCommand("download");
        listCommands[4].setNameCommand("exit");
        listCommands[5].setNameCommand("shutdown");
        listCommands[6].setNameCommand("update");
        listCommands[7].setNameCommand("udpLoadFile");
        listCommands[8].setNameCommand("udpDownloadFile");
        int[] countOfParameter=new int[1];
        countOfParameter[0]=1;
        listCommands[2].setCountParameters(countOfParameter);
        listCommands[3].setCountParameters(countOfParameter);
        listCommands[7].setCountParameters(countOfParameter);
        listCommands[8].setCountParameters(countOfParameter);
    }
    public Parser(){
        createListCommands();
    }
    public Command getCommandWithParameters(String command) throws CountOfParameterException, NameCommandException {
        int signal=-1;
        int index=-1;
        for(int i=0;i<listCommands.length;i++) {
            signal = command.indexOf(listCommands[i].getNameCommand());
            if(signal==0) {
                index=i;
                break;
            }

        }

       if(index==-1)
        {
            throw new NameCommandException();
            //throw new CountOfParameterException();
        }
        ArrayList<String> parameters=new ArrayList<>();
        char[]string= command.toCharArray();
        for(int i=0;i<string.length;i++)
        {
            if(i!=string.length-1)
                if(string[i]==' '&&string[i+1]!=' ')
                {
                    String parameter= "";
                    i++;
                    if(i!=string.length)
                        while(i!=(string.length)&&string[i]!=' '){
                            parameter+=string[i];
                            i++;
                        }
                    parameters.add(parameter);
                }
        }

        Command commandInArray=new Command();
        commandInArray.setNameCommand(listCommands[index].getNameCommand());
        commandInArray.setListParameters(parameters);
        return commandInArray;
    }


}
