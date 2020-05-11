package hu.ait.aitpuritytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val PREF_NAME = "PREFUID"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        var uid = getUID()
        if (uid == "empty"){
            saveUID()
            uid = getUID()
        }

        btnSurveyStart.setOnClickListener {
            var intentDetails = Intent()
            intentDetails.setClass(this, SurveyActivity::class.java)
            intentDetails.putExtra("UID", uid.toString())
            startActivityForResult(intentDetails, 0)
        }
    }

    private fun saveUID() {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //how to edit
        var editor = sharedPref.edit()
        editor.putString(FirebaseAuth.getInstance().currentUser!!.uid, UUID.randomUUID().toString())
        editor.apply()
    }

    fun getUID(): String? {
        var sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var uid = sharedPref.getString(FirebaseAuth.getInstance().currentUser!!.uid, "empty")
        Toast.makeText(this, uid, Toast.LENGTH_LONG).show()
        return uid
    }


    // Toolbar
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}