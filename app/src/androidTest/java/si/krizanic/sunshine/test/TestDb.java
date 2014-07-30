package si.krizanic.sunshine.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Test;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import si.krizanic.sunshine.data.WeatherContract;
import si.krizanic.sunshine.data.WeatherDbHelper;

/**
 * Created by Bojan on 30.7.2014.
 */
public class TestDb extends AndroidTestCase {
    private final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {
        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getLocationContentValues();

        long locationRowId;
        locationRowId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, values);

        assertTrue(locationRowId != -1);
        Log.v(LOG_TAG, "New row id: " + locationRowId);

        Cursor cursor = db.query(
                WeatherContract.LocationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            validateCursor(values, cursor);

            ContentValues weatherValues = getWeatherContentValues(locationRowId);
            long weatherRowId;
            weatherRowId = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, weatherValues);
            assertTrue(weatherRowId != -1);
            //Log.v(LOG_TAG, "Weather row id: " + weatherRowId);

            Cursor weatherCursor = db.query(
                    WeatherContract.WeatherEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (weatherCursor.moveToFirst()) {
                validateCursor(weatherValues, weatherCursor);
            }

        } else {
            fail("No values returned.");
        }

        dbHelper.close();
    }

    static public String TEST_CITY_NAME = "Gančani";

    ContentValues getLocationContentValues() {
        ContentValues values = new ContentValues();
        String testLocationSetting = "9231";
        double testLatitude = 64.772;
        double testLongitude = -147.355;
        values.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, TEST_CITY_NAME);
        values.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, testLatitude);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, testLongitude);

        return values;
    }

    ContentValues getWeatherContentValues(long locationRowId) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        values.put(WeatherContract.WeatherEntry.COLUMN_DATETEXT, "20141205");
        values.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, 1.1);
        values.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2);
        values.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3);
        values.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 75);
        values.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 65);
        values.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        values.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        values.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 321);

        return values;
    }

    static public void validateCursor(ContentValues expectedValues, Cursor valueCursor) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for(Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(-1 == idx);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
    }
}
