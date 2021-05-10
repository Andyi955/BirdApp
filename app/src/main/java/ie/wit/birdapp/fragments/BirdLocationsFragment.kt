package ie.wit.birdapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.birdapp.R
import ie.wit.birdapp.helpers.getAllBirds
import ie.wit.birdapp.helpers.getFavouriteBirds
import ie.wit.birdapp.helpers.setMapMarker
import ie.wit.birdapp.main.BirdApp
import kotlinx.android.synthetic.main.fragment_bird_locations.*


class BirdLocationsFragment : Fragment() {

    lateinit var app: BirdApp
    var viewFavourites = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as BirdApp
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_bird_locations, container, false)

        return layout;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.favourites_title)

        imageMapFavourites.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                app.mMap.clear()
                if (!viewFavourites) {
                    imageMapFavourites.setImageResource(android.R.drawable.star_big_on)
                    viewFavourites = true
                    getFavouriteBirds(app)

                }
                else {
                   imageMapFavourites.setImageResource(android.R.drawable.star_big_off)
                    viewFavourites = false
                    getAllBirds(app)
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                BirdLocationsFragment().apply {
                    arguments = Bundle().apply { }
                }
    }
}