import javax.swing.*; //provides classes for color space
import javax.swing.border.*;
import java.awt.*; //only classes imported and not other packages inside it
import java.awt.event.*; //package event under awt imported
import java.util.*;
import java.text.*;
import java.net.*;  //for linking server and and socket
import java.io.*;   //for input and output of data(streams)

public class Server implements ActionListener
{
    JTextField text; //defined globally to access in action listener
    JPanel a1;       //defined globally to access in action listener bcoz it is only in the scope of contructor server

    static Box vertical = Box.createVerticalBox(); //to define a box globally to allign all the messages vertically one below the other on the right side
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server()
    {
        //to create our own panel
        f.setLayout(null);
        //1st panel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter()
        {
           public void mouseClicked(MouseEvent ae)
           {
                System.exit(0);
           } 
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,30,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel menu = new JLabel(i15);
        menu.setBounds(410,20,15,30);
        p1.add(menu);

        JLabel name = new JLabel("Iron-Man");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));    //new constructor new Font(anonymous)
        p1.add(name);

        JLabel status = new JLabel("Active-now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,13));                                      
        p1.add(status);

        //new panel, green panel ke neeche(a1)
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        p1.setBackground(new Color(7,94,84));
        f.add(a1);

        //add textbox below
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        //send button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,45);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);

        f.setSize(450,700);
        f.setLocation(200,30);
        f.setUndecorated(true);  //remove header bar
        f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String out = text.getText();

            //create a label output to store string out
            
            JPanel p2 = formatLabel(out);   //additional panel created to be on right and all rights are displayed on line end in a vertical box
            
            a1.setLayout(new BorderLayout());   //empty constructor borderlayout(or default constructor)

            //create panel on top right on top of a1 to display messages
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);

            vertical.add(right);   //adding all the rights to the box
            vertical.add(Box.createVerticalStrut(5));   //gives spaces between every right panel(height)

            a1.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            //to refresh the frame
            //comes under jframe so can be called without any object
            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out)  //return type is JPanel called by p2 JPanel
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);  //to set the background color below the text
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) 
    {
        new Server();

        try
        {
            ServerSocket skt = new ServerSocket(6001);  //ye ek server create kiya hai.isme koi error ya exception aaya toh catch me obj e me jayega
                                                
            
            while(true)
            {
                Socket s = skt.accept();             //to run an infinite loop
                DataInputStream din= new DataInputStream(s.getInputStream());
                dout= new DataOutputStream(s.getOutputStream());

                while(true)
                {
                    String msg = din.readUTF();

                    JPanel panel = formatLabel(msg);
                    JPanel left =new JPanel (new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);

                    vertical.add(left);
                    f.validate();   
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();    //object e me joh bhi type ka exception aaya hai voh display karo
        }
    }
}