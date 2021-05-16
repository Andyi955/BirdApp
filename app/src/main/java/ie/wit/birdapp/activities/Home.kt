package ie.wit.birdapp.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.birdapp.R
import ie.wit.birdapp.fragments.*
import ie.wit.birdapp.helpers.*
import ie.wit.birdapp.main.BirdApp
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.add_bird_fragment.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class Home : AppCompatActivity(),
NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: BirdApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)
        app = application as BirdApp


        app.locationClient = LocationServices.getFusedLocationProviderClient(this)
        if(checkLocationPermissions(this)) {
            // todo get the current location
            setCurrentLocation(app)
        }

        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        if(app.currentUser.email != null)
            navView.getHeaderView(0).nav_header_email.text = app.currentUser.email
        else
            navView.getHeaderView(0).nav_header_email.text = "No Email Specified..."


        navView.getHeaderView(0).imageView.setOnClickListener {
           showImagePicker(this,1)
        }

        //Checking if Google User, upload google profile pic
        checkExistingPhoto(app,this)



        ft = supportFragmentManager.beginTransaction()

     val fragment = AddBirdFragment.newInstance()
    ft.replace(R.id.mainFrame,fragment)
        ft.commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_maps -> navigateTo(BirdLocationsFragment.newInstance())
            R.id.nav_add -> navigateTo(AddBirdFragment.newInstance())
            R.id.nav_collection -> navigateTo(BirdCollectionFragment.newInstance())
            R.id.nav_collection_all -> navigateTo(AllBirdsFragment.newInstance()    )
            R.id.nav_sign_out ->
                signOut()
            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }




    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment)
            .addToBackStack(null)
            .commit()
    }



    private fun signOut()
    {
      AuthUI.getInstance()
          .signOut(this)
          .addOnCompleteListener{startActivity<Login>()}
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    writeImageRef(app,readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                            .resize(180, 180)
                            .transform(CropCircleTransformation())
                            .into(navView.getHeaderView(0).imageView, object : Callback {
                                override fun onSuccess() {
                                    // Drawable is ready
                                    uploadImageView(app,navView.getHeaderView(0).imageView)
                                }
                                override fun onError(e: Exception) {}
                            })
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            setCurrentLocation(app)
        } else {
            // permissions denied, so use the default location
            app.currentLocation = Location("Default").apply {
                latitude = 52.245696
                longitude = -7.139102}
        }
        Log.v("Bird", "Home LAT: ${app.currentLocation.latitude} LNG: ${app.currentLocation.longitude}")
    }

}
