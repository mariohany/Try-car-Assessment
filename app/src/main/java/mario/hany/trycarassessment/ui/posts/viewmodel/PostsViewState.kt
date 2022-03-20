package mario.hany.trycarassessment.ui.posts.viewmodel

import mario.hany.trycarassessment.data.models.Post

sealed class PostsViewState{
    object Loading : PostsViewState()
    class Posts(val list: List<Post>): PostsViewState()
    class Error(val msg:String): PostsViewState()
}
