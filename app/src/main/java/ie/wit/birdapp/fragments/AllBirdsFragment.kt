package ie.wit.birdapp.fragments

import AddBirdAdapter
import BirdListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                AllBirdsFragment().apply {
                    arguments = Bundle().apply { }
                }
    }

    override fun onResume() {
        super.onResume()
        getAllUsersDonations()
    }

    fun getAllUsersDonations() {
        loader = createLoader(requireActivity())
        showLoader(loader, "Downloading All Users Birds from Firebase")
        val birdList = ArrayList<BirdModel>()
        app.database.child("collections")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        hideLoader(loader)
                        val children = snapshot.children
                        children.forEach {
                            val bird = it.
                            getValue(BirdModel::class.java)

                            birdList.add(bird!!)
                            root.recyclerView.adapter =
                                    AddBirdAdapter(birdList, this@AllBirdsFragment,birdall = true)
                            root.recyclerView.adapter?.notifyDataSetChanged()

                            app.database.child("collections").removeEventListener(this)
                        }
                    }
                })
    }

}