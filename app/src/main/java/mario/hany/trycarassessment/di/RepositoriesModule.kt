package mario.hany.trycarassessment.di

import io.realm.Realm
import io.realm.RealmConfiguration
import mario.hany.trycarassessment.ui.posts.repos.IPostsRepository
import mario.hany.trycarassessment.ui.posts.repos.PostsRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<IPostsRepository> { PostsRepository(get(), get()) }
    single { Realm.getInstance(get()) }
    single {
        RealmConfiguration.Builder().allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true).build()
    }
}