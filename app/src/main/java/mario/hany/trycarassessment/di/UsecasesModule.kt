package mario.hany.trycarassessment.di

import mario.hany.trycarassessment.ui.posts.usecases.IPostsUseCase
import mario.hany.trycarassessment.ui.posts.usecases.PostsUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single<IPostsUseCase> { PostsUseCase(get()) }

}