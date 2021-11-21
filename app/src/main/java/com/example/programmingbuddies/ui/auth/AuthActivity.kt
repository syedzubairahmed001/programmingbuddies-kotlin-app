package com.example.programmingbuddies.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.programmingbuddies.ui.home.MainActivity
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ActivityAuthBinding
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.ui.userdetails.UserDetailsActivity
import com.example.programmingbuddies.util.makeStatusBarTransparent
import com.example.programmingbuddies.util.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 200
    private val binding by viewBinding(ActivityAuthBinding::inflate)
    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var authCode:String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        makeStatusBarTransparent()
        collectUIEvents()
        Log.d("AuthActivity", "onCreate: ")

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("176124880939-cqh6n04njbl4097fkncu650g2ha5sm9q.apps.googleusercontent.com")
            .requestServerAuthCode("176124880939-cqh6n04njbl4097fkncu650g2ha5sm9q.apps.googleusercontent.com")
            .build()

        auth = Firebase.auth

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun collectUIEvents() = lifecycleScope.launchWhenStarted {
        viewModel.uiEvents.collect{
            when(it){
                AuthScreenEvents.NavigateToHomeScreen -> navigateToHomeScreen()
                AuthScreenEvents.NavigateToUserDetailsScreen -> navigateToUserDetailsScreen()
                is AuthScreenEvents.ShowToast -> showToast(it.message)
            }
        }
    }

    private fun showToast(message: String) {

    }

    private fun navigateToUserDetailsScreen() {
        Intent(this, UserDetailsActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToHomeScreen() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun signIn() {
        viewModel.startLoading()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val currentUser = auth.currentUser
        if(account!=null){
//            Toast.makeText(this,account.idToken,Toast.LENGTH_SHORT).show()
            Log.d("AuthACtivity", "AUthCOde: " + account.serverAuthCode)
            Log.d("AuthACtivity", "onStart: " + account.idToken)
        }
        updateUIFirebase(currentUser)
    }


    private fun updateUIFirebase(googleSignInAccount: FirebaseUser?) {
        if(googleSignInAccount!=null){
            binding.googleSignIn.isClickable = false
//            Toast.makeText(this,"Signed In",Toast.LENGTH_SHORT).show()
            Log.d("AuthACtivity", "onStart: ")
            getAccessToken()
            saveUserIntoPref(googleSignInAccount)

        }else{
            binding.googleSignIn.isClickable = true
        }
    }

    private fun getAccessToken() {

    }

    private fun saveUserIntoPref(user: FirebaseUser) {
        val userh = User(
            name = user.displayName.toString(),
            email = user.email.toString(),
            profilePictureURL = user.photoUrl.toString()
        )
        Toast.makeText(this, "Hey" + userh.profilePictureURL, Toast.LENGTH_LONG).show()
        viewModel.saveUser(
            User(
                name = user.displayName.toString(),
                email = user.email.toString(),
                profilePictureURL = user.photoUrl.toString()
            )
        )
        viewModel.loginComplete(
            User(
                name = user.displayName.toString(),
                email = user.email.toString(),
                profilePictureURL = user.photoUrl.toString()
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        viewModel.stopLoading()
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Log.d("AccessCode", "handleSignInResult: " + account.serverAuthCode)
            authCode = account.serverAuthCode
            firebaseAuthWithGoogle(account.idToken!!)
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AuthActivity", "signInResult:failed code=" + e.statusCode)
            updateUIFirebase(null)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){

                    val token = task.result.credential as OAuthCredential
                    Toast.makeText(this, "${token.accessToken}", Toast.LENGTH_LONG).show()
                    Log.d("AuthActivity", "handleSignInResult: " + token.accessToken)
                    print("${token.accessToken}" + "${token.idToken}")
                    val user = auth.currentUser
                    Log.d("AuthActivity", "handleSignInResult: " + authCode.toString())
                    Log.d("AuthActivity", "handleSignInResult: " + user?.getIdToken(true))
                    updateUIFirebase(user)

                }else{
                    Log.d("AuthActivity", "signInWithCredential:failure ", task.exception)
                    updateUIFirebase(null)
                }
            }
    }
}