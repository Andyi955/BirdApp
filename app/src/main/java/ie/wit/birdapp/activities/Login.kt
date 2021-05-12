package ie.wit.birdapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import ie.wit.birdapp.R
import ie.wit.birdapp.main.BirdApp
import ie.wit.birdapp.utils.createLoader
import ie.wit.birdapp.utils.hideLoader
import ie.wit.birdapp.utils.showLoader
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.startActivity


class Login : AppCompatActivity(), View.OnClickListener {

    lateinit var app: BirdApp

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        app = application as BirdApp

        checkSignedIn()
    }

    private fun checkSignedIn() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            app.currentUser = FirebaseAuth.getInstance().currentUser!!
            app.database = FirebaseDatabase.getInstance().reference
            app.storage = FirebaseStorage.getInstance().reference
            startActivity<Home>()
        }
        else
        // not signed in
            createSignInIntent()
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        val customLayout = AuthMethodPickerLayout
            .Builder(R.layout.login)
            .setGoogleButtonId(R.id.googleSignInButton)
            .setEmailButtonId(R.id.emailSignInButton)
            .setPhoneButtonId(R.id.phoneSignInButton)
            .build()

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false,true) // true,true for Smart Lock
                .setTheme(R.style.FirebaseLoginTheme)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            123)
        // [END auth_fui_create_intent]

    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                app.currentUser = FirebaseAuth.getInstance().currentUser!!
                app.database = FirebaseDatabase.getInstance().reference
                app.storage = FirebaseStorage.getInstance().reference

                startActivity<Home>()
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                if (response != null) {
                    response.getError()?.getErrorCode()
                }
                // ...
                startActivity<Login>()
            }
        }
    }
    // [END auth_fui_result]

    override fun onClick(v: View) { createSignInIntent() }

}


