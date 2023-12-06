package com.proco.domain.model.schedule

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.proco.domain.model.time.HoursOfDay


@Stable
data class Schedule(
    val dayName: DayName,
    val hours: SnapshotStateList<HoursOfDay>
)

enum class DayName(val index: Int) {
    Saturday(0),
    Sunday(1),
    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
}
