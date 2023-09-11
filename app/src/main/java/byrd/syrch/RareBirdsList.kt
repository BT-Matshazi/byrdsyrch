package byrd.syrch

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import byrd.syrch.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class RareBirdsList : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rare_birds_list2, container, false)

        val rareList = mutableListOf(
            PlaceholderContent.RareBirds("Northern Wheatear","Oenanthe oenanthe","5 Points") ,
            PlaceholderContent.RareBirds("Egyptian Vulture","Neophron percnopterus","10 Points"),
            PlaceholderContent.RareBirds("Southern Hyliota","Hyliota australis","10 Points"),
            PlaceholderContent.RareBirds("African Hobby","Falco cuvierii","15 Points"),
            PlaceholderContent.RareBirds("Racket-tailed Roller","Coracias spatulatus","15 Points"),
            PlaceholderContent.RareBirds("African Golden Oriole","Oriolus auratus","20 Points")
        )
        // Set the adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.rareList)
        recyclerView.adapter = RareBirdsAdapter(rareList)
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RareBirdsList().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}