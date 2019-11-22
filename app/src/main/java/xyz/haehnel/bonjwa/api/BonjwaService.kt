package xyz.haehnel.bonjwa.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import java.time.Instant

interface BonjwaService {
    @GET("schedule")
    fun getSchedule(): Deferred<List<BonjwaScheduleItem>>
}

class BonjwaScheduleItem (
    val title: String,
    val caster: String,
    val startDate: Instant,
    val endDate: Instant,
    val cancelled: Boolean
)