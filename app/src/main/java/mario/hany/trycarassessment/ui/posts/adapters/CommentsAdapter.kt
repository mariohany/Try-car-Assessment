package mario.hany.trycarassessment.ui.posts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.databinding.CommentItemBinding
import mario.hany.trycarassessment.ui.posts.holders.CommentsViewHolder

class CommentsAdapter(private val list: List<Comment>): RecyclerView.Adapter<CommentsViewHolder>()  {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}