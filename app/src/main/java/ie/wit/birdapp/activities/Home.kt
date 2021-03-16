package ie.wit.birdapp.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import ie.wit.birdapp.R
import ie.wit.birdapp.fragments.AddBirdFragment
import ie.wit.birdapp.fragments.BirdCollectionFragment
import ie.wit.birdapp.fragments.MapsFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.home.*
import org.jetbrains.anko.toast


class Home : AppCompatActivity(),
NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)


        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        ft = supportFragmentManager.beginTransaction()

     val fragment = AddBirdFragment.newInstance()
    ft.replace(R.id.mainFrame,fragment)
        ft.commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        R.id.nav_maps -> navigateTo(MapsFragment.newInstance())
         R.id.nav_add -> navigateTo(AddBirdFragment.newInstance())
            R.id.nav_collection -> navigateTo(BirdCollectionFragment.newInstance())

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

  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    bird1.image = data.data.toString()
                    birdImage.setImageBitmap(readImage(this, resultCode, data))
                    imageBtn.setText(R.string.change_bird_image)

                }
            }

        }
    }*/


}
