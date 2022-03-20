package mario.hany.trycarassessment.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import mario.hany.trycarassessment.R
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.databinding.PostDetailsFragmentBinding
import mario.hany.trycarassessment.ui.posts.adapters.CommentsAdapter
import mario.hany.trycarassessment.ui.posts.viewmodel.PostDetailsViewModel
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewState
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailsFragment : Fragment() {

    private val viewModel: PostDetailsViewModel by viewModel()
    private var _binding: PostDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val commentsList: MutableList<Comment> by lazy { mutableListOf() }
    private val commentsAdapter: CommentsAdapter by lazy { CommentsAdapter(commentsList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(SELECTED_POST)?.let {
            (it as? Post)?.let { viewModel.selectedPost = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViews()
        bindObservers()
    }

    private fun bindViews() {
        _binding?.run {
            LinearLayoutManager(requireContext()).also {
                commentsRv.layoutManager = it
                commentsRv.adapter = commentsAdapter
            }

            viewModel.selectedPost?.let {
                titleTv.text = it.title
                bodyTv.text = it.body
                commentsList.clear()
                it.comments?.let { it1 -> commentsList.addAll(it1) }
                commentsAdapter.notifyDataSetChanged()
                normalView.isVisible = true
                favoriteIv.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (it.isFavorite == true)
                            R.drawable.ic_favorite_selected
                        else
                            R.drawable.ic_favorite_unselected,
                        null
                    )
                )

                favoriteIv.setOnClickListener { _ ->
                    favoriteIv.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            if (it.isFavorite == true)
                                R.drawable.ic_favorite_unselected
                            else
                                R.drawable.ic_favorite_selected,
                            null
                        )
                    )
                    viewModel.updatePostFavoriteStatus()
                }
            }
        }
    }

    private fun bindObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is PostsViewState.Loading -> showLoader(true)
                is PostsViewState.Error -> {
                    showLoader(false)
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    showLoader(false)
                }
            }
        }
    }

    private fun showLoader(state: Boolean) {
        _binding?.apply {
            loader.isVisible = state
            normalView.isVisible = !state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SELECTED_POST = "SELECTED_POST"
    }
}