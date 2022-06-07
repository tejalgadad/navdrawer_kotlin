package com.example.navdrawer_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [loginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var fAuth: FirebaseAuth
    private lateinit var log: Button
    private lateinit var loadingPB: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)

        email = view.findViewById(R.id.log_username)
        password = view.findViewById(R.id.log_password)
        log = view.findViewById(R.id.btn_login)
        fAuth = Firebase.auth
        loadingPB = view.findViewById(R.id.idPBLoading)

        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            val navLogin= activity as fragmentNavigation
            navLogin.navigateFrag(Register(), false)
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }

    private fun firebaseSignIn(){
        loadingPB.visibility=View.VISIBLE
        log.isEnabled = false
        log.alpha = 0.5f
        fAuth.signInWithEmailAndPassword(email.text.toString(),
            password.text.toString()).addOnCompleteListener {
                task->
            if(task.isSuccessful){
                Toast.makeText(context,"Login Successful", Toast.LENGTH_SHORT).show()
//                var navHome = activity as  fragmentNavigation
//                navHome.navigateFrag(homeFragment(), true)
                activity?.let{
                    val intent = Intent (it, MainActivity::class.java)
                    it.startActivity(intent)
                    it.finishAfterTransition() //jhol
                }
            }
            else{
                log.isEnabled = true
                log.alpha = 1.0f
                Toast.makeText(context,task.exception?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun validateEmptyForm() {
        val icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.warning1
        )
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        when {
            TextUtils.isEmpty(email.text.toString().trim()) -> {
                email.setError("Please Enter Email ID", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password", icon)
            }

            email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() -> {
                if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    firebaseSignIn()
                    // Toast.makeText(context,"Login Successful", Toast.LENGTH_SHORT).show()
                } else {
                    email.setError("Please Enter Valid Email ID", icon)
                }
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment loginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}