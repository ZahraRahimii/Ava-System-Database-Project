package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public class GUI {
    public static int maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static boolean user_ready = false, pass_ready = false , n = false, fn = false, un = false, pa= false, bi= false, bo= false;

    public static String query = null;
    public static Connection con;
    public static Statement stmt = null;
    public static ResultSet rs = null;
    public static JPanel centerP = new JPanel(new BorderLayout());
    public static JTextArea everything_text = new JTextArea("\t\t\ttype here...");
    public static JButton ok = new JButton("OK");

    public static HashMap<JButton, Integer> following_ava = new HashMap<>();
    public static HashMap<JButton, Integer> sb_ava = new HashMap<>();
    public static HashMap<JButton, Integer> my_ava = new HashMap<>();
    public static HashMap<JButton, Integer> hashtag_ava = new HashMap<>();
    public static HashMap<JButton, Integer> ghanari_ava = new HashMap<>();
    public static HashMap<JButton, Integer> comments = new HashMap<>();

    public GUI() throws Exception {
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/new_new_ava","root","zr3h2k10");
        stmt = con.createStatement();
        JFrame  frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel rightP = new JPanel(new GridLayout(8, 1));

        everything_text.setFont(new Font("Segoe Script", Font.BOLD, 20));
        centerP.add(everything_text, BorderLayout.CENTER);
        centerP.add(ok, BorderLayout.EAST);

        JButton create_accountB = new JButton("Create New Account"); // **
        JButton login_accountB = new JButton("Login To Account");   // **
        JButton post_avaB = new JButton("Post Ava");                // **
        JButton get_activities_of_followingsB = new JButton("Get Activities Of Followings"); //**
        JButton get_my_avaB = new JButton("Get My Ava");    // **
        JButton send_messageB = new JButton("Send Message");
        JButton get_messagesB = new JButton("Get Messages");
        JButton getLoginsB = new JButton("Get Logins Status");     // **

        createAccount1(create_accountB);
        login(login_accountB);
        post_ava(post_avaB);
        getLogins1(getLoginsB);
        get_activities_of_(get_activities_of_followingsB);
        get_activities_of_(get_my_avaB);
        send_message(send_messageB);
        get_message1(get_messagesB);

        rightP.add(create_accountB);
        rightP.add(login_accountB);
        rightP.add(post_avaB);
        rightP.add(get_activities_of_followingsB);
        rightP.add(get_my_avaB);
        rightP.add(send_messageB);
        rightP.add(get_messagesB);
        rightP.add(getLoginsB);

//        JPanel centerP = new JPanel(new BorderLayout());


        JPanel leftP = new JPanel(new GridLayout(8 , 1));

        JButton followB = new JButton("Follow");            // **
        JButton unfollowB = new JButton("UnFollow");        //  **
        JButton blockB = new JButton("Block");              // **
        JButton unblockB = new JButton("UnBlock");          // **
        JButton get_sb_activitiesB = new JButton("Get Somebody's Activities"); // **
        JButton get_ava_with_hashtagB = new JButton("Get Ava With 'hashtag'");
        JButton ghanari_avaB = new JButton("Get Ghanari Ava");
        JButton first_pageB = new JButton("Go To First Page");

        follow_block(followB);
        follow_block(unfollowB);
        follow_block(blockB);
        follow_block(unblockB);
        get_activities_of_(get_sb_activitiesB);
        goToFirstPage(first_pageB);
        get_activities_of_(get_ava_with_hashtagB);
        get_activities_of_(ghanari_avaB);

        leftP.add(followB);
        leftP.add(unfollowB);
        leftP.add(blockB);
        leftP.add(unblockB);
        leftP.add(get_sb_activitiesB);
        leftP.add(get_ava_with_hashtagB);
        leftP.add(ghanari_avaB);
        leftP.add(first_pageB);

        panel.add(rightP, BorderLayout.EAST);
        panel.add(centerP, BorderLayout.CENTER);
        panel.add(leftP, BorderLayout.WEST);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("Ava");
        frame.setVisible(true);
        frame.setSize(maxX, maxY-50);

    }

    public void login(JButton loginB){
        loginB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame  frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 1));

                JTextField user_name = new JTextField();
                JPasswordField pass = new JPasswordField();
                panel.add(user_name);
                panel.add(pass);

                frame.add(panel, BorderLayout.CENTER);
                frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/3,
                        Toolkit.getDefaultToolkit().getScreenSize().height/3);
                frame.pack();
                frame.setTitle("login");
                frame.setVisible(true);
                frame.setSize( Toolkit.getDefaultToolkit().getScreenSize().width/5,
                        Toolkit.getDefaultToolkit().getScreenSize().height/5);

                System.out.println(user_name.getText());
                System.out.println(pass.getPassword());
                user_name.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER){
                            // sql method
                            user_ready = true;
                            System.out.println("user");

                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                pass.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            pass_ready = true;
                            if (user_ready){
                                System.out.println(user_name.getText());
                                System.out.println(new String(pass.getPassword()));
                                try {
                                    login_sql(user_name.getText(), new String(pass.getPassword()));
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
            }
        });


    }

    public static void login_sql (String user, String pass) throws Exception {

        query = "call loginIntoAccount3(" + "\'" + user + "\'," + "\'" + pass + "\');";
        rs = stmt.executeQuery(query);
    }

    public static void getLogins1 (JButton getLogins){
        getLogins.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                query = "call getLogins2()";
                JFrame f = new JFrame("Get Logins");
                f.setSize(maxX/5, maxY/5);
                JTextArea jta = new JTextArea();
                String logs = "";
                try {
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        logs = logs + "\n" + new String(rs.getString(1));
                    }
                    jta.append(logs);
                    f.add(new JPanel(new GridLayout()).add(jta));
                    f.setVisible(true);
                } catch (Exception e1){
                    System.out.println(e1);
                }
        }});

    }

    public static JButton createAccount1(JButton create_account){
        create_account.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame f = new JFrame("To Create Account");
                f.setSize(maxX/3, maxY/3);
                JTextField  name = new JTextField("Enter name"), fname= new JTextField("Enter family name"),
                        uname= new JTextField("Enter user name"), pass= new JTextField("Enter password")
                        , birthday= new JTextField("Enter birthday"), bio= new JTextField("Enter bio");
                JPanel p = new JPanel(new GridLayout(6, 1));
                f.add(p);
                p.add(name); p.add(fname); p.add(uname); p.add(pass) ; p.add(birthday) ;p.add(bio);
                f.setVisible(true);
                name.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            n = true;
                            if (n & fn & un & pa & bi & bo) {
                                create_account_sql(name.getName(), fname.getName(), uname.getName(), pass.getName(), birthday.getName(), bio.getName());
                            }
                        }
                        ;
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                fname.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            fn = true;
                            if (n & fn & un & pa & bi & bo) {
                                create_account_sql(name.getName(), fname.getName(), uname.getName(), pass.getName(), birthday.getName(), bio.getName());
                            }
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                uname.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            un = true;
                            if (n & fn & un & pa & bi & bo) {

                                create_account_sql(name.getName(), fname.getName(), uname.getName(), pass.getName(), birthday.getName(), bio.getName());
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                pass.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            pa = true;
                            if (n & fn & un & pa & bi & bo) {
                                create_account_sql(name.getName(), fname.getName(), uname.getName(), pass.getName(), birthday.getName(), bio.getName());
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                birthday.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            bi = true;
                            if (n & fn & un & pa & bi & bo) {
                                create_account_sql(name.getName(), fname.getName(), uname.getName(), pass.getName(), birthday.getName(), bio.getName());
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                bio.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            bo = true;
                            if (n & fn & un & pa & bi & bo) {
                                create_account_sql(name.getText(), fname.getText(), uname.getText(), pass.getText(), birthday.getText(), bio.getText());
                            }
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
            }
        });
        return create_account;
    }

    public static void create_account_sql(String name, String fname,  String uname,  String pass,  String birthday,  String bio) {

        name = "\'" + name + "\',";
        fname = "\'" + fname + "\',";
        uname = "\'" + uname + "\',";
        pass = "\'" + pass + "\',";
        birthday = "\'" + birthday + "\',";
        bio = "\'" + bio + "\'";

        query = "call create_account2( " + name + fname + uname + pass + birthday + bio + ")";
        System.out.println(query);
        try {
            rs = stmt.executeQuery(query);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void post_ava(JButton ava){
        ava.addActionListener(new NewAva());

    }

    public static class NewAva implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            new_page();

            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    String content = everything_text.getText();
                    System.out.println(everything_text.getText());
                    query = "call post_ava11(" + "\"" + content + "\"" + ")";
//                    System.out.println(query);
                    try {
                        rs = stmt.executeQuery(query);
                    } catch (Exception e2){
                        System.out.println(e2);
                    }
                    JFrame f = new JFrame("Your ava posted");
                    f.setSize(maxX/10, maxY/5);
                    f.setLocation(maxX/3, maxY/3);
                    f.setVisible(true);
                }
            });
