package project.dbm0204.org.meditimer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import project.dbm0204.org.meditimer.AlarmContract.*;

/**
 * Created by bmathew2014 on 6/17/15
 *
 * Helps with the creation of the Alarm Table, and insertion, deletion, and update statements.
 */
public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "alarm.db";

    public static final String SQL_CREATE_ALARM =
            "CREATE Table " + Alarm.TABLE_NAME + " (" +
                    Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Alarm.COLUMN_NAME_PATIENT_NAME + " TEXT, " +
                    Alarm.COLUMN_NAME_MEDICINE_NAME + " TEXT, " +
                    Alarm.COLUMN_NAME_DOSAGE + " TEXT, " +
                    Alarm.COLUMN_NAME_INSTRUCTIONS + " TEXT, " +
                    Alarm.COLUMN_NAME_HOUR + " INTEGER, " +
                    Alarm.COLUMN_NAME_MINUTES + " INTEGER, " +
                    Alarm.COLUMN_NAME_REPEAT + " INTEGER, " +
                    Alarm.COLUMN_NAME_RINGTONE + " TEXT, " +
                    Alarm.COLUMN_NAME_ISENABLED + " BOOLEAN ) ";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;

    private static final String SQL_DELETE_ALL_ROWS =
            "DELETE FROM " + Alarm.TABLE_NAME;


    /**
     * Constuctor that builds the helper to create, open and manage the Alarm Database.
     * @param context   Context Object
     */
    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Assigns what to do when creating the database.
     * @param db    The SQLite Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
    }

    /**
     * Directs what actions are implemented when updating the database.
     * @param db            The SQLite Database
     * @param oldVersion    The old version of the database
     * @param newVersion    The new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Uncomment below to delete all rows and start a fresh database.  Need to change version#.
        //db.execSQL(SQL_DELETE_ALL_ROWS);
        db.execSQL(SQL_DELETE_ALARM);
        this.onCreate(db);
    }

    /**
     * Creates a ModelAlarm object and uses a cursor to populate the object.
     * Helper for CRUD methods
     * @param c The Cursor
     * @return  A ModelAlarm Object
     */
    private ModelAlarm populateModel(Cursor c) {
        //Retrieving info from each column and storing it in variables
        long id = c.getLong(c.getColumnIndex(Alarm._ID));
        String patient = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_PATIENT_NAME));
        String medicine = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_MEDICINE_NAME));
        String dosage = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_DOSAGE));
        String instructions = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_INSTRUCTIONS));
        int hour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_HOUR));
        int minutes = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_MINUTES));
       // long startDate = c.getLong(c.getColumnIndex(Alarm.COLUMN_NAME_START_DATE));
       // long endDate = c.getLong(c.getColumnIndex(Alarm.COLUMN_NAME_END_DATE));
        int repeat = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_REPEAT));
        //Tracey made change here
        Uri ringtone = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_RINGTONE)) != "" ? Uri.parse(c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_RINGTONE))) : null;


        boolean isEnabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ISENABLED)) != 0;
        //Populating ModelAlarm from variables
        ModelAlarm alarm = new ModelAlarm();
        alarm.setID(id);
        alarm.setPatient(patient);
        alarm.setMedicine(medicine);
        alarm.setDosage(dosage);
        alarm.setInstructions(instructions);
        alarm.setAlarmHour(hour);
        alarm.setAlarmMinutes(minutes);
       // alarm.setStartDate(startDate);
       // alarm.setEndDate(endDate);
        alarm.setRingtone(ringtone);
        alarm.setRepeat(repeat);
        alarm.setEnabled(isEnabled);
        return alarm;
    }

    //private java.sql.Date convertStringToDBDate(String sDate) throws ParseException {
        //SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //Calendar pDate = Calendar.parse("20110210");
        //java.sql.Date sqlDate = new java.sql.Date(pDate.getDate());
        //return sqlDate;
    //}

    /**
     * Returns a collection of values contained in an alarm which will be used ot populate the model.
     * Helper for CRUD methods
     * @param alarm The ModelAlarm
     * @return      The values of the ModelAlarm
     */
    private ContentValues populateContent(ModelAlarm alarm) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_PATIENT_NAME, alarm.getPatient());
        values.put(Alarm.COLUMN_NAME_MEDICINE_NAME, alarm.getMedicine());
        values.put(Alarm.COLUMN_NAME_DOSAGE, alarm.getDosage());
        values.put(Alarm.COLUMN_NAME_INSTRUCTIONS, alarm.getInstructions());
        values.put(Alarm.COLUMN_NAME_HOUR, alarm.getHours());
        values.put(Alarm.COLUMN_NAME_MINUTES, alarm.getMinutes());
      //  values.put(Alarm.COLUMN_NAME_START_DATE, alarm.getStartDate());
      //  values.put(Alarm.COLUMN_NAME_END_DATE, alarm.getEndDate());
        values.put(Alarm.COLUMN_NAME_RINGTONE, alarm.getRingtone() != null ?  alarm.getRingtone().toString() : ""); //Tracey Made change here

        values.put(Alarm.COLUMN_NAME_REPEAT, alarm.getRepeat());
        values.put(Alarm.COLUMN_NAME_ISENABLED, alarm.getIsEnabled());
        return values;
    }

    /**
     * Returns a statement to create a read/write database and insert an alarm into the alarm table
     * @param alarm The ModelAlarm object
     * @return read/write  to insert an alarm into the Alarm table with the attributes of
     * the ModelAlarm
     */
    public long createAlarm(ModelAlarm alarm) {
        ContentValues values = populateContent(alarm);
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
    }
    /**
     * Returns all the ModelAlarm objects
     *
     * @return      All ModelAlarms if any exist or null
     */
    public List<ModelAlarm> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Alarm.TABLE_NAME ;

        Cursor c = db.rawQuery(query, null);
        List<ModelAlarm> alarmList = new ArrayList<ModelAlarm>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }
        return null;
    }


    /**
     * Returns a ModelAlarm object with the matching id
     * @param id    The ID
     * @return      The ModelAlarm if one exists or null
     */
    public ModelAlarm getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE "
                + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }
        return null;
    }

    /**
     * Returns a statement to create a read/write database and statement to delete from the
     * alarm table with the matching ID.
     * @param id    The ID
     * @return      The statement that creates the RW database and delete statement.
     */
    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME,
                Alarm._ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**
     * Returns a statement to create a read/write database and statement to update an
     * alarm in the alarm table.
     * @param alarm   The ModelAlarm
     * @return      The statement that creates the RW database and update statement.
     */
    public long updateAlarm(ModelAlarm alarm) {
        ContentValues values = populateContent(alarm);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values,
                Alarm._ID + " = ?", new String[] {String.valueOf(alarm.getID())});
    }
}
