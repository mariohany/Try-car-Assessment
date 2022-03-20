package mario.hany.trycarassessment.ui.posts.usecases

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.realm.RealmList
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.data.models.Post

interface IPostsUseCase {
    fun getPosts(): Single<List<Post>>
    fun getPostComments(postId: Int): Observable<RealmList<Comment>>
    fun savePostsLocally(posts: List<Post>)
    fun updatePostFavoriteStatus(post: Post)
    fun getFavoritePosts(): Single<List<Post>>
}