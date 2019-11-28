package xyz.haehnel.bonjwa.repo

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.threeten.bp.Instant
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.haehnel.bonjwa.api.BonjwaScheduleItem
import xyz.haehnel.bonjwa.api.BonjwaService

class ScheduleRepository {
    suspend fun getSchedule(): List<BonjwaScheduleItem> {

        val moshi = Moshi.Builder().add(InstantAdapter()).add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.bonjwa.ezhub.de")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val api = retrofit.create(BonjwaService::class.java)

        return api.getSchedule().await() //TODO: Wait for compose bugfix (https://issuetracker.google.com/issues/143468771)
    }
}

class InstantAdapter {
    @ToJson
    @Suppress("unused")
    fun toJson(instant: Instant): String {
        return instant.toString()
    }

    @FromJson
    @Suppress("unused")
    fun fromJson(instant: String): Instant? {
        return Instant.parse(instant)
    }
}