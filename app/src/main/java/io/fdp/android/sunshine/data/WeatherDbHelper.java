package io.fdp.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.fdp.android.sunshine.data.WeatherContract.WeatherEntry;
import io.fdp.android.sunshine.data.WeatherContract.LocationEntry;

/**
 * Created by nano on 11/4/14.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weather.db";
    private static final String SQL_REAL_NOT_NULL = " REAL NOT NULL, ";
    private static final String SQL_INTEGER_NOT_NULL = " INTEGER NOT NULL, ";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE ";
    private static final String SQL_TEXT_NOT_NULL = " TEXT NOT NULL, ";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_LOCATION_TABLE = SQL_CREATE_TABLE + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY," +
                LocationEntry.COLUMN_LOCATION_SETTING + "TEXT UNIQUE NOT NULL" +
                LocationEntry.COLUMN_CITY_NAME + SQL_TEXT_NOT_NULL +
                LocationEntry.COLUMN_COORD_LAT + SQL_REAL_NOT_NULL +
                LocationEntry.COLUMN_COORD_LONG + SQL_REAL_NOT_NULL +
                "UNIQUE (" + LocationEntry.COLUMN_LOCATION_SETTING + ") ON CONFLICT IGNORE" +
                " );";

        final String SQL_CREATE_WEATHER_TABLE = SQL_CREATE_TABLE + WeatherEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                WeatherEntry.COLUMN_LOC_KEY + SQL_INTEGER_NOT_NULL +
                WeatherEntry.COLUMN_DATETEXT + SQL_TEXT_NOT_NULL +
                WeatherEntry.COLUMN_SHORT_DESC + SQL_TEXT_NOT_NULL +
                WeatherEntry.COLUMN_WEATHER_ID + SQL_INTEGER_NOT_NULL +

                WeatherEntry.COLUMN_MIN_TEMP + SQL_REAL_NOT_NULL +
                WeatherEntry.COLUMN_MAX_TEMP + SQL_REAL_NOT_NULL +

                WeatherEntry.COLUMN_HUMIDITY + SQL_REAL_NOT_NULL +
                WeatherEntry.COLUMN_PRESSURE + SQL_REAL_NOT_NULL +
                WeatherEntry.COLUMN_WIND_SPEED + SQL_REAL_NOT_NULL +
                WeatherEntry.COLUMN_DEGREES + SQL_REAL_NOT_NULL +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + WeatherEntry.COLUMN_DATETEXT + ", " +
                WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
