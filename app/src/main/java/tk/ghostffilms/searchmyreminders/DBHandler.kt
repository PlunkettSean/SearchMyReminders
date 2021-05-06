package tk.ghostffilms.searchmyreminders

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?, cursorFactory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION){

    /**
     * Creates database table
     * @param db reference to the myRemindersSearchApp database
     */
    override fun onCreate(db: SQLiteDatabase) {
        // define create statement for studnet table
        val query = "CREATE TABLE " + TABLE_REMINDER + "(" +
                COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REMINDER_TEXT + " TEXT, " +
                COLUMN_REMINDER_PRIORITY + " TEXT, " +
                COLUMN_REMINDER_DATE + " TEXT);"

        // execute create statement
        db.execSQL(query)

        // populate the reminder table in the database
        // these lines of code should be commented out after the
        // app is run the first time
    }

    /**
     * Creates a new version of the database.
     * @param db reference to myRemindersSearchApp database
     * @param oldVersion old version of the database
     * @param newVersion new version of the database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // define drop statement for the reminder table
        val query = "DROP TABLE IF EXISTS $TABLE_REMINDER"

        // execute the drop statement
        db.execSQL(query)

        // call method that creates the table
        onCreate(db)
    }

    /**
     * This method gets called by the MainActivity when the app launches.
     * It inserts a new row in the student table.
     * @param text reminder text
     * @param priority reminder priority
     * @param date reminder date
     */
    fun addReminder(text: String?, priority: String?, date: String?){

        // get reference to myRemindersSearchApp database
        val db = writableDatabase

        // initialize a ContentValues object
        val values = ContentValues()

        // put data into the ContentValues object
        values.put(COLUMN_REMINDER_TEXT, text)
        values.put(COLUMN_REMINDER_PRIORITY, priority)
        values.put(COLUMN_REMINDER_DATE, date)

        // insert data in ContentValues object into student table
        db.insert(TABLE_REMINDER, null, values)

        // close database connection
        db.close()
    }

    /**
     * This method gets called by the search method in the StudentAdapter.
     * It will select the students from the database that satisfy
     * the specified search criteria.
     */
    fun search(key: String, value: String?) : MutableList<Reminder> {
        // get a reference to the myRemindersSearchApp database
        val db = writableDatabase

        // initialize query String
        var query = ""
        // define select statement and store it in query String
        query = "SELECT * FROM " + TABLE_REMINDER +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'" + value + "'"

        // execute the select statement and store its return in an
        // immutable Cursor
        val c = db.rawQuery(query, null)

        // create MutableList of Students that will be
        // returned by the method
        val list: MutableList<Reminder> = ArrayList()

        // loop through the rows in the Cursor
        while (c.moveToNext()) {
            // create an immutable Reminder using the data in the current
            // row in the Cursor
            val reminder: Reminder = Reminder(c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex("text")),
                c.getString(c.getColumnIndex("priority")),
                c.getString(c.getColumnIndex("date")));
            // add the Reminder that was just created into the MutableList
            // of Reminders
            list.add(reminder)
        }

        // close database reference
        db.close()

        // return the MutableList of Reminders
        return list
    }

    companion object {
        // initialize constants for the DB name and version
        private const val DATABASE_NAME = "myRemindersSearchApp.db"
        private const val DATABASE_VERSION = 2

        // initialize constants for the reminder table
        private const val TABLE_REMINDER = "reminder"
        private const val COLUMN_REMINDER_ID = "_id"
        private const val COLUMN_REMINDER_TEXT = "text"
        private const val COLUMN_REMINDER_PRIORITY = "priority"
        private const val COLUMN_REMINDER_DATE = "date"
    }
}