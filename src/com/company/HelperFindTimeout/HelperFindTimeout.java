package com.company.HelperFindTimeout;

import java.net.SocketTimeoutException;

/**
 * Created by andrei on 23.10.16.
 */
public class HelperFindTimeout extends Thread {
    private int numberBeginPackage;
    private int numberFollowingPackage;
    int a;
    long timeOut;
    private boolean end = false;
    public void run()  {
        while(!end){
            a=numberBeginPackage;
            try {
                sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(numberBeginPackage==numberFollowingPackage&&numberBeginPackage==a||end)
                break;
            a=numberBeginPackage;
            try {
                throw new SocketTimeoutException();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        if(!end)
            try {
                throw new SocketTimeoutException();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }


    }
    public HelperFindTimeout(int numberBeginPackage, int numberFollowingPackage, long timeOut)
            throws InterruptedException, SocketTimeoutException {
        this.numberBeginPackage = numberBeginPackage;
        this.numberFollowingPackage = numberFollowingPackage;
        this.timeOut=timeOut;
        start();

    }


    public int getNumberBeginPackage() {
        return numberBeginPackage;
    }

    public void setNumberBeginPackage(int numberBeginPackage) {
        this.numberBeginPackage = numberBeginPackage;
    }

    public int getNumberFollowingPackage() {
        return numberFollowingPackage;
    }

    public void setNumberFollowingPackage(int numberFollowingPackage) {
        this.numberFollowingPackage = numberFollowingPackage;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
