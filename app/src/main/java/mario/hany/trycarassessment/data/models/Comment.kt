package mario.hany.trycarassessment.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Comment(
    var body: String?=null,
    var email: String?=null,
    @PrimaryKey
    var id: Int?=null,
    var name: String?=null,
    var postId: Int?=null
): RealmObject(), Serializable