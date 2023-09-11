package byrd.syrch

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import byrd.syrch.databinding.FragmentRareBirdsListBinding

import byrd.syrch.placeholder.PlaceholderContent.RareBirds
import byrd.syrch.placeholder.PlaceholderContent

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RareBirdsAdapter(
    private val values: List<RareBirds>
) : RecyclerView.Adapter<RareBirdsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRareBirdsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.birdName.text = item.birdName
        holder.species.text = item.species
        holder.points.text = item.points
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentRareBirdsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val birdName:TextView = binding.birdName
        val species:TextView = binding.speciesName
        val points: TextView = binding.points

        override fun toString(): String {
            return super.toString() + " '" + birdName.text + "'"
        }
    }

}