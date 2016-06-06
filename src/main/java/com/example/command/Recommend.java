/**
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Copyright (C) 2016 Oggetto Web ltd (http://oggettoweb.com/), SFU (http://sfedu.ru)
 */

package com.example.command;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.*;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommend
        implements Command {

    public static final String CODE = "recommend";

    protected Map<String, String> _data;

    @Override
    public Command init(Map<String, String> data) {
        this._data = data;
        return this;
    }

    @Override
    public String execute()
    {
        try {
            Recommender recommender = com.example.Recommender.instance().getRecommender();
            JSONObject result = new JSONObject();

            if (this._data.get("user_id") == null) {
                result.put("result", "");
                return result.toString();
            }
            Long userId = Long.decode(this._data.get("user_id"));
            List<RecommendedItem> recommendations = recommender.recommend(userId, 4);

            Map<Long, Float> mapResult = new HashMap<>();
            for (RecommendedItem recommendation : recommendations) {
                mapResult.put(recommendation.getItemID(), recommendation.getValue());
            }
            result.put("result", mapResult);
            return result.toString();
        } catch (NoSuchUserException e) {
            return (new JSONObject()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
