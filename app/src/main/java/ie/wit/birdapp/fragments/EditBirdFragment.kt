package ie.wit.birdapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import ie.wit.birdapp.R
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import ie.wit.birdapp.utils.createLoader
import ie.wit.birdapp.utils.hideLoader
import ie.wit.birdapp.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast


class EditBirdFragment : Fragment(),AnkoLogger {

    lateinit var app: BirdApp
    lateinit var loader : androidx.appcompat.app.AlertDialog
    lateinit var root: View
    var editBird: BirdModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp

       arguments?.let {
            editBird = it.getParcelable("editcollection")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(requireActivity())


        //root.editName.setText(editBird!!.name)


        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Collection")
            updateBirdData()
            updateCollection(editBird!!.uid, editBird!!)
            updateUserCollection(app.currentUser!!.uid,
                editBird!!.uid, editBird!!)
        }


        val searchableSpinner = root.findViewById<SearchableSpinner>(R.id.editBirdttype) //gets the id of the searchable spinner
        searchableSpinner?.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.Types, android.R.layout.simple_spinner_item) //Gets my bird type array form strings and loads it into the spinner
        searchableSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                // activity?.toast("Bird Type Selected")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                activity?.toast("Nothing selected")
            }

        }








        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(birdModel: BirdModel) =
            EditBirdFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editbird",birdModel)
                }
            }
    }


    fun updateBirdData() {
        editBird!!.name = root.editName.text.toString()
        editBird!!.type = root.editBirdttype.isSelected.toString()
        editBird!!.ref = root.editRefNo.text.toString().toLong()
    }
    fun updateUserCollection(userId: String, uid: String?, collection: BirdModel) {
        app.database.child("user-collections").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(collection)
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, BirdCollectionFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }

    fun updateCollection(uid: String?, collection: BirdModel) {
        app.database.child("collections").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(collection)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Donation error : ${error.message}")
                    }
                })
    }





}