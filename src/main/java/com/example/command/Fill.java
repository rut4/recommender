/**
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Copyright (C) 2016 Oggetto Web ltd (http://oggettoweb.com/), SFU (http://sfedu.ru)
 */

package com.example.command;

import java.sql.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.PreparedStatement;
import java.util.Map;

public class Fill
        implements Command {

    public static final String CODE = "fill";

    protected JSONArray _data;


    @Override
    public Command init(Map<String, String> data) {
        try {
            this._data = new JSONArray(data.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String execute() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setDatabaseName("recommender");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into taste_preferences (item_id, user_id, preference) values (?, ?, ?)");
            for (int i = 0; i < this._data.length(); i++) {
                JSONObject object;
                try {
                    object = this._data.getJSONObject(i);
                } catch (Exception e) {
                    continue;
                }
                stmt.setObject(1, object.get("item_id"));
                stmt.setObject(2, object.get("user_id"));
                stmt.setObject(3, object.get("preference"));
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
