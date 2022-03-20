package mario.hany.trycarassessment.ui.posts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.databinding.PostItemBinding
import mario.hany.trycarassessment.ui.posts.holders.PostsViewHolder

class PostsAdapter(private val list: List<Post>, private val onItemClick:(Post) -> Unit): RecyclerView.Adapter<PostsViewHolder>()  {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position])
        }
    }
}