package mario.hany.trycarassessment.data.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Post(
    var body: String? = null,
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var userId: Int? = null,
    var comments: RealmList<Comment>? = null,
    var isFavorite: Boolean? = false
): RealmObject(), Serializable