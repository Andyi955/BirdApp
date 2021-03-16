package ie.wit.birdapp.fragments

import AddBirdAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.birdapp.R
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import ie.wit.birdapp.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.collection_fragment.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [BirdCollectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BirdCollectionFragment : Fragment(){
    lateinit var app: BirdApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.collection_fragment, container, false)
        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        root.recyclerView.adapter = AddBirdAdapter(app.birdStore.findAll() as ArrayList<BirdModel>)


        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as AddBirdAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteBird(viewHolder.itemView.tag as Long)


            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)



        return root
    }




    companion object {
        @JvmStatic
        fun newInstance() =
                BirdCollectionFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

    fun deleteBird(bird1: Long) {
        app.birdStore.delete(bird1)
    }


}





