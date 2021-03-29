



package ie.wit.birdapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import ie.wit.birdapp.R
import ie.wit.birdapp.activities.Login
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.models.BirdModel
import ie.wit.birdapp.utils.*
import kotlinx.android.synthetic.main.add_bird_fragment.*
import kotlinx.android.synthetic.main.add_bird_fragment.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddBirdFragment : Fragment(),AnkoLogger {

    lateinit var app: BirdApp
    lateinit var loader: AlertDialog


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
        loader = createLoader(requireActivity())
        activity?.title = getString(R.string.action_addbird)


        val searchableSpinner = root.findViewById<SearchableSpinner>(R.id.spinner_view) //gets the id of the searchable spinner
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
    fun setButtonListener(layout: View) {
        layout.addButton.setOnClickListener {


            if (layout.birdName.editableText.isEmpty())
                activity?.toast("Please Enter name")

            if (layout.spinner_view.selectedItem.toString() == "None Selected")
                activity?.toast("Please select a type")

            if (layout.birdRef.editableText.isEmpty())
                activity?.toast("Please enter ref number")
            else {
                val type = layout.spinner_view.selectedItem.toString()
                val addname = layout.birdName.text.toString()
                val refNo = layout.birdRef.text.toString().toLong()



                layout.progressBar3.setProgress(progressBar3.progress + 10)

                if (layout.progressBar3.progress >= 50) {
                    layout.progressBar3.progress = 0
                    activity?.toast("Level Up")

                }



                app.birdStore.create(BirdModel(name = addname, type = type, ref = refNo))
                activity?.toast("Bird Added to collection")
            }

        }
    }

 /*   override fun onResume(){
        super.onResume()
        getAllBirds()
    }

    fun getAllBirds(){
        showLoader(loader, "Downloading bird collection ")
        var callGetAll = app.birdStore.findAll()
        callGetAll.en(this)

    }

    override fun onResponse(call: Call<List<BirdModel>>, response: Response<List<BirdModel>>) {
        // donationServiceAvailable = true
        serviceAvailableMessage(requireActivity())
        info("Retrofit JSON = ${response.body()}")
        app.birds = response.body() as ArrayList<BirdModel>
        updateUI()
        hideLoader(loader)
    }

    override fun onFailure(call: Call<List<BirdModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(requireActivity())
        hideLoader(loader)
    }

    fun updateUI(){


    }*/




}