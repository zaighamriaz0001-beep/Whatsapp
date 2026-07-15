
package chatting.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class Client implements ActionListener{
    
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataInputStream din;
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client(){
        
        f.setBounds(800, 50, 450, 600);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        
        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 450, 50);
        p1.setBackground(new Color(7, 94, 94));
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(Server.class.getResource("/chatting/Icons/back.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon b = new ImageIcon(i2);
        JLabel back = new JLabel(b);
        back.setBounds(5, 10, 30, 30);
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        ImageIcon i3 = new ImageIcon(Server.class.getResource("/chatting/Icons/Sir.png"));
        Image i4 = i3.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon p = new ImageIcon(i4);
        JLabel profile = new JLabel(p);
        profile.setBounds(50, 5, 45, 40);
        
        p1.add(profile);
        
        
        ImageIcon i5 = new ImageIcon(Server.class.getResource("/chatting/Icons/phone.png"));
        Image i6 = i5.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon ph = new ImageIcon(i6);
        JLabel phone = new JLabel(ph);
        phone.setBounds(300, 10, 30, 30);
        
        p1.add(phone);
        
        ImageIcon i7 = new ImageIcon(Server.class.getResource("/chatting/Icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon v = new ImageIcon(i8);
        JLabel video = new JLabel(v);
        video.setBounds(350, 10, 30, 30);
        
        p1.add(video);
        
        ImageIcon i9 = new ImageIcon(Server.class.getResource("/chatting/Icons/more.png"));
        Image i10 = i9.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon m = new ImageIcon(i10);
        JLabel more = new JLabel(m);
        more.setBounds(400, 10, 30, 30);
        
        p1.add(more);
        
        JLabel name = new JLabel();
        name.setText("Sir Javed Ferzund");
        name.setBounds(110, 8, 150, 20);
        name.setFont(new Font("Bauhaus 93", Font.BOLD, 18));
        name.setForeground(Color.WHITE);
        p1.add(name);
        
        JLabel status = new JLabel();
        status.setText("Active");
        status.setBounds(112, 25, 100, 20);
        status.setFont(new Font("Ariel", Font.PLAIN, 14));
        status.setForeground(Color.WHITE);
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5, 55, 440, 420);
        f.add(a1);

        
        JScrollPane scroll = new JScrollPane(a1);
        scroll.setBounds(5, 55, 440, 420);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        f.add(scroll);
        
        text = new JTextField();
        text.setBounds(5, 490, 300, 50);
        text.setFont(new Font("Sans", Font.PLAIN, 16));
        
        
        f.add(text);
        
        JButton send = new JButton();
        send.setText("SEND");
        send.setBounds(330, 490, 100, 50);
        send.setFont(new Font("Aptos", Font.BOLD, 14));
        send.addActionListener(this);
        send.setBackground(new Color(7,94,94));
        send.setForeground(Color.WHITE);
        f.add(send);
        
        
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        
        try{
            String out = text.getText(); 
            JPanel p2 = formatLable(out); 
            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15)); 
            a1.add(vertical, BorderLayout.PAGE_START);


            dout.writeUTF(out);
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLable(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style = \"width: 150px\">" + out + "</p></html>");        
        output.setFont(new Font("Tahoma", Font.BOLD, 14));
        output.setBackground(new Color(136, 250, 132));
        output.setBorder(new EmptyBorder(15, 15, 15, 30));
        output.setOpaque(true);
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        JLabel time = new JLabel();
        
        time.setFont(new Font("Tahoma", Font.PLAIN, 12));
        time.setForeground(Color.GRAY);

        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    public static void main(String [] args){
        new Client();
        
        try{
            Socket s = new Socket("127.0.0.1", 5000);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true) {
                a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLable(msg);
                
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    f.validate();
                    s.close();
                }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
