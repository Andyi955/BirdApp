package ie.wit.birdapp.fragments

import AddBirdAdapter
import BirdListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.birdapp.R
import ie.wit.birdapp.models.BirdModel
import ie.wit.birdapp.utils.createLoader
import ie.wit.birdapp.utils.hideLoader
import ie.wit.birdapp.utils.showLoader
import kotlinx.android.synthetic.main.collection_fragment.view.*
import org.jetbrains.anko.info

class AllBirdsFragment : BirdCollectionFragment(), BirdListener

{
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.collection_fragment, container, false)
        activity?.title = getString(R.string.menu_collection_all)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        var query = FirebaseDatabase.getInstance()
            .reference.child("collections")

        var options = FirebaseRecyclerOptions.Builder<BirdModel>()
            .setQuery(query, BirdModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = AddBirdAdapter(options, this)

        return root
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AllBirdsFragment().apply {
                arguments = Bundle().apply { }
            }
    }


}