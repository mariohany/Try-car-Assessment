package mario.hany.trycarassessment.ui.posts.holders

import androidx.recyclerview.widget.RecyclerView
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.databinding.PostItemBinding

class PostsViewHolder(private val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.run {
            titleTv.text = post.title
            bodyTv.text = post.body
        }
    }
}