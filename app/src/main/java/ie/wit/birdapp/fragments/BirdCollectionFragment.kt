package ie.wit.birdapp.fragments

import AddBirdAdapter
import BirdListener
import android.os.Bundle
import android.service.media.MediaBrowserService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.birdapp.R
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import ie.wit.birdapp.utils.*
import kotlinx.android.synthetic.main.collection_fragment.view.*
import kotlinx.android.synthetic.main.collection_fragment.view.recyclerView
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


/**
 * A simple [Fragment] subclass.
 * Use the [BirdCollectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BirdCollectionFragment : Fragment(), AnkoLogger, BirdListener {
    lateinit var app: BirdApp
   lateinit var loader: AlertDialog
    lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.collection_fragment, container, false)
        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        activity?.title = getString(R.string.action_collection)


        var query = FirebaseDatabase.getInstance()
            .reference
            .child("user-collections").child(app.currentUser.uid)

        var options = FirebaseRecyclerOptions.Builder<BirdModel>()
            .setQuery(query, BirdModel::class.java)
            .setLifecycleOwner(this)
            .build()

        root.recyclerView.adapter = AddBirdAdapter(options, this)


        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as AddBirdAdapter
                deleteCollection((viewHolder.itemView.tag as BirdModel).uid)
                deleteUserCollection(
                    app.currentUser.uid,
                                     (viewHolder.itemView.tag as BirdModel).uid)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onBirdClick(viewHolder.itemView.tag as BirdModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerView)

        return root
    }




    companion object {
        @JvmStatic
        fun newInstance() =
                BirdCollectionFragment().apply {
                    arguments = Bundle().apply {}
                }
    }





    fun deleteUserCollection(userId: String, uid: String?) {
        app.database.child("user-collections").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }

    fun deleteCollection(uid: String?) {
        app.database.child("collections").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }
    override fun onBirdClick(birdModel: BirdModel) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, EditBirdFragment.newInstance(birdModel))
            .addToBackStack(null)
            .commit()
    }



}





