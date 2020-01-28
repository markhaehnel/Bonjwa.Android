package xyz.haehnel.bonjwa.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

interface BonjwaService {
    @GET("schedule")
    fun getSchedule(): Deferred<List<BonjwaScheduleItem>>

    @GET("events")
    fun getEvents(): Deferred<List<BonjwaEventItem>>
}
