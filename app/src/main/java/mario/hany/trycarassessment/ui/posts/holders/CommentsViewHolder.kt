package mario.hany.trycarassessment.ui.posts.holders

import androidx.recyclerview.widget.RecyclerView
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.databinding.CommentItemBinding

class CommentsViewHolder(private val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Comment) {
        binding.run {
            nameTv.text = post.name
            emailTv.text = post.email
            bodyTv.text = post.body
        }
    }
}