import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI {

    private static JFrame frameLogin;
    public static void login()
    {
        frameLogin=new JFrame("first way");

        JLabel lblUserName = new JLabel("User Name:");
        lblUserName.setBounds(10, 10, 100, 50);
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 50, 100, 50);

        JTextField txtUserName = new JTextField(10);
        txtUserName.setBounds(100,10,100,40);
        JTextField txtPassword = new JTextField(10);
        txtPassword.setBounds(100,50,100,40);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(200, 150, 90, 50);
        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                String userName = txtUserName.getText();
                String password = txtPassword.getText();
                // database
                if (userName.equals("jack") && password.equals("123")) {
                    System.out.println("login success");
                    menu();
                }
                else
                {
                    // dialog for error message
                    JDialog d = new JDialog(frameLogin, "Error");
                    JLabel l = new JLabel("User name or password error.");
                    d.add(l);
                    d.setSize(200, 200);
                    d.setVisible(true);
                }
            }
        });



        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.add(btnLogin);
        frameLogin.add(lblUserName);
        frameLogin.add(lblPassword);
        frameLogin.add(txtUserName);
        frameLogin.add(txtPassword);
        frameLogin.setSize(400, 300);
        frameLogin.setLayout(null);
        frameLogin.setVisible(true);
    }

    public static void menu()
    {
        JFrame frameMenu=new JFrame("Menu");
        JButton button = new JButton("Chat");
        button.setBounds(200, 150, 90, 50);
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.add(button);
        frameMenu.setSize(500, 600);
        frameMenu.setLayout(null);
        frameMenu.setVisible(true);
    }


    public static void main(String[] args) {
        login();
    }
}
