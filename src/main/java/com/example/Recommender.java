package com.example;

import com.example.command.Recommend;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.AbstractRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.AbstractFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.RatingSGDFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;

/**
 * Created by eduard on 11.05.16.
 */
public class Recommender {
    private static Recommender _instance = null;
    private AbstractRecommender _recommender = null;

    public static Recommender instance()
    {
        if (_instance == null) {
            _instance = new Recommender();
        }
        return _instance;
    }

    private Recommender()
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setDatabaseName("recommender");
        DataModel model = new MySQLJDBCDataModel(new ConnectionPoolDataSource(dataSource));
        try {
            AbstractFactorizer factorizer = new RatingSGDFactorizer(model, 50, 15);
            this._recommender = new SVDRecommender(model, factorizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AbstractRecommender getRecommender()
    {
        return this._recommender;
    }
}
