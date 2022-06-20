import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMain {

    static ArrayList<PrintWriter> pwl = new ArrayList<PrintWriter>();
    static MessageQueue<String> mQ = new MessageQueue<String>();

    public static void main(String[] args) throws Exception {

        System.out.println("Server Signing on");

        MessageDispatcher md = new MessageDispatcher();
        md.setDaemon(true);
        md.start();

        ServerSocket ss = new ServerSocket(9081);
        Socket soc = null;
        Thread th = null;
        Conversation c = null;

        for (int i = 0; i < 5; i++) {
            soc = ss.accept();
            System.out.println("Client No. " + (i + 1) + " connected");
            c = new Conversation(soc);
            th = new Thread(c);
            th.start();
        }
    }
}