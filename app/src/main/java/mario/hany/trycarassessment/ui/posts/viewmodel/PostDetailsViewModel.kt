package mario.hany.trycarassessment.ui.posts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.ui.posts.usecases.IPostsUseCase

class PostDetailsViewModel(private val useCase: IPostsUseCase) : ViewModel() {
    private val _viewState: MutableLiveData<PostsViewState> by lazy { MutableLiveData() }
    val viewState: LiveData<PostsViewState> = _viewState

    var selectedPost: Post? = null

    fun updatePostFavoriteStatus() {
        selectedPost?.let {
            _viewState.apply {
                useCase.updatePostFavoriteStatus(it)
            }
        }
    }
}