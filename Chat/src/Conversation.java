
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conversation implements Runnable {

    private Socket soc;
    private PrintWriter nos;
    private BufferedReader nis;

    public Conversation(Socket soc) throws Exception {
        this.soc = soc;
        this.nos = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
        ServerMain.pwl.add(this.nos);
        this.nis = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
    }

    @Override
    public void run() {
        try {
            try {
                String str = this.nis.readLine();

                while (!str.equals("end")) {

                    ServerMain.mQ.enqueue(str);
                    str = this.nis.readLine();
                }
            } catch (Exception e) {
            }
            ServerMain.pwl.remove(this.soc);
        } catch (Exception ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.nis.close();
                this.nos.close();
                this.soc.close();
            } catch (Exception e) {
            }
        }
    }
}