package xyz.haehnel.bonjwa.model

import org.threeten.bp.Instant

class BonjwaScheduleItem(
    val title: String,
    val caster: String,
    val startDate: Instant,
    val endDate: Instant,
    val cancelled: Boolean
) {
    var isRunning: Boolean = false
        get() {
            val now = Instant.now()
            return startDate.isBefore(now) && endDate.isAfter(now)
        }
        private set
}
