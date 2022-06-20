import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyMsgSendActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add username with the message
        String str = ClientMain.username + " : " + ClientMain.tf.getText();
        ClientMain.nos.println(str);
        ClientMain.tf.setText("");
    }
}
