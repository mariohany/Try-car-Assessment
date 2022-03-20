package mario.hany.trycarassessment.di

import mario.hany.trycarassessment.BuildConfig
import mario.hany.trycarassessment.data.network.ApiInterface
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = BuildConfig.BASE_URL

val networkModule = module {

    factory { provideOkHttpClient() }
    factory { provideRetrofit(get()) }
    factory { provideDealerRemoteService(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {

    return OkHttpClient().newBuilder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}

fun provideDealerRemoteService(retrofit: Retrofit): ApiInterface =
    retrofit.create(ApiInterface::class.java)
