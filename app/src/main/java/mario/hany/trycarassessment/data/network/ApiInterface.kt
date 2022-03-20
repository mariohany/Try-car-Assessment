package mario.hany.trycarassessment.data.network

import io.reactivex.rxjava3.core.Observable
import io.realm.RealmList
import mario.hany.trycarassessment.data.models.Comment
import mario.hany.trycarassessment.data.models.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("posts/")
    fun getPosts(): Observable<List<Post>>

    @GET("posts/{post_id}/comments")
    fun getPostComments(@Path("post_id") postId:Int): Observable<RealmList<Comment>>
}