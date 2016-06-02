/**
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Copyright (C) 2016 Oggetto Web ltd (http://oggettoweb.com/), SFU (http://sfedu.ru)
 */

package com.example.command;

import com.example.Recommender;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.codehaus.jettison.json.JSONObject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class Clear
        implements Command {

    public static final String CODE = "clear";

    @Override
    public Command init(Map<String, String> data) {
        return this;
    }

    @Override
    public String execute() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName("127.0.0.1");
            dataSource.setUser("root");
            dataSource.setPassword("root");
            dataSource.setDatabaseName("recommender");
            JSONObject result = new JSONObject();
            String message;
            try {
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("TRUNCATE taste_preferences");
                stmt.close();
                connection.close();
                message = "success";
            } catch (Exception $e) {
                message = $e.getMessage();
            }
            result.put("result", message);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
