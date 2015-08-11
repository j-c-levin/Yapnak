package com.yapnak.website;

import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Joshua on 08/06/2015.
 */
public class login extends HttpServlet {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

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

    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = null;
        Connection connection = null;
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

        Cookie[] cookies = req.getCookies();
        //work out which cookie holds what
        String email = null;
        String hash = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("com.yapnak.email")) {
                out.println("cookies found");
                email = cookies[i].getValue();
            } else if (cookies[i].getName().equals("com.yapnak.hash")) {
                hash = cookies[i].getValue();
            }
        }
        //change to &&?
        if (email != null || hash != null) {
            out.println("email: " + email + " hash: " + hash);
            try {
                String sql = "SELECT salt,password FROM client WHERE email = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = null;
                rs = stmt.executeQuery();
                //if the salt exists
                if (rs.next()) {
                    String x = email + rs.getString("password") + rs.getString("salt");
                    if (hash.equals(hashPassword(x))) {
                        //login
                        out.println("Login with cookies successful!");
                        HttpSession session = req.getSession();
                        session.setAttribute("email", email);
                        resp.setHeader("Refresh", "0; url=/client.jsp");
                    } else {
                        //go through normal authentication
                    }
                } else {
                    //go through normal authentication
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                try {
                    email = req.getParameter("username");
                    String password = req.getParameter("password");
                    if (email == "" || password == "") {
                        out.println(
                                "<html><head></head><body>You are missing either a message or a name! Try again! " +
                                        "Redirecting in 3 seconds...</body></html>");
                    } else {
                        String sql = "SELECT email, password FROM client WHERE email = ?";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setString(1, email);
                        ResultSet rs = null;
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            if (hashPassword(password).equals(rs.getString("password"))) {
                                out.println("Login success!");
                                //Save a user's session in a cookie if they've requested it
                                if (req.getParameter("save") != null) {
                                    //get unique salt for client
                                    sql = "SELECT salt FROM client WHERE email = ?";
                                    stmt = connection.prepareStatement(sql);
                                    stmt.setString(1, email);
                                    rs = stmt.executeQuery();
                                    rs.next();
                                    out.println(rs.getString("salt"));
                                    //Check if a salt exists
                                    if (rs.getString("salt") == null) {
                                        out.println("No salt exists, creating");
                                        sql = "UPDATE client SET salt = ? WHERE email = ?";
                                        stmt = connection.prepareStatement(sql);
                                        stmt.setString(1, nextSessionId());
                                        stmt.setString(2, email);
                                        int success = 2;
                                        success = stmt.executeUpdate();
                                        if (success == 1) {
                                            out.println("salt created");
                                            sql = "SELECT salt FROM client WHERE email = ?";
                                            stmt = connection.prepareStatement(sql);
                                            stmt.setString(1, email);
                                            rs = null;
                                            rs = stmt.executeQuery();
                                            rs.next();
                                        } else {
                                            out.println("trouble creating the salt");
                                        }
                                    }
                                    //Use created salt
                                    Cookie part1 = new Cookie("com.yapnak.email", email);
                                    String x = email + hashPassword(password) + rs.getString("salt");
                                    Cookie part2 = new Cookie("com.yapnak.hash", hashPassword(x));
                                    int time = 60 * 60 * 24 * 7;
                                    part1.setMaxAge(time);
                                    part2.setMaxAge(time);
                                    resp.addCookie(part1);
                                    resp.addCookie(part2);
                                    out.println("cookies added");
                                }
                                //TODO: Add the client page
                                HttpSession session = req.getSession();
                                session.setAttribute("email", email);
                                resp.setHeader("Refresh", "0; url=/client.jsp");
                            } else {
                                out.println("Incorrect login.");
                                resp.setHeader("Refresh", "3; url=/index.jsp");
                            }
                        } else {
                            out.println("LOGIN FAILED!!!");
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = null;
        Connection connection = null;
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

        Cookie[] cookies = req.getCookies();
        //work out which cookie holds what
        String email = null;
        String hash = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("com.yapnak.email")) {
                out.println("cookies found");
                email = cookies[i].getValue();
            } else if (cookies[i].getName().equals("com.yapnak.hash")) {
                hash = cookies[i].getValue();
            }
        }
        if (email != null || hash != null) {
            out.println("email: " + email + " hash: " + hash);
            try {
                String sql = "SELECT salt,password FROM client WHERE email = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = null;
                rs = stmt.executeQuery();
                //if the salt exists
                if (rs.next()) {
                    String x = email + rs.getString("password") + rs.getString("salt");
                    if (hash.equals(hashPassword(x))) {
                        //login
                        out.println("Login with cookies successful!");
                        HttpSession session = req.getSession();
                        session.setAttribute("email", email);
                        resp.setHeader("Refresh", "0; url=/client.jsp");
                    } else {
                        //go through normal authentication
                    }
                } else {
                    //go through normal authentication
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                try {
                    email = req.getParameter("username");
                    String password = req.getParameter("password");
                    if (email == "" || password == "") {
                        out.println(
                                "<html><head></head><body>You are missing either a message or a name! Try again! " +
                                        "Redirecting in 3 seconds...</body></html>");
                    } else {
                        String sql = "SELECT email, password, clientID FROM client WHERE email = ?";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setString(1, email);
                        ResultSet rs = null;
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            if (hashPassword(password).equals(rs.getString("password"))) {
                                out.println("Login success!");
                                //Save a user's session in a cookie if they've requested it
                                if (req.getParameter("save") != null) {
                                    //get unique salt for client
                                    sql = "SELECT salt FROM client WHERE email = ?";
                                    stmt = connection.prepareStatement(sql);
                                    stmt.setString(1, email);
                                    rs = stmt.executeQuery();
                                    rs.next();
                                    out.println(rs.getString("salt"));
                                    //Check if a salt exists
                                    if (rs.getString("salt") == null) {
                                        out.println("No salt exists, creating");
                                        sql = "UPDATE client SET salt = ? WHERE email = ?";
                                        stmt = connection.prepareStatement(sql);
                                        stmt.setString(1, nextSessionId());
                                        stmt.setString(2, email);
                                        int success = 2;
                                        success = stmt.executeUpdate();
                                        if (success == 1) {
                                            out.println("salt created");
                                            sql = "SELECT salt FROM client WHERE email = ?";
                                            stmt = connection.prepareStatement(sql);
                                            stmt.setString(1, email);
                                            rs = null;
                                            rs = stmt.executeQuery();
                                            rs.next();
                                        } else {
                                            out.println("trouble creating the salt");
                                        }
                                    }
                                    //Use created salt
                                    Cookie part1 = new Cookie("com.yapnak.email", email);
                                    String x = email + hashPassword(password) + rs.getString("salt");
                                    Cookie part2 = new Cookie("com.yapnak.hash", hashPassword(x));
                                    int time = 60 * 60 * 24 * 7;
                                    part1.setMaxAge(time);
                                    part2.setMaxAge(time);
                                    resp.addCookie(part1);
                                    resp.addCookie(part2);
                                    out.println("cookies added");
                                }
                                //TODO: Add the client page
                                HttpSession session = req.getSession();
                                session.setAttribute("email", email);
                                resp.setHeader("Refresh", "0; url=/console");
                            } else {
                                out.println("Incorrect login.");
                                resp.setHeader("Refresh", "1; url=/client");
                            }
                        } else {
                            out.println("LOGIN FAILED!!!");
                            resp.setHeader("Refresh", "1; url=/client");
                        }
                    }
                } finally {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        resp.setHeader("Refresh", "3; url=/index.jsp");
        }
    }
}