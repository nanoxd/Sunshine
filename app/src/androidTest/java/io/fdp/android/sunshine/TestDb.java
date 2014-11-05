package io.fdp.android.sunshine;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import io.fdp.android.sunshine.data.WeatherDbHelper;

/**
 * Created by nano on 11/5/14.
 */
public class TestDb extends AndroidTestCase {
    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }
}
