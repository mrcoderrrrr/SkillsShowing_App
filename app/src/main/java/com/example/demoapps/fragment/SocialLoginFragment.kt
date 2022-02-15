package com.example.demoapps.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentSocialLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


class SocialLoginFragment : Fragment() {
    private lateinit var dataBinding: FragmentSocialLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val req_Code: Int = 123
    private var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var callbackManager: CallbackManager
    private val EMAIL="email"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_social_login, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
    }

    private fun setClick() {
        googleSignin()
        fbSignin()
        buttonView()
    }

    private fun buttonView() {
        dataBinding.btnGoogleSignIn.visibility = View.VISIBLE
        dataBinding.btnGoogleLogOut.visibility =View.GONE
        dataBinding.tvGoogletxt.text = "Log In SucessFull"
        dataBinding.tvGoogletxt.visibility = View.GONE
        dataBinding.btnFbLogin.visibility=View.VISIBLE
        dataBinding.btnFbLogout.visibility=View.GONE
    }

    private fun fbSignin() {
    dataBinding.btnFbLogin.setReadPermissions(EMAIL)
    callbackManager= CallbackManager.Factory.create()
    dataBinding.btnFbLogin.registerCallback(callbackManager,object : FacebookCallback<LoginResult?> {
        override fun onCancel() {

        }

        override fun onError(error: FacebookException) {

        }

        override fun onSuccess(result: LoginResult?) {
            handleFacebookAcessToken(result!!.accessToken)
        }
    })
        dataBinding.btnFbLogout.setOnClickListener {
            LoginManager.getInstance().logOut()
            dataBinding.btnGoogleSignIn.visibility = View.VISIBLE
            dataBinding.btnGoogleLogOut.visibility =View.GONE
            dataBinding.tvGoogletxt.text = "Log In SucessFull"
            dataBinding.tvGoogletxt.visibility = View.GONE
            dataBinding.btnFbLogin.visibility=View.VISIBLE
            dataBinding.btnFbLogout.visibility=View.GONE
        }
    }

    private fun handleFacebookAcessToken(accessToken: AccessToken) {
val credential=FacebookAuthProvider.getCredential(accessToken.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(requireActivity(), OnCompleteListener {task ->
            if (task.isSuccessful){
                dataBinding.btnGoogleSignIn.visibility = View.GONE
                dataBinding.tvGoogletxt.text = "Log In SucessFull"
                dataBinding.tvGoogletxt.visibility = View.VISIBLE
                dataBinding.btnFbLogin.visibility=View.GONE
                dataBinding.btnFbLogout.visibility=View.VISIBLE
            }
        })
    }

    private fun googleSignin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        dataBinding.btnGoogleSignIn.setOnClickListener {
            googleSignIn()
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, req_Code)
        }
        dataBinding.btnGoogleLogOut.setOnClickListener {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(requireActivity(), OnCompleteListener {
                    googleSignOut()
                    Toast.makeText(requireContext(), "Sign Out", Toast.LENGTH_LONG).show()
                })
        }
    }

    private fun googleSignOut() {
        dataBinding.btnGoogleSignIn.visibility = View.VISIBLE
        dataBinding.tvGoogletxt.text = ""
        dataBinding.tvGoogletxt.visibility = View.GONE
        dataBinding.btnGoogleLogOut.visibility = View.GONE
        dataBinding.btnFbLogin.visibility = View.VISIBLE
    }

    private fun googleSignIn() {
        dataBinding.btnGoogleSignIn.setSize(SignInButton.SIZE_WIDE)
        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (acct != null) {
            dataBinding.btnGoogleSignIn.visibility = View.GONE
            dataBinding.tvGoogletxt.text = acct.displayName
            dataBinding.tvGoogletxt.visibility = View.VISIBLE
            dataBinding.btnGoogleLogOut.visibility = View.VISIBLE
            dataBinding.btnFbLogin.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            callbackManager.onActivityResult(requestCode,resultCode,data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

        } catch (e: ApiException) {
            dataBinding.btnGoogleSignIn.visibility = View.VISIBLE
            dataBinding.tvGoogletxt.text = ""
            dataBinding.tvGoogletxt.visibility = View.GONE
            dataBinding.btnGoogleLogOut.visibility = View.GONE
        }
    }
}