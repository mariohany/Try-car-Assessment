package mario.hany.trycarassessment.di

import mario.hany.trycarassessment.ui.posts.viewmodel.PostDetailsViewModel
import mario.hany.trycarassessment.ui.posts.viewmodel.PostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { PostsViewModel(get()) }
    viewModel { PostDetailsViewModel(get()) }
}