import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import ie.wit.birdapp.R
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import java.time.LocalDate

interface BirdListener{
    fun onBirdClick(birdModel: BirdModel)
}

class AddBirdAdapter(val birdcollections: ArrayList<BirdModel>,
                     private val listener: BirdListener
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

    fun removeAt(position: Int){
        birdcollections.removeAt(position)
        notifyItemRemoved(position)
    }


    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(birdcollection: BirdModel, listener: BirdListener) {

            itemView.tag = birdcollection
            itemView.birdname.text = birdcollection.name
            itemView.birdtype.text = birdcollection.type
            itemView.birdrefNo.text = birdcollection.ref.toString()
            itemView.imageIcon.setImageResource(R.mipmap.ic_bird_round)
            itemView.setOnClickListener { listener.onBirdClick(birdcollection) }
        }

    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val birdcollection = birdcollections[holder.adapterPosition]
        holder.bind(birdcollection,listener)
    }
}