



package ie.wit.birdapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import ie.wit.birdapp.R
import ie.wit.birdapp.adapters.AddBirdAdapter

import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import kotlinx.android.synthetic.main.add_bird_fragment.*
import kotlinx.android.synthetic.main.add_bird_fragment.view.*
import kotlinx.android.synthetic.main.collection_layout_cards.*
import kotlinx.android.synthetic.main.collection_layout_cards.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.util.*


class AddBirdFragment : Fragment(),AnkoLogger {

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
        val root = inflater.inflate(R.layout.add_bird_fragment, container, false)
        activity?.title = getString(R.string.action_addbird)





       val searchableSpinner =root.findViewById<SearchableSpinner>(R.id.spinner_view) //gets the id of the searchable spinner
        searchableSpinner?.adapter = ArrayAdapter.createFromResource(requireActivity(),R.array.Types,android.R.layout.simple_spinner_item) //Gets my bird type array form strings and loads it into the spinner
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



            root.progressBar3.max = 50



        //setImageButton(root)




        setButtonListener(root)

        return root;
    }








    companion object {
        @JvmStatic
        fun newInstance() =
                AddBirdFragment().apply {
                    arguments = Bundle().apply {}
                }
    }

    /**
     * Button that allows birds to be added to the
     * BirdCollectionFragment
     */
    fun setButtonListener(layout:View ){
        layout.addButton.setOnClickListener {



            if (layout.birdName.editableText.isEmpty())
                activity?.toast("Please Enter name")

            if(layout.spinner_view.selectedItem.toString() == "None Selected")
                activity?.toast("Please select a type")

            if(layout.birdRef.editableText.isEmpty())
                activity?.toast("Please enter ref number")




            else  {
                val type = layout.spinner_view.selectedItem.toString()
                val addname = layout.birdName.text.toString()
                val refNo = layout.birdRef.text.toString().toLong()



                layout.progressBar3.setProgress(progressBar3.progress +10)

                if(layout.progressBar3.progress >= 50) {
                    layout.progressBar3.progress = 0
                   activity?.toast("Level Up")

                }



                app.birdStore.create(BirdModel(name = addname,  type = type, ref = refNo))
            activity?.toast("Bird Added to collection")
        }

    }}
  /*  fun setImageButton(layout: View){
        layout.imageBtn.setOnClickListener {
            showImagePicker(this.requireActivity(), IMAGE_REQUEST)
        }
    }*/






}