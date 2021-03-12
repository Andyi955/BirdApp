package ie.wit.birdapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import ie.wit.birdapp.R
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import java.time.LocalDate


class AddBirdAdapter(val birdcollections: List<BirdModel>
): RecyclerView.Adapter<AddBirdAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.collection_layout_cards,
                        parent,
                        false
                )
        )
    }



    override fun getItemCount(): Int = birdcollections.size


     class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(birdcollection: BirdModel) {

            itemView.birdname.text = birdcollection.name
            itemView.birdtype.text = birdcollection.type
            itemView.birdrefNo.text = birdcollection.ref.toString()
            itemView.imageIcon.setImageResource(R.mipmap.ic_bird_round)
        }

    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val birdcollection = birdcollections[holder.adapterPosition]
        holder.bind(birdcollection)
    }
}
