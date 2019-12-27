package xyz.haehnel.bonjwa.model

import org.threeten.bp.Instant

class BonjwaScheduleItem (
    val title: String,
    val caster: String,
    val startDate: Instant,
    val endDate: Instant,
    val cancelled: Boolean
)