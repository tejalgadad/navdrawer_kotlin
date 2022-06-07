package com.example.navdrawer_kotlin

import android.content.ContentValues.TAG
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

        view.findViewById<Button>(R.id.info_save).setOnClickListener {
            validateEmptyForm()
        }

       setFragmentListener()
        return view
    }

    private fun setFragmentListener() {
        setFragmentResultListener("logindata") { requestKey, bundle ->
            val result = bundle.getString("bundlekey")
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
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

//        var flag =0
//        database= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
//        ).getReference(path)
//
//        Toast.makeText(context,"in save",Toast.LENGTH_SHORT).show()
//
//        loo@ for (i in 1..4) {
//            Toast.makeText(context,"in for loop " + i +" "+idnum,Toast.LENGTH_SHORT).show()
//
//            database.child(i.toString()).get().addOnSuccessListener {
//                if(i.toString()==(it.child("idnum").value).toString()){
//                    idnum = i
//                    Toast.makeText(context,idnum,Toast.LENGTH_SHORT).show()
//                    flag = 1
//                }
//            }
//            if(flag==1){
//                break@loo
//            }
//        }
//        Toast.makeText(context,"out of loop",Toast.LENGTH_SHORT).show()


        if(idnum>4){
            Toast.makeText(context,"Only 4 Emergency Contacts Allowed",Toast.LENGTH_SHORT).show()
        }
        else{
            database= FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
            ).getReference(path)
            //val dataId =database.push().key
            val data = Database(idnum,name.text.toString(), number.text.toString(),
                email.text.toString(), message.text.toString())
            database.child(idnum.toString()).setValue(data).addOnCompleteListener{
                Toast.makeText(context,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
            }
            name.text.clear()
            number.text.clear()
            email.text.clear()
            message.text.clear()
            idnum++
        }
    }

    private fun validateDeleteForm(){
        val icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.warning1
        )
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        when {
            TextUtils.isEmpty(name.text.toString().trim()) -> {
                name.setError("Please Enter Name To Delete Data", icon)
            }
            name.text.toString().isNotEmpty()->{
                val username = name.text.toString()
                database = FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("data")
               for (i in 1..4){
                   database.child(i.toString()).get().addOnSuccessListener {
                       if(username==(it.child("name").value).toString()){
                           database.child(i.toString()).removeValue().addOnCompleteListener{
                               Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show()
                               name.text.clear()
                           }.addOnFailureListener{
                               Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                           }
                       }
                   }
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