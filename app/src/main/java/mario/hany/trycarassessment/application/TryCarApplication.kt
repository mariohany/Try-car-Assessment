package mario.hany.trycarassessment.application

import android.app.Application
import io.realm.Realm
import mario.hany.trycarassessment.di.networkModule
import mario.hany.trycarassessment.di.repositoriesModule
import mario.hany.trycarassessment.di.useCasesModule
import mario.hany.trycarassessment.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TryCarApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidContext(this@TryCarApplication)
            modules(
                listOf(
                    networkModule, repositoriesModule, viewModelsModule, useCasesModule
                )
            )
        }
    }
}