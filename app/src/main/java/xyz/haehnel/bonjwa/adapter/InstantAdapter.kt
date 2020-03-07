package xyz.haehnel.bonjwa.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.Instant

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