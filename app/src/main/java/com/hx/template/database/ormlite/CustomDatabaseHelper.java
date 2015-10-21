package com.hx.template.database.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hx.template.entity.User;
import com.hx.template.utils.LogUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

public class CustomDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "template.db";

	private static final int DATABASE_VERSION = 1;

	private Context mContext;

	public CustomDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTableIfNotExists(arg1, User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(arg1, User.class, true);
			onCreate(arg0, arg1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		if (mContext != null) {
			File f = mContext.getDatabasePath(DATABASE_NAME);
			if (f.exists()) {
				mContext.deleteDatabase(DATABASE_NAME);
				LogUtils.e("DB", "---delete SDCard DB---");
				f.delete();
			} else {
				LogUtils.e("DB", "---delete App DB---");
				mContext.deleteDatabase(DATABASE_NAME);
			}
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
