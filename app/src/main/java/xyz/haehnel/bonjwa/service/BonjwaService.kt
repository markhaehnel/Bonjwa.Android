package xyz.haehnel.bonjwa.service

import retrofit2.http.GET
import xyz.haehnel.bonjwa.model.BonjwaEventItem
import xyz.haehnel.bonjwa.model.BonjwaScheduleItem

interface BonjwaService {
    @GET("schedule")
    suspend fun getSchedule(): List<BonjwaScheduleItem>

    @GET("events")
    suspend fun getEvents(): List<BonjwaEventItem>
}
