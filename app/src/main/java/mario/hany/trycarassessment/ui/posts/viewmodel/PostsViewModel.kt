package mario.hany.trycarassessment.ui.posts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mario.hany.trycarassessment.ui.posts.usecases.IPostsUseCase

class PostsViewModel(private val useCase: IPostsUseCase) : ViewModel() {
    private val _viewState: MutableLiveData<PostsViewState> by lazy { MutableLiveData() }
    val viewState: LiveData<PostsViewState> = _viewState

    fun getPosts() {
        _viewState.apply {
            value = PostsViewState.Loading
                useCase.getPosts()
                .subscribe({
                    useCase.savePostsLocally(it)
                    value = PostsViewState.Posts(it)
                }, {
                    value = PostsViewState.Error(it.localizedMessage ?: "")
                })
        }
    }

    fun getFavoritePosts() {
        _viewState.apply {
            value = PostsViewState.Loading
            useCase.getFavoritePosts()
                .subscribe({
                    value = PostsViewState.Posts(it)
                }, {
                    value = PostsViewState.Error(it.localizedMessage ?: "")
                })
        }
    }
}