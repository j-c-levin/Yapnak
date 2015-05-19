package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Joshua on 19/05/2015.
 */
@Api(
        name = "sqlApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class SQLServlet{

    @ApiMethod(
            name = "insert",
            path = "sql",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void doPost(@Named("id") String id) throws IOException {
/*
        String url = null;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://your-project-id:your-instance-name/guestbook?user=root";
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://127.0.0.1:3306/guestbook?user=root";

                // Alternatively, connect to a Google Cloud SQL instance using:
                // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
*/

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main",
                    "client", "g7lFVLRzYdJoWXc3");
            try {
                String statement = "INSERT INTO user (userID) VALUES(1122)";
                PreparedStatement stmt = conn.prepareStatement(statement);
/*                    stmt.setString(1, fname);
                    stmt.setString(2, content);*/
                int success = 2;
                success = stmt.executeUpdate();
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
