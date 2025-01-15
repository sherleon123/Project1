package model

data class RecordLeaderboard private constructor(
    val topRecord: MutableList<Record>

) {
    class Builder(
        var topRecord: MutableList<Record> = mutableListOf()

    ) {
        fun addRecord(record: Record) = apply { (this.topRecord as MutableList).add(record) }
        fun build() = RecordLeaderboard(topRecord)


    }
    fun addNewScore(record: Record) {
        topRecord.add(record)

        // Sort the list in descending order (top scores at the beginning)
        topRecord.sortByDescending{it.record}

        // Limit to top 5 scores
        if (topRecord.size > 10) {
            topRecord.removeAt(topRecord.lastIndex)  // Remove the lowest score
        }
    }
}