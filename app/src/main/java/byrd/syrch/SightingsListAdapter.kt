package byrd.syrch

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import byrd.syrch.birdData.SightingData
import byrd.syrch.databinding.FragmentSightingsItemListBinding
import byrd.syrch.placeholder.PlaceholderContent

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SightingsListAdapter(private val values: ArrayList<SightingData>) : RecyclerView.Adapter<SightingsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sightings_item, parent, false)
        return ViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = values[position]

        holder.birdName.text = item.birdName
        holder.sightingDate.text = item.dateAdded
        holder.sightingLocation.text = item.location
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val birdName: TextView = itemView.findViewById(R.id.birdName)
        val sightingDate: TextView = itemView.findViewById(R.id.sightingDate)
        val sightingLocation: TextView = itemView.findViewById(R.id.sightingLocation)

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }
    }

}