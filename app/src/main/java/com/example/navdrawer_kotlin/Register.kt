package com.example.navdrawer_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

import androidx.fragment.app.setFragmentResultListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [registerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var cnfPassword: EditText
    private lateinit var reg: Button
    private lateinit var fAuth: FirebaseAuth

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
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        username = view.findViewById(R.id.reg_username)
        email = view.findViewById(R.id.reg_email)
        password = view.findViewById(R.id.reg_password)
        cnfPassword = view.findViewById(R.id.reg_cnf_password)
        fAuth = Firebase.auth
        reg = view.findViewById(R.id.btn_register_reg)


//        view.findViewById<Button>(R.id.btn_login_reg).setOnClickListener {
//            val navRegister= activity as fragmentNavigation
//            navRegister.navigateFrag(Login(), false)
//        }

        view.findViewById<Button>(R.id.btn_register_reg).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }



    private fun firebaseSignUp(){
        reg.isEnabled = false
        reg.alpha =0.5f

        fAuth.createUserWithEmailAndPassword(email.text.toString(),
            password.text.toString()).addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                Toast.makeText(context,"Register Successful",Toast.LENGTH_SHORT).show()
                activity?.let{
                    val intent = Intent (it, SignInUp::class.java)
                    it.startActivity(intent)
                    it.finishAfterTransition() //jhol
                }

//                val navRegister= activity as fragmentNavigation
//                navRegister.navigateFrag(Login(), false)
            }
            else{
                reg.isEnabled = true
                reg.alpha =1.0f
                Toast.makeText(context,task.exception?.message,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateEmptyForm(){
        val icon = AppCompatResources.getDrawable(requireContext(),
            R.drawable.warning1)
        icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
        when{
            TextUtils.isEmpty(username.text.toString().trim())->{
                username.setError("Please Enter Username", icon)
            }
            TextUtils.isEmpty(email.text.toString().trim())->{
                email.setError("Please Enter Email ID", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim())->{
                password.setError("Please Enter Password", icon)
            }
            TextUtils.isEmpty(cnfPassword.text.toString().trim())->{
                cnfPassword.setError("Please Enter Password Again", icon)
            }

            username.text.toString().isNotEmpty()&&
                    email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty()&&
                    cnfPassword.text.toString().isNotEmpty() ->
            {
                if(email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    if(password.text.toString().length>=6){
                        if(password.text.toString() == cnfPassword.text.toString()){
                            firebaseSignUp()

                        }
                        else{
                            cnfPassword.setError("Password Didn't Match", icon)
                        }
                    }
                    else{
                        password.setError("Please Enter Atleast 6 Characters",icon)
                    }

                }
                else{
                    email.setError("Please Enter Valid Email ID",icon)
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
         * @return A new instance of fragment registerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Register().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}