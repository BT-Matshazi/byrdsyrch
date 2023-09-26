package byrd.syrch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import byrd.syrch.birdData.SightingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

/**
 * A fragment representing a list of Items.
 */
class SightingsList : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var search: SearchView
    private lateinit var dbref : DatabaseReference
    private lateinit var listrecyclerView: RecyclerView
    private lateinit var SightingArrayList: ArrayList<SightingData>
    private lateinit var filteredSightingArrayList: ArrayList<SightingData>
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
        val view = inflater.inflate(R.layout.fragment_sightings_item_list, container, false)

        filteredSightingArrayList = arrayListOf()

        search = view.findViewById(R.id.searchView)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterSightingList(newText)
                return true
            }
        })


        listrecyclerView = view.findViewById(R.id.SightingRecyclerView)
        listrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        listrecyclerView.setHasFixedSize(true)

        SightingArrayList = arrayListOf<SightingData>()

        //getting user id
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        if (userId != null) {
            getSightingList(userId)
        }
        // Set the adapter
        return view
    }

    private fun getSightingList(userId: String) {
        dbref = FirebaseDatabase.getInstance().getReference("sightings")

        val query = dbref.orderByChild("userId").equalTo(userId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                SightingArrayList.clear()

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val listData = userSnapshot.getValue(SightingData::class.java)
                        SightingArrayList.add(listData!!)
                    }
                }
                // Update the filtered list
                filterSightingList(search.query.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

    // TODO: Customize parameter argument names
    const val ARG_COLUMN_COUNT = "column-count"

    // TODO: Customize parameter initialization
    @JvmStatic
    fun newInstance(columnCount: Int) =
        SightingsList().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }

    private fun filterSightingList(query: String) {
        filteredSightingArrayList.clear()

        for (sightingData in SightingArrayList) {
            val categoryName = sightingData.birdName?.toLowerCase(Locale.getDefault())
            if (categoryName != null) {
                if (categoryName.contains(query.toLowerCase(Locale.getDefault()))) {
                    filteredSightingArrayList.add(sightingData)
                }
            }
        }

        listrecyclerView.adapter = SightingsListAdapter(filteredSightingArrayList)
    }
}