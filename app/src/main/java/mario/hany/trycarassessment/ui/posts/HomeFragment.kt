package mario.hany.trycarassessment.ui.posts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import mario.hany.trycarassessment.R
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.data.network.ConnectionStateMonitor
import mario.hany.trycarassessment.databinding.FragmentHomeBinding
import mario.hany.trycarassessment.ui.posts.PostDetailsFragment.Companion.SELECTED_POST
import mario.hany.trycarassessment.ui.posts.adapters.PostsAdapter
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewModel
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewState
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var networkSnackBar:Snackbar? = null
    private lateinit var connectionLiveData : ConnectionStateMonitor
    private val viewModel: PostsViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val postsList:MutableList<Post> by lazy { mutableListOf() }
    private val postsAdapter: PostsAdapter by lazy { PostsAdapter(postsList){ post ->
        Bundle().also {
            it.putSerializable(SELECTED_POST, post)
            findNavController().navigate(R.id.postDetailsFragment, it)
        }
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getPosts()
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

        connectionLiveData = ConnectionStateMonitor(requireContext())
        connectionLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                networkSnackBar?.setAction(null, null)
                    ?.setDuration(Snackbar.LENGTH_SHORT)
                    ?.setText("Back Online Again")
                    ?.show()
            } else {
                networkSnackBar = Snackbar.make(binding.root, "No Internet Connection !!",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Setting") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
                        }else{
                            startActivity( Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    }
                networkSnackBar?.show()
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