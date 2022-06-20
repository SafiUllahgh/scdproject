import java.io.PrintWriter;


public class MessageDispatcher extends Thread {
 
    @Override
    public void run() {
        System.out.println("Message Dispatcher Started");
 
        while (true) {
 
            String str = ServerMain.mQ.dequeue();
 
            for (PrintWriter pw : ServerMain.pwl) {
                System.out.println("Sent Message to All");
                pw.println(str);
            }
        }
    }
}