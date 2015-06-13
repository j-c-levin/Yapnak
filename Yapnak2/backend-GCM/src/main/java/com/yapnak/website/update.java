package com.yapnak.website;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class update extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = null;
        Connection connection = null;
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
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
                String name = req.getParameter("name");
                if (name.equals("")) {
                    name = (String) session.getAttribute("name");
                }
                String type = req.getParameter("type");
                if (type.equals("")) {
                    type = (String) session.getAttribute("type");
                }
//                String address = req.getParameter("address");
                String deal = req.getParameter("deal");
                if (deal.equals("")) {
                    deal = (String) session.getAttribute("deal");
                }
                String logo = (String) session.getAttribute("image");
                Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);

                List<BlobKey> blobKeys = blobs.get("image");
                if (blobKeys == null || blobKeys.isEmpty()) {
                    //do nothing
                }
                else {
                    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
                    blobstoreService.delete(new BlobKey(logo));
                    logo = blobKeys.get(0).getKeyString();
                }
                out.print(name + " " + type + " " + deal + " ");
                    String sql = "UPDATE client SET clientName = ?, clientFoodStyle = ?, clientOffer = ?, clientPhoto = ? WHERE email = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setString(2, type);
                    stmt.setString(3, deal);
                    stmt.setString(4, logo);
                    stmt.setString(5, (String) session.getAttribute("email"));
                    int success = 2;
                    success = stmt.executeUpdate();
                    if (success == 1) {
                        //success
                        out.print("successfully updated " + logo);
                        resp.setHeader("Refresh", "0; url=/client.jsp");
                    } else {
                        out.print("failed to update");
                        resp.setHeader("Refresh", "0; url=/client.jsp");
                    }
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
