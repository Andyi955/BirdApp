import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import ie.wit.birdapp.R
import ie.wit.birdapp.models.BirdModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import java.time.LocalDate

interface BirdListener{
    fun onBirdClick(birdModel: BirdModel)
}

class AddBirdAdapter(val birdcollections: ArrayList<BirdModel>,
                     private val listener: BirdListener, birdall : Boolean
): RecyclerView.Adapter<AddBirdAdapter.MainHolder>() {

    val birdAll = birdall

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.collection_layout_cards,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val birdcollection = birdcollections[holder.adapterPosition]
        holder.bind(birdcollection,listener,birdAll)
    }



    override fun getItemCount(): Int = birdcollections.size

    fun removeAt(position: Int){
        birdcollections.removeAt(position)
        notifyItemRemoved(position)
    }


    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(birdcollection: BirdModel, listener: BirdListener,birdAll: Boolean) {

            itemView.tag = birdcollection
            itemView.birdname.text = birdcollection.name
            itemView.birdtype.text = birdcollection.type
            itemView.birdrefNo.text = birdcollection.ref.toString()
            if(birdcollection.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)

            if(!birdAll)
            itemView.setOnClickListener { listener.onBirdClick(birdcollection) }
            if(birdcollection.profilepic.isNotEmpty()) {
                Picasso.get().load(birdcollection.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_bird_round)
        }

    }


}