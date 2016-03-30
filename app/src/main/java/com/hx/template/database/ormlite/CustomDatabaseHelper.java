package com.hx.template.database.ormlite;

import android.content.Context;

import com.hx.template.entity.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.sqlcipher.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class CustomDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "template.db";

    private static final int DATABASE_VERSION = 1;

    public CustomDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(net.sqlcipher.database.SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(net.sqlcipher.database.SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        return getDao(User.class);
    }

}
