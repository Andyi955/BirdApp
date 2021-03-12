/*
package ie.wit.birdapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.birdapp.R
import ie.wit.birdapp.adapters.AddBirdAdapter
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.collection_fragment.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.logged_in.*


*/
/**
 * A simple [Fragment] subclass.
 * Use the [LoggedInFragment.newInstance] factory method to
 * create an instance of this fragment.
 *//*

class LoggedInFragment : Fragment(R.layout.logged_in) {


    lateinit var app: BirdApp




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        button_ok.setOnClickListener {
            val action = LoggedInFragmentDirections.actionLoggedInFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            LoggedInFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}*/
