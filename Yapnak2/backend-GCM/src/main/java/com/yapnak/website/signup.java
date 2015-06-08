package com.yapnak.website;

import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Joshua on 08/06/2015.
 */
public class signup extends HttpServlet {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    String url = null;
    Connection connection = null;
    String sql = null;

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String SALT = "Y3aQcfpTiUUdpSAY";

    // A password hashing method.
    public static String hashPassword(String in) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");
            md.update(SALT.getBytes());        // <-- Prepend SALT.
            md.update(in.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);            // <-- Return the Hex Hash.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Finish signup
        String user = req.getParameter("user");
        PrintWriter out = resp.getWriter();
        if (user != null) {
            try {
                if (SystemProperty.environment.value() ==
                        SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
                } else {
                    // Local MySQL instance to use during development.
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
                }
                sql = "SELECT email, password FROM signup WHERE hash = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, user);
                ResultSet rs = stmt.executeQuery();
                //check if user has signed up
                if (rs.next()) {
                    sql = "INSERT INTO client (clientUsername, password) VALUES (?,?)";
                    stmt = connection.prepareStatement(sql);
                    stmt.setString(1, rs.getString("email"));
                    stmt.setString(2, rs.getString("password"));
                    int success = 2;
                    success = stmt.executeUpdate();
                    if (success == 1) {
                        out.println("You're signed up!  Redirecting you to login.");
                        resp.setHeader("Refresh", "3; url=/index.jsp");
                    } else {
                        out.println("Huh, something went wrong.  We'll look into it.");
                        resp.setHeader("Refresh", "3; url=/index.jsp");
                    }
                }
                //redirect if user hasn't signed up
                else {
                    out.println("You don't appear to have signed up, go ahead and join");
                    resp.setHeader("Refresh", "3; url=/index.jsp");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //redirect if user hasn't signed up
        else {
            out.println("You don't appear to have signed up, go ahead and join");
            resp.setHeader("Refresh", "3; url=/index.jsp");
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Sign up user
        PrintWriter out = resp.getWriter();
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            try {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                sql = "SELECT email from signup where email = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    out.println("It looks like you've already signed up, we'll go ahead and resend confirmation");
                    Properties props = new Properties();
                    Session session = Session.getDefaultInstance(props, null);
                    String link = "https://yapnak-app.appspot.com/signup?user=" + hashPassword(email);
                    //TODO:what happens if they didn't request the email?
                    String msgBody = "Hey there!\n\nYou've asked to signup to Yapnak, a startup connecting hungry people with quality lunchtime deals." +
                            "\n\nTo activate your account, please click the link: \n\n" + link + "\n\n\n***Don't reply to this e-mail***";
                    //TODO:integrate help support?  set it so that e-mails are forwarded to the yapnak account?
                    try {
                        Message msg = new MimeMessage(session);
                        msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                        msg.addRecipient(Message.RecipientType.TO,
                                new InternetAddress(email));
                        msg.setSubject("Activate your Yapnak account");
                        msg.setText(msgBody);
                        Transport.send(msg);
                        resp.setHeader("Refresh", "3; url=/index.jsp");
                    } catch (AddressException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }else {
                    sql = "INSERT INTO signup (email, hash, password) VALUES (?,?,?)";
                    stmt = connection.prepareStatement(sql);
                    stmt.setString(1, email);
                    stmt.setString(2, hashPassword(email));
                    stmt.setString(3, hashPassword(password));
                    int success = 2;
                    success = stmt.executeUpdate();
                    if (success == 1) {
                        Properties props = new Properties();
                        Session session = Session.getDefaultInstance(props, null);
                        String link = "https://yapnak-app.appspot.com/signup?user=" + hashPassword(email);
                        //TODO:what happens if they didn't request the email?
                        String msgBody = "Hey there!\n\nYou've asked to signup to Yapnak, a startup connecting hungry people with quality lunchtime deals." +
                                "\n\nTo activate your account, please click the link: \n\n" + link + "\n\n\n***Don't reply to this e-mail***";
                        //TODO:integrate help support?  set it so that e-mails are forwarded to the yapnak account?
                        try {
                            Message msg = new MimeMessage(session);
                            msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                            msg.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(email));
                            msg.setSubject("Activate your Yapnak account");
                            msg.setText(msgBody);
                            Transport.send(msg);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        out.println("Check your e-mail for confirmation");
                        resp.setHeader("Refresh", "3; url=/index.jsp");
                    } else {
                        out.println("Huh, something didn't work");
                        resp.setHeader("Refresh", "3; url=/index.jsp");
                    }
                }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}