package ie.wit.birdapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.birdapp.R
import ie.wit.birdapp.adapters.AddBirdAdapter
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.add_bird_fragment.view.*
import kotlinx.android.synthetic.main.collection_fragment.*
import kotlinx.android.synthetic.main.collection_fragment.view.*
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult


/**
 * A simple [Fragment] subclass.
 * Use the [BirdCollectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BirdCollectionFragment : Fragment(){
    lateinit var app: BirdApp
    lateinit var adapter: AddBirdAdapter


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

        root.recyclerView.adapter = AddBirdAdapter(app.birdStore.findAll())





        return root
    }




    companion object {
        @JvmStatic
        fun newInstance() =
                BirdCollectionFragment().apply {
                    arguments = Bundle().apply {}
                }
    }




}





