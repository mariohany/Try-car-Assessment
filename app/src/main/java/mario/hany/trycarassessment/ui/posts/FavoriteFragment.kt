package mario.hany.trycarassessment.ui.posts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mario.hany.trycarassessment.R
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.databinding.FragmentFavoriteBinding
import mario.hany.trycarassessment.ui.posts.adapters.PostsAdapter
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewModel
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewState
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val viewModel: PostsViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val postsList:MutableList<Post> by lazy { mutableListOf() }
    private val postsAdapter: PostsAdapter by lazy { PostsAdapter(postsList){ post ->
        Bundle().also {
            it.putSerializable(PostDetailsFragment.SELECTED_POST, post)
            findNavController().navigate(R.id.postDetailsFragment, it)
        }
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFavoritePosts()
        bindViews()
        bindObservers()
    }

    private fun bindViews() {
        _binding?.run {
            LinearLayoutManager(requireContext()).also {
                postsRv.layoutManager = it
                postsRv.adapter = postsAdapter
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bindObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when(it){
                is PostsViewState.Loading -> showLoader(true)
                is PostsViewState.Posts -> {
                    showLoader(false)
                    postsList.clear()
                    postsList.addAll(it.list)
                    postsAdapter.notifyDataSetChanged()
                }
                is PostsViewState.Error -> {
                    showLoader(false)
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoader(state: Boolean){
        _binding?.apply {
            loader.isVisible = state
            postsRv.isVisible = !state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}