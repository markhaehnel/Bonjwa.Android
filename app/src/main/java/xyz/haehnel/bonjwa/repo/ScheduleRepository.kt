package xyz.haehnel.bonjwa.repo

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.haehnel.bonjwa.adapter.InstantAdapter
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem
import xyz.haehnel.bonjwa.service.BonjwaService

object ScheduleRepository : IScheduleRepository {
    private const val baseUrl = "https://api.bonjwa.ezhub.de"
    private val moshi = Moshi.Builder()
        .add(InstantAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    override fun getSchedule(): Deferred<List<BonjwaScheduleItem>> {
        val api = retrofit.create(BonjwaService::class.java)
        return api.getSchedule() // TODO: Wait for compose bugfix (https://issuetracker.google.com/issues/143468771)
    }

    override fun getEvents(): Deferred<List<BonjwaEventItem>> {
        val api = retrofit.create(BonjwaService::class.java)
        return api.getEvents() // TODO: Wait for compose bugfix (https://issuetracker.google.com/issues/143468771)
    }
}

interface IScheduleRepository {
    fun getSchedule(): Deferred<List<BonjwaScheduleItem>>
    fun getEvents(): Deferred<List<BonjwaEventItem>>
}


