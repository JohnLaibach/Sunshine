package si.krizanic.sunshine.data;

import android.provider.BaseColumns;

/**
 * Created by Bojan on 30.7.2014.
 */
public class WeatherContract {
    public static final class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_LOC_KEY = "location_id";
        public static final String COLUMN_DATETEXT = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";

        public static final String COLUMN_SHORT_DESC = "short_desc";

        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        public static final String COLUMN_HUMIDITY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_DEGREES = "degrees";
    }

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";

        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        public static final String COLUMN_CITY_NAME = "city_name";

        public static final String COLUMN_COORD_LAT = "lat";

        public static final String COLUMN_COORD_LONG = "long";

        /*public static final String COLUMN_POSTAL_CODE = "postal_code";

        public static final String COLUMN_LOCATION_NAME = "location_name";*/
    }
}
