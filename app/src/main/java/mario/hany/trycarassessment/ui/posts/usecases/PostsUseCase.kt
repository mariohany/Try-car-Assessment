package mario.hany.trycarassessment.ui.posts.usecases

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.realm.RealmList
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.ui.posts.repos.IPostsRepository

class PostsUseCase(private val repo: IPostsRepository): IPostsUseCase {
    override fun getPosts(): Single<List<Post>> = repo.getPosts()

    override fun getPostComments(postId: Int): Observable<RealmList<Comment>> = repo.getPostComments(postId)

    override fun savePostsLocally(posts: List<Post>) = repo.savePostsLocally(posts)

    override fun updatePostFavoriteStatus(post: Post) = repo.updatePostFavoriteStatus(post)

    override fun getFavoritePosts(): Single<List<Post>> = repo.getFavoritePosts()
}