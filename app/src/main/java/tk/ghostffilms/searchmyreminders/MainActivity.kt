package tk.ghostffilms.searchmyreminders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // declare DBHandler as mutable field using null safety
    var dbHandler: DBHandler? = null

    // declare RecyclerView as mutable field using null safety
    var reminderRecyclerView: RecyclerView? = null

    // declare a ReminderAdapter as a mutable field
    // specify that it will be initialized later
    lateinit var reminderAdapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // fully initialize dbHandler
        dbHandler = DBHandler(this, null)

        // make RecyclerView refer to actual RecyclerView in activity_main layout resource
        reminderRecyclerView = findViewById<View>(R.id.reminderRecyclerView) as RecyclerView

        // initialize a MutableList of Reminders
        var reminders: MutableList<Reminder> = ArrayList()

        // initialize the ReminderAdapter
        reminderAdapter = ReminderAdapter(reminders)

        // tell Kotlin that RecyclerView isn't null and set the ReminderAdapter on it
        reminderRecyclerView!!.adapter = reminderAdapter

        // tell Kotlin that the RecylerView isn't null and apply a LinearLayout to it
        reminderRecyclerView!!.layoutManager = LinearLayoutManager(this)

//        addReminder("High Priority Reminder 1", "High", "2021-05-02")
//        addReminder("High Priority Reminder 2", "High", "2021-05-03")
//        addReminder("Medium Priority Reminder 1", "Medium", "2021-05-04")
//        addReminder("Medium Priority Reminder 2", "Medium", "2021-05-05")
//        addReminder("Low Priority Reminder 1", "Low", "2021-05-06")
//        addReminder("Low Priority Reminder 2", "Low", "2021-05-07")
    }

    /**
     * This method populates a reminder in the database.  It gets called when
     * the app launches.
     * @param text reminder text
     * @param priority reminder priority
     * @param date reminder date
     */
    fun addReminder(text: String, priority: String, date: String) {
        dbHandler?.addReminder(text, priority, date)
    }

    /**
     * This method gets called when the Search button is clicked.  It
     * searches for students based on the specified search criteria and
     * refreshes the StudentAdapter with the retrieved data so that it
     * may be displayed in the RecyclerView.
     * @param view MainActivity
     */
    fun searchLow(view: View?){
        try {
            // call search by year in ReminderAdapter
            reminderAdapter.search(dbHandler!!, "priority", "Low")
            // refresh StudentAdapter Mutable List
            reminderAdapter.reminders = dbHandler!!.search("priority", "Low")
        } catch (e: Exception) {
            print(e)
        }
    }
    fun searchMed(view: View?){
        try {
            // call search by year in StudentAdapter
            reminderAdapter.search(dbHandler!!, "priority", "Medium")
            // refresh StudentAdapter Mutable List
            reminderAdapter.reminders = dbHandler!!.search("priority", "Medium")
        } catch (e: Exception) {
            print(e)
        }
    }
    fun searchHigh(view: View?){
        try {
            // call search by year in StudentAdapter
            reminderAdapter.search(dbHandler!!, "priority", "High")
            // refresh StudentAdapter Mutable List
            reminderAdapter.reminders = dbHandler!!.search("priority", "High")
        } catch (e: Exception) {
            print(e)
        }
    }
}