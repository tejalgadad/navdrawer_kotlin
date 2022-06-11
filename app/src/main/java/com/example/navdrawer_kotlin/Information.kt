package com.example.navdrawer_kotlin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Information.newInstance] factory method to
 * create an instance of this fragment.
 */
class Information : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var path: String=""

    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var number: EditText
    private lateinit var message: EditText
    private lateinit var del: Button
    private lateinit var save: Button

    private var idnum: Int = 1
    private var key:String =""
    private var identity: String = ""
    private lateinit var database : DatabaseReference
    private lateinit var fAuth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        path = Firebase.auth.uid.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_information, container, false)
        email = view.findViewById(R.id.info_email)
        name = view.findViewById(R.id.info_name)
        number = view.findViewById(R.id.info_number)
        message = view.findViewById(R.id.info_message)
        del = view.findViewById(R.id.info_del)
        save = view.findViewById(R.id.info_save)

        view.findViewById<Button>(R.id.info_del).setOnClickListener {
            validateDeleteForm()
        }

        view.findViewById<Button>(R.id.info_back).setOnClickListener {
            activity?.let{
                    val intent = Intent (it, EmergencyActivity::class.java)
                    it.startActivity(intent)
                    it.finishAfterTransition() //jhol
                }
        }

        view.findViewById<Button>(R.id.info_save).setOnClickListener {
            validateEmptyForm()
        }


        val bundle = arguments
        identity = bundle!!.getString("card").toString()

        setData()
        return view
    }

    private fun setData() {

        database= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference(path)

        if(identity=="card1"){
            key = path+"1"
            database.child(key).get().addOnSuccessListener {
                if(it.child("name").value.toString()!="null"){
                    name.setText((it.child("name").value).toString())
                    email.setText((it.child("email").value).toString())
                    message.setText((it.child("message").value).toString())
                    number.setText((it.child("phone").value).toString())
                }
            }
        }
        else if (identity =="card2"){
            key = path+"2"
            database.child(key).get().addOnSuccessListener {
                if (it.child("name").value.toString() != "null") {
                    name.setText((it.child("name").value).toString())
                    email.setText((it.child("email").value).toString())
                    message.setText((it.child("message").value).toString())
                    number.setText((it.child("phone").value).toString())
                }
            }
        }
        else if (identity =="card3"){
            key = path+"3"
            database.child(key).get().addOnSuccessListener {
                if (it.child("name").value.toString() != "null") {
                    name.setText((it.child("name").value).toString())
                    email.setText((it.child("email").value).toString())
                    message.setText((it.child("message").value).toString())
                    number.setText((it.child("phone").value).toString())
                }
            }
        }
        else if (identity =="card4"){
            key = path+"4"
            database.child(key).get().addOnSuccessListener {
                if (it.child("name").value.toString() != "null") {
                    name.setText((it.child("name").value).toString())
                    email.setText((it.child("email").value).toString())
                    message.setText((it.child("message").value).toString())
                    number.setText((it.child("phone").value).toString())
                }
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
            TextUtils.isEmpty(name.text.toString().trim()) -> {
                name.setError("Please Enter Password", icon)
            }
            TextUtils.isEmpty(number.text.toString().trim()) -> {
                number.setError("Please Enter Phone Number", icon)
            }

            email.text.toString().isNotEmpty() &&
                    name.text.toString().isNotEmpty() &&
                    number.text.toString().isNotEmpty() -> {
                if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    //if(number.toString().matches(Regex("(0|91)?[7-9][0-9]{9}"))){
                    save()
                   // }
//                    else{
//                        number.setError("Please Enter Valid Phone Number", icon)
//                    }
                }
                else {
                    email.setError("Please Enter Valid Email ID", icon)
                }
            }
        }
    }

    private fun save (){
            database= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
            ).getReference(path)
            //val dataId =database.push().key
            val data = Database(name.text.toString(), number.text.toString(),
                email.text.toString(), message.text.toString())

            database.child(key).setValue(data).addOnCompleteListener {
                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show()
                activity?.let {
                    val intent = Intent(it, EmergencyActivity::class.java)
                    it.startActivity(intent)
                    it.finishAfterTransition()
                }}.addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
    }

    private fun validateDeleteForm(){
        val icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.warning1
        )
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
            database = FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app").getReference(path)

               database.child(key).get().addOnSuccessListener {
                   database.child(key).removeValue().addOnCompleteListener{
                       Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show()
                       name.text.clear()
                       number.text.clear()
                       email.text.clear()
                       message.text.clear()
                       activity?.let {
                           val intent = Intent(it, EmergencyActivity::class.java)
                           it.startActivity(intent)
                           it.finishAfterTransition()
                       } }.addOnFailureListener{
                       Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment Information.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Information().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}