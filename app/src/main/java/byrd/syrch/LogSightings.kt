package byrd.syrch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.*
import java.util.*
import android.widget.Toast
import byrd.syrch.birdData.BirdEntry
import byrd.syrch.birdData.SightingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.collections.Map

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogSightings.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogSightings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    var reference: StorageReference = FirebaseStorage.getInstance().getReference()

    lateinit var birdName: EditText
    lateinit var sightingDate: EditText
    lateinit var sightingLocation: EditText
    lateinit var addSighting: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_sightings, container, false)

        birdName = view.findViewById(R.id.birdName)
        sightingDate = view.findViewById(R.id.sightingDate)
        sightingLocation = view.findViewById(R.id.sightingLocation)
        addSighting = view.findViewById(R.id.addSighting)

        addSighting.setOnClickListener {
            // Handle the onclick action here
            val birdNameText = birdName.text.toString()
            val sightingDateText = sightingDate.text.toString()
            val sightingLocationText = sightingLocation.text.toString()

            // Do something with the input values
            val birdEntry = BirdEntry(userId, auth.currentUser?.email, sightingDateText, birdNameText, sightingLocationText)

            // Get the database reference
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("sightings")

            if (birdNameText == "" || sightingDateText == ""|| sightingLocationText == ""){
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                myRef.push().setValue(birdEntry)
                Toast.makeText(requireContext(), "Posted", Toast.LENGTH_SHORT).show()
                // Clear the fields after processing
                clearFields()
            }
        }
        return view
    }

    companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogSightings.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
        LogSightings().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

//    ****************************************************************************************

    fun clearFields(){
        birdName.setText("")
        sightingDate.setText("")
        sightingLocation.setText("")
    }

}