package com.example.navdrawer_kotlin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.navdrawer_kotlin.R
import com.example.navdrawer_kotlin.StaticRvModel
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class BlogRecyclerAdapter  (val clickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<BlogPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BlogViewHolder ->{
                holder.bind(items.get(position))
                holder.itemView.setOnClickListener {
                    clickListener(position)
                    notifyDataSetChanged()
                }
            }
//            holder.layoutPosition.setOnClickListener{
//               clickListener(position)
//                notifyDataSetChanged()
//            }
        }
    }

    override fun getItemCount(): Int {
       return  items.size
    }

    fun submitList(blogList: List<BlogPost>){
        items = blogList
    }

    class BlogViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val blogImage = itemView.blog_image
        val blogTitle = itemView.blog_title
        val blogAuthor = itemView.blog_author

        fun bind(blogPost: BlogPost){
            blogTitle.setText(blogPost.title)
            blogAuthor.setText(blogPost.username)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white)
                .error(R.drawable.white)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(blogPost.image)
                .into(blogImage)

        }


    }
}


