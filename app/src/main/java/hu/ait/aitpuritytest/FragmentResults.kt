package hu.ait.aitpuritytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentResults: Fragment() {

    companion object {
        const val TAG = "TAG_Fragment_Two"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            inflater.inflate(R.layout.fragment_results_page, container, false)
        return rootView
    }
}