import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso

import ie.wit.birdapp.R
import ie.wit.birdapp.fragments.AllBirdsFragment
import ie.wit.birdapp.models.BirdModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import java.time.LocalDate

interface BirdListener{
    fun onBirdClick(birdModel: BirdModel)
}

class AddBirdAdapter(options: FirebaseRecyclerOptions<BirdModel>,
                     private val listener: BirdListener?) : FirebaseRecyclerAdapter<BirdModel,
                                                            AddBirdAdapter.MainHolder>(options) {

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(birdcollection: BirdModel, listener: BirdListener) {

            itemView.tag = birdcollection
            itemView.birdname.text = birdcollection.name
            itemView.birdtype.text = birdcollection.type
            itemView.birdrefNo.text = birdcollection.ref.toString()

            if(listener is AllBirdsFragment)
               ; //do nothing
            else
                itemView.setOnClickListener { listener.onBirdClick(birdcollection) }

            if(birdcollection.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)

            if(!birdcollection.profilepic.isEmpty()) {
                Picasso.get().load(birdcollection.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_bird_round)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.collection_layout_cards,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int,model: BirdModel) {
        holder.bind(model,listener!!)
    }



    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }




}
