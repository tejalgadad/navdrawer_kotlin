package com.example.navdrawer_kotlin

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

    class StaticRvAdapter(private val mList: List<StaticRvModel>,val clickListener: (StaticRvModel,Int) -> Unit)
         : RecyclerView.Adapter<StaticRvAdapter.ViewHolder>() {
        var root:Int = -1


//        interface onItemClickListener{
//            fun onItemClick(position: Int)
//        }
//        fun setOnItemClickListener(listener: onItemClickListener){
//            mListener = listener
//            Log.e("pos","hi")
//        }

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.static_rv_item, parent, false)
            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val StaticRvModel = mList[position]
            val item: StaticRvModel = mList[position]
            // sets the image to the imageview from our itemHolder class
            StaticRvModel.getimg()?.let { holder.imageView.setImageResource(it) }
            // sets the text to the textview from our itemHolder class
            holder.textView.text = StaticRvModel.gettext()
//            holder.linearLayout.setOnClickListener{
//
//            }
            holder.linearLayout.setOnClickListener {
                clickListener(item, position)
                root=position
                notifyDataSetChanged()
            }


            if(root==position){
                Log.e("posit",position.toString())
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg)
            }else{
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg)
            }
        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.image)
            val textView: TextView = itemView.findViewById(R.id.text)
            val linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)

            }
        }