//            centerP.setVisible(true);
//            centerP.revalidate();
//            centerP.repaint();
        }
    }

    public static void follow_block(JButton button){
        button.addActionListener(new Follow_Block());
    }


    public static class Follow_Block implements ActionListener{
        String sfd;

        @Override
        public void actionPerformed(ActionEvent e1) {
            new_page();
            sfd = e1.getActionCommand();
//            System.out.println("gu");
//            new_page();
            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e2) {
                    super.mouseClicked(e2);
                    String sb = everything_text.getText();
//                    System.out.println(sb);
                    try {
                        if (sfd.equals("Follow"))
                            query = "call follow2(" + "\'" + sb + "\')";
                        else if (sfd.equals("UnFollow"))
                            query = "call unfollow1(" + "\'" + sb + "\')";
                        else if (sfd.equals("Block"))
                            query = "call block1(" + "\'" + sb + "\')";
                        else if (sfd.equals("UnBlock"))
                            query = "call unblock1(" + "\'" + sb + "\')";

                        System.out.println(query);
                        rs = stmt.executeQuery(query);
                    } catch (Exception ee){
                        System.out.println(ee);
                    }
                }
            });

        }
    }

    public static void get_activities_of_(JButton following_ava){
        following_ava.addActionListener(new GetAvaOfSb());
    }

    public static class GetAvaOfSb implements ActionListener{
        Object[][] table = null;

        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println(e.getActionCommand());
            if (e.getActionCommand().equals("Get Activities Of Followings")){

                for (Component c: centerP.getComponents()) {
                    centerP.remove(c);
                }
                table = ava_followings_sql();
                get_avas_panel(table, "Get Activities Of Followings");

            } else if (e.getActionCommand().equals("Get Somebody's Activities")) {
//                new_page();
                ok.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        super.mouseClicked(e);
                        for (Component c: centerP.getComponents()) {
                            centerP.remove(c);
                        }
                        table = ava_sb_sql(everything_text.getText());
                        get_avas_panel(table, "Get Somebody's Activities");
                    }
                });
            } else if (e.getActionCommand().equals("Get My Ava")){
                for (Component c: centerP.getComponents()) {
                    centerP.remove(c);
                }
                table = my_ava_sql();
                get_avas_panel(table,"Get My Ava");

            } else if (e.getActionCommand().equals("Get Ava With 'hashtag'")){
//                new_page();
                ok.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        System.out.println("SDfsdf");
                        for (Component c: centerP.getComponents()) {
                            centerP.remove(c);
                        }
                        table = ava_with_hashtag_sql(everything_text.getText());
                        get_avas_panel(table, "Get Ava With 'hashtag'");
                    }
                });
            } else if (e.getActionCommand().equals("Get Ghanari Ava")){
                for (Component c: centerP.getComponents()) {
                    centerP.remove(c);
                }
                table= ghanari_ava();
                get_avas_panel(table, "Get Ghanari Ava");
            }
        }

    }
    public static void new_page(){
        for (Component c: centerP.getComponents()) {
            centerP.remove(c);
        }
        JTextArea everything_text = new JTextArea("\t\t\ttype here...");
        everything_text.setFont(new Font("Segoe Script", Font.BOLD, 20));
        centerP.add(everything_text, BorderLayout.CENTER);
        centerP.add(ok, BorderLayout.EAST);
        centerP.revalidate();
        centerP.repaint();

    }
    public static void get_avas_panel(Object[][] table, String ava_type){
        JPanel newCenterPanel = new JPanel(new GridBagLayout());
        int y = 0;
        HashMap<JButton, Integer> tmp = new HashMap();
        for (final Object[] row : table) {
            if (row[1] != (null)) {
                String content = Arrays.toString(row);
                content = content.substring(1, content.length()-1);
                StringBuffer sb = new StringBuffer(content);
                sb = sb.deleteCharAt(sb.length()-1);
                JTextArea newText = new JTextArea(sb.toString());
                newText.setFont(new Font("Segoe Script", Font.BOLD, 20));
                newText.setSize(maxX / 2, maxY / 2);
                newText.setEditable(false);

                JButton getComments = new JButton("Get Comments");
                JButton add_comment = new JButton("Add Comment");
                JButton like = new JButton("LIKE");
                JButton dislike = new JButton("DISLIKE");
                JButton get_likes = new JButton("Get Likes Number");
                JButton likes_list = new JButton("Who like Ava?");

                addComment1(add_comment, ava_type);
                getComments1(getComments, ava_type);
                like1(like, ava_type);
                dislike1(dislike, ava_type);
                get_likes_num(get_likes, ava_type);
                likes_list1(likes_list, ava_type);

                centerP.remove(ok);
                centerP.remove(everything_text);
                centerP.revalidate();
                centerP.repaint();

                JPanel p1 = new JPanel(new BorderLayout());
                p1.add(newText, BorderLayout.CENTER);

                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.CENTER;
                c.gridx = 2;
                c.gridy = y;
                c.ipadx = 100;
                c.ipady = 100;
                newCenterPanel.add(p1, c);

//            c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = y + 1;
                c.ipadx = 5;
                c.ipady = 10;
                newCenterPanel.add(getComments, c);
                int ava_id = Integer.parseInt((String) row[0]);
                tmp.put(getComments, ava_id);

                c.gridx = 1;
                newCenterPanel.add(add_comment, c);
                tmp.put(add_comment, ava_id);
                c.gridx = 2;
                newCenterPanel.add(get_likes, c);
                tmp.put(get_likes,ava_id);
                c.gridx = 3;
                newCenterPanel.add(dislike, c);
                tmp.put(dislike, ava_id);
                c.gridx = 4;
                newCenterPanel.add(like, c);
                tmp.put(like, ava_id);
                c.gridx = 5;
                newCenterPanel.add(likes_list, c);
                tmp.put(likes_list, ava_id);

                y += 5;

                JScrollPane scrollPane = new JScrollPane(newCenterPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
                        , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


                centerP.add(scrollPane, BorderLayout.CENTER);

                centerP.revalidate();
                centerP.repaint();
            }
        }
        if (ava_type.equals("Get Activities Of Followings"))
            following_ava = tmp;
        else if (ava_type.equals("Get Somebody's Activities"))
            sb_ava = tmp;
        else if (ava_type.equals("Get My Ava"))
            my_ava = tmp;
        else if (ava_type.equals("Get Ava With 'hashtag'"))
            hashtag_ava = tmp;
        else if (ava_type.equals("Get Ghanari Ava"))
            ghanari_ava = tmp;
//        else if (ava_type.equals("Add Comment"))
//            comments = tmp;
    }

    public static void like1(JButton like, String ava_type){

        like.addMouseListener(new MouseAdapter() {
            int ava_id;

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ava_type.equals("Get Activities Of Followings"))
                    ava_id = following_ava.get(like);
                else if (ava_type.equals("Get Somebody's Activities"))
                    ava_id = sb_ava.get(like);
                else if (ava_type.equals("Get My Ava"))
                    ava_id = my_ava.get(like);
                else if (ava_type.equals("Get Ava With 'hashtag'"))
                    ava_id = hashtag_ava.get(like);
                else if (ava_type.equals("Get Ghanari Ava"))
                    ava_id = ghanari_ava.get(like);

                try {
                    query = "call like_ava2(" + ava_id + ",0)";
                    rs = stmt.executeQuery(query);
                } catch (Exception e1){
                    System.out.println(e1);
                }
            }
        });
    }
    public static void dislike1(JButton dislike, String ava_type){

        dislike.addMouseListener(new MouseAdapter() {
            int ava_id;

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ava_type.equals("Get Activities Of Followings"))
                    ava_id = following_ava.get(dislike);
                else if (ava_type.equals("Get Somebody's Activities"))
                    ava_id = sb_ava.get(dislike);
                else if (ava_type.equals("Get My Ava"))
                    ava_id = my_ava.get(dislike);
                else if (ava_type.equals("Get Ava With 'hashtag'"))
                    ava_id = hashtag_ava.get(dislike);
                else if (ava_type.equals("Get Ghanari Ava"))
                    ava_id = ghanari_ava.get(dislike);

                try {
                    query = "call like_ava2(" + ava_id + ",0)";
                    rs = stmt.executeQuery(query);
                } catch (Exception e1){
                    System.out.println(e1);
                }
            }
        });
    }
    public static void get_likes_num(JButton getLikeNum, String ava_type){

        getLikeNum.addMouseListener(new MouseAdapter() {
            int ava_id;

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ava_type.equals("Get Activities Of Followings"))
                    ava_id = following_ava.get(getLikeNum);
                else if (ava_type.equals("Get Somebody's Activities"))
                    ava_id = sb_ava.get(getLikeNum);
                else if (ava_type.equals("Get My Ava"))
                    ava_id = my_ava.get(getLikeNum);
                else if (ava_type.equals("Get Ava With 'hashtag'"))
                    ava_id = hashtag_ava.get(getLikeNum);
                else if (ava_type.equals("Get Ghanari Ava"))
                    ava_id = ghanari_ava.get(getLikeNum);

                try {
                    query = "call likes_number3(" + ava_id + ", 0)";
                    rs = stmt.executeQuery(query);
                    rs.next();

                    JFrame f = new JFrame();
                    f.setSize(maxX/5, maxY/5);
                    f.add(new JLabel("likes number is : " + rs.getString(1)));
                    f.setVisible(true);
                } catch (Exception e1){
                    System.out.println(e1);
                }
            }
        });
    }
    public static void likes_list1(JButton likesL , String ava_type){
        likesL.addMouseListener(new MouseAdapter() {
            int ava_id ;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ava_type.equals("Get Activities Of Followings"))
                    ava_id = following_ava.get(likesL);
                else if (ava_type.equals("Get Somebody's Activities"))
                    ava_id = sb_ava.get(likesL);
                else if (ava_type.equals("Get My Ava"))
                    ava_id = my_ava.get(likesL);
                else if (ava_type.equals("Get Ava With 'hashtag'"))
                    ava_id = hashtag_ava.get(likesL);
                else if (ava_type.equals("Get Ghanari Ava"))
                    ava_id = ghanari_ava.get(likesL);

                try {
                    query = "call user_name_whom_like1(" + ava_id + ", 1)";
                    System.out.println(ava_id);
                    rs = stmt.executeQuery(query);
                    JFrame f = new JFrame();
                    f.setSize(maxX/5, maxY/5);
                    JTextArea text = new JTextArea();
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                        text.append(rs.getString(1) +"\n");
                    }
                    text.setEditable(false);
                    f.add(text);
                    f.setVisible(true);
                } catch (Exception e1){
                    System.out.println(e1);
                }
            }
        });
    }
    public static void getComments1 (JButton getComments, String ava_type){
        getComments.addMouseListener(new MouseAdapter() {
            int ava_id ;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Component c: centerP.getComponents()) {
                    centerP.remove(c);
                }
                centerP.revalidate();
                centerP.repaint();
                if (ava_type.equals("Get Activities Of Followings"))
                    ava_id = following_ava.get(getComments);
                else if (ava_type.equals("Get Somebody's Activities"))
                    ava_id = sb_ava.get(getComments);
                else if (ava_type.equals("Get My Ava"))
                    ava_id = my_ava.get(getComments);
                else if (ava_type.equals("Get Ava With 'hashtag'"))
                    ava_id = hashtag_ava.get(getComments);
                else if (ava_type.equals("Get Ghanari Ava"))
                    ava_id = ghanari_ava.get(getComments);
                else if (ava_type.equals("Add Comment"))
                    ava_id = comments.get(getComments);

                final Object[][] table = get_comment_sql(ava_id);
                get_avas_panel(table, "" );

            }
        });
    }
    public static Object[][] get_comment_sql(int ava_id){

        query = "call get_comments_of_ava9(" + ava_id + ")";
        final Object[][] table = new String[100][5];

        int j = 0;
        try {
            while (rs.next()) {
                table[j++] = new String[]{rs.getString(1), rs.getString(7), rs.getString(8),
                        rs.getString(9), ""+(rs.getString(10))+""};
            }
            for (final Object[] row : table) {
                if (row[1] != (null))
                    System.out.format("%15s%15s%250s%20s%30s%n", row);
            }


        } catch (Exception e1){
            System.out.println(e1);
        }
        return table;
    }
    public static void addComment1(JButton add_comment, String ava_type){
        add_comment.addMouseListener(new MouseAdapter() {
            int ava_id;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new_page();
                ok.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (!ava_type.equals(null) & ava_type.equals("Get Activities Of Followings"))
                            ava_id = following_ava.get(add_comment);
                        else if (!sb_ava.equals(null) &ava_type.equals("Get Somebody's Activities"))
                            ava_id = sb_ava.get(add_comment);
                        else if (!sb_ava.equals(null) & ava_type.equals("Get My Ava"))
                            ava_id = sb_ava.get(add_comment);
                        else if (!hashtag_ava.equals(null) &ava_type.equals("Get Ava With 'hashtag'"))
                            ava_id = hashtag_ava.get(add_comment);
                        else if (!ghanari_ava.equals(null) &ava_type.equals("Get Ghanari Ava"))
                            ava_id = ghanari_ava.get(add_comment);
                        String content = everything_text.getText();

                        query = "call comment_on_ava2(" + "\"" + content + "\"," + ava_id + ")";

                        try {
                            rs = stmt.executeQuery(query);
                        } catch (Exception e2){
                            System.out.println(e2);
                        }
                        JFrame f = new JFrame("Your ava posted");
                        f.setSize(maxX/10, maxY/5);
                        f.setLocation(maxX/3, maxY/3);
                        f.setVisible(true);
                    }
                });


            }
        });
    }
    public static Object[][] ava_followings_sql(){
        query = "call get_followings_activities6()";
        final Object[][] table = new String[100][4];

        try {
            rs = stmt.executeQuery(query);
            int j = 0;
            while (rs.next()) {
                table[j++] = new String[]{rs.getString(1), rs.getString(2),
                        rs.getString(3), "" + rs.getDate(4) + ""};
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return table;
    }

    public static Object[][] ava_sb_sql(String sb){
        query = "call get_sb_activities7(" + "\'" + sb+ "\')";
        final Object[][] table = new String[100][4];

        try {
            rs = stmt.executeQuery(query);
            int j = 0;
            while (rs.next()) {
                table[j++] = new String[]{rs.getString(1),rs.getString(2),
                        rs.getString(3), ""+rs.getDate(4)+""};
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return table;

    }

    public static Object[][] my_ava_sql(){
        query = "call get_my_ava14()";
        final Object[][] table = new String[100][4];

        try {
            rs = stmt.executeQuery(query);
            int j = 0;
            while (rs.next()) {
                table[j++] = new String[]{rs.getString(1), rs.getString(2), rs.getString(3),
                        ""+rs.getDate(4)+""};
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return table;
    }

    public static Object[][] ava_with_hashtag_sql(String hashtag){
        query = "call ava_with_specific_hashtag8(" + "\'" + hashtag + "\')";
        final Object[][] table = new String[100][4];

        try {
            rs = stmt.executeQuery(query);
            int j = 0;
            while (rs.next()) {
                if (rs.getString(1) != null) {
                    table[j++] = new String[]{rs.getString(1), rs.getString(2),
                            rs.getString(3),""+ rs.getDate(4)+""};
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return table;
    }

    public static Object[][] ghanari_ava(){
        query = "call ghanari_ava14()";
        final Object[][] table = new String[100][2];

        try {
            rs = stmt.executeQuery(query);

            int j = 0;
            while (rs.next()) {
                table[j++] = new String[] {""+rs.getInt(1), rs.getString(2)};
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return table;
    }

    public static void goToFirstPage(JButton firstP){
        firstP.addActionListener(new FirstPage());
    }
    public static class FirstPage implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Component c: centerP.getComponents()) {
                centerP.remove(c);
            }
            JTextArea everything_text = new JTextArea("\t\t\ttype here...");
            everything_text.setFont(new Font("Segoe Script", Font.BOLD, 20));
            centerP.add(everything_text, BorderLayout.CENTER);
            centerP.add(ok, BorderLayout.EAST);
            centerP.revalidate();
            centerP.repaint();
        }
    }

    public static void send_message(JButton send_message){
        send_message.addActionListener(new Send_message());
    }
    public static class Send_message implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { // 'type' to 'id'
                                                                    // content
                                                                    // ava_id
                    super.mouseClicked(e);
                    String content = everything_text.getText();
                    String new_content = "";
                    String[] split_con =  content.split("\n");
                    String firstLine = split_con[0];
                    String type = firstLine.split(" ")[0];
                    String to_id = firstLine.split(" ")[2];
//                    int ava_id = ;
                    for (int i = 1; i < content.split("\n").length; i++) {
                        new_content = new_content + split_con[i];
                    }
                    new_content = "\"" + new_content + "\"";

                    query = "call send_message2(" + type + ", " + new_content + ", " + split_con[split_con.length-1].split(" ")[0]  + "," +
                            "\'" + to_id + "\')";

                    try {
                        rs = stmt.executeQuery(query);
                    } catch (Exception e1){
                        System.out.println(e1);
                    }
                }
            });
        }
    }

    public static void get_message1(JButton get_message){
        get_message.addActionListener(new Get_Message());
    }

    public static class Get_Message implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    final Object[][] table = new String[100][5];
                    query = "call get_message_from4(" + "\'" + everything_text.getText() + "\')";
                    JFrame f = new JFrame();
                    f.setSize(maxX/3, maxY/3);
                    JTextArea text = new JTextArea();
                    try {
                        rs = stmt.executeQuery(query);
                        int j = 0;
                        table[j++] = new String[]{rs.getMetaData().getColumnName(1), rs.getMetaData().getColumnName(2),
                                rs.getMetaData().getColumnName(3), rs.getMetaData().getColumnName(4), rs.getMetaData().getColumnName(5)};
                        while (rs.next()) {
                            if (rs.getString(1) != null) {
                                table[j++] = new String[]{rs.getString(1), rs.getString(2),
                                        rs.getString(3), rs.getString(4),""+ rs.getDate(5)+""};
                            }
                        }
                        for (final Object[] row : table) {
                            if (row[1] != (null)) {
                                String content = Arrays.toString(row);
                                text.append(content+"\n");
                            }
                        }
                    } catch (Exception e1){
                        System.out.println(e1);
                    }

                    text.setEditable(false);
                    f.add(text);
                    f.setVisible(true);

                }
            });
        }
    }

    public static void main(String[] args){
        try {
            new GUI();

        } catch (Exception e){
            System.out.println(e);
        }
    }
}
