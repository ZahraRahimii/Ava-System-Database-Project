package com.company;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static String input;

    public static void main(String[] args) {
        ResultSet rs ;
        String query ;

        try {
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/new_new_ava","root","zr3h2k10");
            Statement stmt=con.createStatement();
            while (true) {
                input = sc.nextLine();

                if (input.contains("logins")) { // get logins
                    query = "call getLogins2()";
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        System.out.println(rs.getDate(1));
                    }

                } else if (input.contains("help")){
                    Object[][] firstC = new String[28][2];
                    int k = 0;
                    firstC[k][0] = "To Get Logins";
                    firstC[k++][1] = "get logins";

                    firstC[k][0] = "To Login To Your Account";
                    firstC[k++][1] = "login 'user_name' 'pass'";

                    firstC[k][0] = "To Login To Your Account";
                    firstC[k++][1] = "login 'user_name' 'pass'";

                    firstC[k][0] = "To Login To Your Account";
                    firstC[k++][1] = "login 'user_name' 'pass'";

                    firstC[k][0] = "To Login To Your Account";
                    firstC[k++][1] = "login 'user_name' 'pass'";

                    firstC[k][0] = "To Login To Your Account";
                    firstC[k++][1] = "login 'user_name' 'pass'";

                    firstC[k][0] = "TO Create Account";
                    firstC[k++][1] = "create account 'name' 'fname' 'user_name' 'pass' 'birthday' 'bio'";

                    firstC[k][0] = "To Get Followings Activities";
                    firstC[k++][1] = " get followings activities";

                    firstC[k][0] = "To Follow Somebody";
                    firstC[k++][1] = "I follow 'sb_user_name'";

                    firstC[k][0] = "To UnFollow Somebody";
                    firstC[k++][1] = "I unfollow 'sb_user_name'";

                    firstC[k][0] = "To Block Somebody";
                    firstC[k++][1] = "I block 'sb_user_name'";

                    firstC[k][0] = "To UnBlock Somebody";
                    firstC[k++][1] = "I unblock 'sb_user_name'";

                    firstC[k][0] = "To Post Ava";
                    firstC[k++][1] = "post ava 'content'";

                    firstC[k][0] = "To Get Your Ava";
                    firstC[k++][1] = "get my ava";

                    firstC[k][0] = "To Get Activities Of sb";
                    firstC[k++][1] = "get activities of 'sb_user_name'";


                    firstC[k][0] = "To Get Comments Of specific Ava";
                    firstC[k++][1] = "get comments of ava 'ava_id'";


                    firstC[k][0] = "To Comment On specific Ava";
                    firstC[k++][1] = "comment 'content' on ava 'ava_id'";

                    firstC[k][0] = "To Get Ava With specific hashtag";
                    firstC[k++][1] = "get ava with 'hashtag content'";


                    firstC[k][0] = "To Dislike specific Ava";
                    firstC[k++][1] = "dislike ava 'ava_id'";

                    firstC[k][0] = "To Like specific ava ";
                    firstC[k++][1] = " like ava 'ava_id'";

                    firstC[k][0] = "To Get dislike number";
                    firstC[k++][1] = "dislike number 'ava_id'";

                    firstC[k][0] = "To Get like number 'ava_id'";
                    firstC[k++][1] = "like number 'ava_id'";

                    firstC[k][0] = "To Get username of everyone liked specific ava";
                    firstC[k++][1] = "username of everyone liked ava 'ava_id'";

                    firstC[k][0] = "To Get username of everyone disliked ava 'ava_id'";
                    firstC[k++][1] = "username of everyone disliked ava 'ava_id'";

                    firstC[k][0] = "To Show Ghanari ava";
                    firstC[k++][1] = "show ghanari ava";

                    firstC[k][0] = "To Send Message to sb";
                    firstC[k++][1] = "send message 'type' 'content' / 'ava_id' to 'user_name'";

                    firstC[k][0] = "To Get message from sb";
                    firstC[k++][1] = "get message from 'user_name'";

                    firstC[k][0] = "To Get Senders list";
                    firstC[k++][1] = "get senders list";


                    for (Object[] row: firstC){
                        if (!row.equals(null))
                            System.out.format("%50s : %70s\n" , row);
                    }

                } else if (input.contains("login")) { // login user pass
                    String user, pass;
                    user = input.split(" ")[1];
                    pass = input.split(" ")[2];
                    query = "call loginIntoAccount3(" + "\'" + user + "\'," + "\'" + pass + "\');";
                    rs = stmt.executeQuery(query);

                } else if (input.contains("create account")) {  // create account name, fname, uname, pass, birthday, bio
                    String name, fname, uname, pass, birthday, bio;
                    String[] splitInput = input.split(" ");
                    name = "\'" + splitInput[2] + "\',";
                    fname = "\'" + splitInput[3] + "\',";
                    uname = "\'" + splitInput[4] + "\',";
                    pass = "\'" + splitInput[5] + "\',";
                    birthday = "\'" + splitInput[6] + "\',";
                    bio = "\'" + splitInput[7] + "\'";
                    query = "call create_account2( " + name + fname + uname + pass + birthday + bio + ")";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("get followings activities")) { // get followings activities
                    query = "call get_followings_activities6()";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    System.out.println(rs.getMetaData().getColumnCount());
                    final Object[][] table = new String[100][4];
                    int j = 0;

                    table[j++] = new String[]{rs.getMetaData().getColumnName(1), rs.getMetaData().getColumnName(2),
                            rs.getMetaData().getColumnName(3), rs.getMetaData().getColumnName(4)};

                    while (rs.next()) {
                        table[j++] = new String[]{rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getString(4)};
                    }
                    for (final Object[] row : table) {
                        if (row[1] != (null) )
                            System.out.format("%15s%15s%15s%250s%n", row);
                    }


                } else if (input.contains("unfollow")) { // I unfollow x
                    String sb = input.split(" ")[2];
                    query = "call unfollow1(" + "\'" + sb + "\')";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("follow")) { // I follow x
                    String sb = input.split(" ")[2];
                    query = "call follow2(" + "\'" + sb + "\')";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("unblock")) { // I unblock x
                    String sb = input.split(" ")[2];
                    query = "call unblock1(" + "\'" + sb + "\')";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("block")) { // I block x
                    String sb = input.split(" ")[2];
                    query = "call block1(" + "\'" + sb + "\')";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("post ava")) { // post ava content
                    String[] content_array = input.split(" ");
                    String content = "";
                    for (int i = 2; i < content_array.length; i++) {
                        content = content + content_array[i] + " ";
                    }
                    query = "call post_ava11(" + "\"" + content + "\"" + ")";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("get my ava")) { // get my ava
                    query = "call get_my_ava14()";
                    rs = stmt.executeQuery(query);
                    final Object[][] table = new String[100][3];
                    int j = 0;

                    table[j++] = new String[]{rs.getMetaData().getColumnName(1), rs.getMetaData().getColumnName(2),
                            rs.getMetaData().getColumnName(3)};

                    while (rs.next()) {
                        table[j++] = new String[]{rs.getString(1), rs.getString(2),
                                ""+rs.getString(3)+""};
                    }

                    for (final Object[] row : table) {
                        if (row[1] != (null))
                            System.out.format("%15s%250s%30s%n", row);
                    }


                } else if (input.contains("get activities of")) { // get activities of 'user_name'
                    query = "call get_sb_activities7(" + "\'" + input.split(" ")[3] + "\')";
//                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    final Object[][] table = new String[100][3];
                    int j = 0;

                    table[j++] = new String[]{rs.getMetaData().getColumnName(1), rs.getMetaData().getColumnName(2),
                            rs.getMetaData().getColumnName(3), rs.getMetaData().getColumnName(4)};

                    while (rs.next()) {
                        table[j++] = new String[]{ rs.getString(1), rs.getString(2), rs.getString(3),
                                ""+rs.getDate(4)+""};
                    }

                    for (final Object[] row : table) {
                        if (row[1] != (null))
                            System.out.format("%15s%250s%20s%n", row);
                    }

                } else if (input.contains("get comments of ava")) { // get comments of ava `ava_id`
                    query = "call get_comments_of_ava9(" + input.split(" ")[4] + ")";
                    rs = stmt.executeQuery(query);

                    final Object[][] table = new String[100][5];
                    int j = 0;
                    table[j++] = new String[]{rs.getMetaData().getColumnName(7), rs.getMetaData().getColumnName(8),
                            rs.getMetaData().getColumnName(9), ((rs.getMetaData().getColumnName(10))), rs.getMetaData().getColumnName(11)};
                    while (rs.next()) {
                            table[j++] = new String[]{rs.getString(7), rs.getString(8),
                                    rs.getString(9), ""+(rs.getString(10))+"", rs.getString(11)};
                    }
                    for (final Object[] row : table) {
                        if (row[1] != (null))
                            System.out.format("%15s%15s%250s%20s%30s%n", row);
                    }

                } else if (input.contains("comment") & input.contains("ava")) { // comment 'content' on ava 'ava_id'
                    String[] input_array = input.split(" ");
                    String content = "";
                    for (int i = 1; i < input_array.length - 3; i++) {
                        content = content + input_array[i];
                        if (i != input_array.length - 4)
                            content += " ";
                    }
                    query = "call comment_on_ava2(" + "\"" + content + "\"," + input_array[input_array.length - 1] + ")";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("get ava with")) { // get ava with 'hashtag content'
                    query = "call ava_with_specific_hashtag8(" + "\'" + input.split(" ")[3] + "\')";
//                System.out.println(query);
                    rs = stmt.executeQuery(query);
                    final Object[][] table = new String[100][5];
                    int j = 0;
                    table[j++] = new String[]{rs.getMetaData().getColumnName(1), rs.getMetaData().getColumnName(2),
                            rs.getMetaData().getColumnName(3), rs.getMetaData().getColumnName(4), rs.getMetaData().getColumnName(5)};
                    while (rs.next()) {
                        if (rs.getString(1) != null) {
                            table[j++] = new String[]{rs.getString(1), rs.getString(2),
                                    rs.getString(3),""+ rs.getDate(4)+"", rs.getString(5)};
                        }
                    }
                    for (final Object[] row : table) {
                        if (row[1] != (null))
                            System.out.format("%15s%250s%20s%30s%30s%n", row);
                    }

                } else if (input.contains("dislike ava")) { // dislike ava 'ava_id'
                    query = "call like_ava2(" + input.split(" ")[2] + ",1)";
                    rs = stmt.executeQuery(query);

                } else if (input.contains("like ava")) { // like ava 'ava_id'
                    query = "call like_ava2(" + input.split(" ")[2] + ",0)";
                    rs = stmt.executeQuery(query);

                } else if (input.contains("dislike number")) { // dislike number 'ava_id'
                    query = "call likes_number3(" + input.split(" ")[2] + ", 1)";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    System.out.println(rs.getString(1));

                } else if (input.contains("like number")) { // like number 'ava_id'
                    query = "call likes_number3(" + input.split(" ")[2] + ", 0)";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    System.out.println(rs.getString(1));

                } else if (input.contains("username of everyone liked ava")) { // username of everyone liked ava 'ava_id'
                    query = "call user_name_whom_like1(" + input.split(" ")[5] + ", 0)";
                    rs = stmt.executeQuery(query);
                    System.out.println(rs.getMetaData().getColumnName(1) + " : ");
//                    rs.next();
//                    System.out.println(rs.getString(1));
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }

                } else if (input.contains("username of everyone disliked ava")) { // username of everyone disliked ava 'ava_id'
                    query = "call user_name_whom_like1(" + input.split(" ")[5] + ", 1)";
                    rs = stmt.executeQuery(query);
                    System.out.println(rs.getMetaData().getColumnName(1) + " : ");
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                } else if (input.contains("show ghanari ava")) { // show ghanari ava
                    query = "call ghanari_ava15()";
                    rs = stmt.executeQuery(query);
                    System.out.println(rs.getMetaData().getColumnName(1) + "\t" + rs.getMetaData().getColumnName(2));
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + "\t\t" + rs.getString(2));
                    }
                } else if (input.contains("send message")) { // send message 'type' 'content'/'ava_id' to 'user_name'
                    int type = Integer.parseInt(input.split(" ")[2]);
                    String[] input_array = input.split(" ");
                    String content = "";

                    for (int i = 3; i < input_array.length - 4; i++) {
                        content = content + input_array[i];
                        if (i != input_array.length - 5)
                            content += " ";
                    }
                    content = "\"" + content + "\"";
                    query = "call send_message2(" + type + ", " + content + ", " + input_array[input_array.length - 3] + "," +
                            "\'" + input_array[input_array.length - 1] + "\')";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);

                } else if (input.contains("get message from")) { // get message from 'user_name'
                    query = "call get_message_from4(" + "\'" + input.split(" ")[3] + "\')";
                    rs = stmt.executeQuery(query);
                    final Object[][] table = new String[100][5];
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
                        if (row[1] != (null))
                            System.out.format("%15s%250s%20s%20s%30s%n", row);
                    }

                } else if (input.contains("get senders list")) {
                    query = "call get_senders_list1()";
                    rs = stmt.executeQuery(query);
                    System.out.println(rs.getMetaData().getColumnName(1) + "\t" + rs.getMetaData().getColumnName(2));
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + "\t\t\t" + rs.getString(2));
                    }
                }

            }
//            con.close();

        } catch(Exception e){
            System.out.println(e);
        }
    }
}
