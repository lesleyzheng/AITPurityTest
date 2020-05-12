package hu.ait.aitpuritytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_welcome_page.view.*
import java.util.*

class FragmentWelcome: Fragment() {

    companion object {
        const val TAG = "TAG_Fragment_One"
        const val PREF_NAME = "PREFUID"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_welcome_page, container, false)

        var uid = getUID()
        if (uid == "empty"){
            saveUID()
            uid = getUID()
        }

        rootView.btnSurveyStart.setOnClickListener {
            var intentDetails = Intent()
            intentDetails.setClass(context!!, SurveyActivity::class.java)
            intentDetails.putExtra("UID", uid.toString())
            startActivityForResult(intentDetails, 0)
        }

        return rootView
    }

    private fun saveUID() {
        var sharedPref = activity!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //how to edit
        var editor = sharedPref.edit()
        editor.putString(FirebaseAuth.getInstance().currentUser!!.uid, UUID.randomUUID().toString())
        editor.apply()
    }

    fun getUID(): String? {
        var sharedPref = activity!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var uid = sharedPref.getString(FirebaseAuth.getInstance().currentUser!!.uid, "empty")
        Toast.makeText(context, uid, Toast.LENGTH_LONG).show()
        return uid
    }

}