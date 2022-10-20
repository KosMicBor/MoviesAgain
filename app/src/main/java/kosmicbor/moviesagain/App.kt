package kosmicbor.moviesagain

import android.app.Application
import kosmicbor.moviesagain.di.networkModule
import kosmicbor.moviesagain.di.useCasesModule
import kosmicbor.moviesagain.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                useCasesModule,
                viewModelsModule
            )
        }

    }
}