package br.org.cesar.discordtime.stickysessions.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.org.cesar.discordtime.stickysessions.BuildConfig
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication
import br.org.cesar.discordtime.stickysessions.logger.Logger
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.login_layout
import com.google.android.material.button.MaterialButton
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewStarter: IViewStarter

    @Inject
    lateinit var mRouter: IRouter

    @Inject
    lateinit var mLogger: Logger

    private lateinit var mContext: Context
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loading: ProgressBar
    private lateinit var logoImage: ImageView
    private lateinit var signInButton: MaterialButton
    private lateinit var loginTokenText: TextView
    private lateinit var versionText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as StickySessionApplication).inject(this)

        mContext = this

        // Get views
        loading = findViewById(R.id.loading_sign_in)
        logoImage = findViewById(R.id.image_logo)
        signInButton = findViewById(R.id.btn_sign_in)
        signInButton.setOnClickListener { this.signIn() }
        loginTokenText = findViewById(R.id.text_login_token)
        versionText = findViewById(R.id.text_version)
        versionText.text = String.format(getString(R.string.version_text_format),
                BuildConfig.VERSION_NAME)

        // Google and Firebase objects
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        isUserLogged(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun isUserLogged(user: FirebaseUser?) {
        if (user != null) {
            // user already logged go to next activity
            goNext()
        }
    }

    private fun goNext() {
        val route = mRouter.getNext(ViewNames.LOGIN_ACTIVITY, IRouter.USER_LOGGED)
        mViewStarter.goNext(mContext, route.to, route.shouldClearStack)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                mLogger.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        mLogger.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        showProgressDialog()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        mLogger.d(TAG, "signInWithCredential:success")
                        // next activity
                        goNext()
                    } else {
                        // If sign in fails, display a message to the user.
                        mLogger.w(TAG, "signInWithCredential:failure", task.exception)
                        Snackbar.make(login_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        hideProgressDialog()
                    }
                }
    }

    private fun showProgressDialog() {
        loading.visibility = View.VISIBLE
        logoImage.visibility = View.INVISIBLE
        signInButton.visibility = View.INVISIBLE
        loginTokenText.visibility = View.INVISIBLE
        versionText.visibility = View.INVISIBLE
    }

    private fun hideProgressDialog() {
        loading.visibility = View.INVISIBLE
        logoImage.visibility = View.VISIBLE
        signInButton.visibility = View.VISIBLE
        loginTokenText.visibility = View.VISIBLE
        versionText.visibility = View.VISIBLE
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}
