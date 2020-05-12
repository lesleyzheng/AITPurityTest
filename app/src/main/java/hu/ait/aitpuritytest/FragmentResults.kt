package hu.ait.aitpuritytest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.aitpuritytest.data.Score
import kotlinx.android.synthetic.main.fragment_results_page.*

class FragmentResults: Fragment() {

    companion object {
        const val TAG = "TAG_Fragment_Two"
    }
    private var tableData = mutableListOf(0,0,0,0,0,0,0,0,0,0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_results_page, container, false)

        calcTable()
        return rootView
    }

    private fun displayChart() {
        var entries = createEntries()
        var dataset = BarDataSet(entries, "Scores")
        var labels = ArrayList<String>()

        labels.add("0")
        labels.add("10")
        labels.add("20")
        labels.add("30")
        labels.add("40")
        labels.add("50")
        labels.add("60")
        labels.add("70")
        labels.add("80")
        labels.add("90")

        var data = BarData(labels, dataset)
        chart.data = data
        chart.setDescription("All Scores")
        chart.invalidate()
    }

    private fun calcTable() {
        var postsCollection = FirebaseFirestore.getInstance().collection(
            "results")

        postsCollection.get()
            .addOnSuccessListener { documents ->
                Log.e("query", "success")
                for (document in documents) {
                    var score = document.toObject(Score::class.java).score
                    var index = score/10
                    if (index == 10) {
                        index = 9
                    }
                    tableData[index] = tableData[index] + 1
                }
                displayChart()
            }
            .addOnFailureListener { exception ->
                Log.e("query", "failure")
            }
    }

    private fun createEntries(): List<BarEntry> {
        var entries = mutableListOf<BarEntry>()
        for (i in 0..9){
            entries.add(BarEntry(tableData[i].toFloat(), i))
        }
        return entries
    }
}