/*package ie.wit.birdapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.birdapp.R
import ie.wit.birdapp.adapters.AddBirdAdapter
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.add_bird_fragment.view.*
import kotlinx.android.synthetic.main.collection_fragment.view.*
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import org.jetbrains.anko.toast


*//**
 * A simple [Fragment] subclass.
 * Use the [BirdCollectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *//*
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var app: BirdApp
    lateinit var adapter: AddBirdAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        button_loginpage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLogIn2()
            findNavController().navigate(action)


        }
    }





    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }



}*/

