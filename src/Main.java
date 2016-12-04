import com.MyHouse.Controller.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    static String ip = "192.168.57.2";
    public static void main(String[] args) throws IOException, InterruptedException {
        Controller.move();
    }
}
