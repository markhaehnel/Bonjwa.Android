package xyz.haehnel.bonjwa.model

import com.squareup.moshi.Json
import org.threeten.bp.Duration
import org.threeten.bp.Instant

class BonjwaScheduleItem(
    val title: String,
    val caster: String,
    val startDate: Instant,
    @Json(name = "endDate")
    val _endDate: Instant,
    val cancelled: Boolean,
) {
    val endDate: Instant
        get() {
            return if (_endDate.isBefore(startDate)) _endDate.plus(Duration.ofDays(1)) else _endDate
        }
    var isRunning: Boolean = false
        get() {
            val now = Instant.now()
            return startDate.isBefore(now) && endDate.isAfter(now)
        }
        private set
}
