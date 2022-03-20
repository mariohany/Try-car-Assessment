package mario.hany.trycarassessment.ui.posts.repos

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.data.models.Post
import mario.hany.trycarassessment.data.network.ApiInterface

class PostsRepository(private val remote: ApiInterface, private val realm: Realm) :
    IPostsRepository {
    override fun getPosts(): Single<List<Post>> {
        realm.where(Post::class.java).findAll()?.let {
            if (!it.isNullOrEmpty())
                return Single.just(it)
        }

        return remote.getPosts().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .flatMapIterable { items -> items }
            .flatMap { item ->
                getPostComments(item.id!!).map {
                    Post(
                        item.body,
                        item.id,
                        item.title,
                        item.userId,
                        it
                    )
                }
            }.toList()
    }

    override fun getPostComments(postId: Int): Observable<RealmList<Comment>> =
        remote.getPostComments(postId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    override fun savePostsLocally(posts: List<Post>) {
        realm.executeTransaction {
            it.copyToRealmOrUpdate(posts)
        }
    }

    override fun updatePostFavoriteStatus(post: Post) {
        realm.executeTransaction {
            post.isFavorite = !(post.isFavorite ?: false)
            it.copyToRealmOrUpdate(post)
        }
    }

    override fun getFavoritePosts(): Single<List<Post>> =
        Single.just(realm.where(Post::class.java).equalTo("isFavorite", true).findAll())

}