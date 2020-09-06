package xyz.haehnel.bonjwa.repo

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
        .build()

    override suspend fun getSchedule(): List<BonjwaScheduleItem> {
        val api = retrofit.create(BonjwaService::class.java)
        return api.getSchedule()
    }

    override suspend fun getEvents(): List<BonjwaEventItem> {
        val api = retrofit.create(BonjwaService::class.java)
        return api.getEvents()
    }
}

interface IScheduleRepository {
    suspend fun getSchedule(): List<BonjwaScheduleItem>
    suspend fun getEvents(): List<BonjwaEventItem>
}


