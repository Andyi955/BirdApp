package ie.wit.birdapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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

    lateinit var loader : AlertDialog

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        app = application as BirdApp
        // Buttons
        emailSignInButton.setOnClickListener(this)
        emailCreateAccountButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        verifyEmailButton.setOnClickListener(this)
        sign_in_button.setOnClickListener(this)



        // [START initialize_auth]
        // Initialize Firebase Auth
        app.auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        // Configure Google Sign In
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        app.googleSignInClient = GoogleSignIn.getClient(this, gso)


        app.database = FirebaseDatabase.getInstance().reference

        loader = createLoader(this)

        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        sign_in_button.setColorScheme(0)

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = app.auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showLoader(loader, "Creating Account...")

        // [START create_user_with_email]
        app.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = app.auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    hideLoader(loader)
                    // [END_EXCLUDE]
                }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        showLoader(loader, "Logging In...")

        // [START sign_in_with_email]
        app.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = app.auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful) {
                        status.setText(R.string.auth_failed)
                    }
                    hideLoader(loader)
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        app.auth.signOut()
        app.googleSignInClient.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        // Disable button
        verifyEmailButton.isEnabled = false

        // Send verification email
        // [START send_email_verification]
        val user = app.auth.currentUser
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this) { task ->
                    // [START_EXCLUDE]
                    // Re-enable button
                    verifyEmailButton.isEnabled = true

                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Verification email sent to ${user.email} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // [END_EXCLUDE]
                }
        // [END send_email_verification]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = editEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            editEmail.error = "Required."
            valid = false
        } else {
            editEmail.error = null
        }

        val password = editPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            editPassword.error = "Required."
            valid = false
        } else {
            editPassword.error = null
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideLoader(loader)
        if (user != null) {
            status.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            detail.text = getString(R.string.firebase_status_fmt, user.uid)

            emailPasswordButtons.visibility = View.GONE
            emailPasswordFields.visibility = View.GONE
            signedInButtons.visibility = View.VISIBLE

            verifyEmailButton.isEnabled = !user.isEmailVerified
            app.storage = FirebaseStorage.getInstance().reference
            startActivity<Home>()
        } else {
            status.setText(R.string.signed_out)
            detail.text = null

            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            signedInButtons.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.emailCreateAccountButton -> createAccount(
                editEmail.text.toString(),
                editPassword.text.toString()
            )
            R.id.emailSignInButton -> signIn(
                editEmail.text.toString(),
                editPassword.text.toString()
            )
            R.id.signOutButton -> signOut()
            R.id.verifyEmailButton -> sendEmailVerification()
            R.id.sign_in_button -> googleSignIn()

        }
    }
    // [START google signin]
    private fun googleSignIn() {
        val signInIntent = app.googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END google signin]


    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_SIGN_IN = 9001

    }

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        showLoader(loader, "Logging In with Google...")
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        app.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = app.auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                hideLoader(loader)
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]







}
