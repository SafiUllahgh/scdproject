
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;


public class ClientMain {

    static JTextField tf = new JTextField(25);
    static PrintWriter nos = null;
    static String username;
  
    public static void main(String[] args) throws Exception {

        // Obtain server connection
        final Socket soc = new Socket("127.0.0.1", 9081);
        
        System.out.println("Client Signing on");
        
        // Create output channel to server
        // With Autoflush enabled
        nos = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
        // Create input channel from server
        final BufferedReader nis = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        // Initialise Swing Components
        JFrame chatFrame = new JFrame("Client Chat Window");
        final JTextArea msgBox = new JTextArea(50, 50);
        final JTextField tusername = new JTextField(10);
        final JButton send = new JButton("SEND");
        final JButton setUsername = new JButton("Set Username");
        final JLabel userNameDetails = new JLabel("Enter username:");
        final JButton logout = new JButton("Logout");
        JPanel jUsername = new JPanel();
        JPanel msgPanel = new JPanel();

        msgBox.setText("Enter username to start chatting");

        // By default disable chatting features
        send.setEnabled(false);
        msgBox.setEditable(false);
        tf.setEditable(false);
        logout.setVisible(false);

        // Set size of frames
        msgPanel.setSize(500, 100);
        chatFrame.setSize(500, 400);

        // Add Components on Swing container
        jUsername.add(userNameDetails);
        jUsername.add(tusername);
        jUsername.add(setUsername);
        jUsername.add(logout);

        msgPanel.add(tf);
        msgPanel.add(send);

        chatFrame.add(msgPanel, BorderLayout.SOUTH);
        chatFrame.add(msgBox, BorderLayout.CENTER);
        chatFrame.add(jUsername, BorderLayout.NORTH);

        // Create Listener object
        MyMsgSendActionListener l = new MyMsgSendActionListener();

        // Set various action listeners
        send.addActionListener(l);
        tf.addActionListener(l);
        // Set action listener for setting username
        setUsername.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tusername.getText().equals("") && tusername.getText().length() > 3) {
                    username = tusername.getText();
                    tusername.setEditable(false);
                    tusername.setEnabled(false);
                    tf.setEditable(true);
                    msgBox.setText("");
                    send.setEnabled(true);
                    setUsername.setEnabled(false);
                    setUsername.setVisible(false);
                    logout.setVisible(true);
                    nos.println(username + " Signed in");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter username more than 3 letters", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Set Logout action
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nos.println(username + " : " + "Signed out");
                    nos.println("end");
                    nos.close();
                    nis.close();
                    soc.close();
                    System.exit(0);
                } catch (Exception ex) {
                }
            }
        });

        // Give default close operation
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set frame visible
        chatFrame.setVisible(true);


        // take continous input from server
        try {
            String str = nis.readLine();
            if (str.startsWith(username + " : ")) {
                str = str.replace(username + " : ", "me : ");
            }
            while (!str.equals("end")) {
                msgBox.append(str + "\n");
                str = nis.readLine();
                if (str.startsWith(username + " : ")) {
                    str = str.replace(username + " : ", "me : ");
                }
            }
        } catch (Exception e) {
        }
    }

	public Object main() {
		// TODO Auto-generated method stub
		return "Client Signing on";
	}
}

