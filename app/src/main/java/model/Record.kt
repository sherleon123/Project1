package model

data class Record private constructor(
    val record: Int,
    var lon : Double,
    var lat : Double,)
{
    class Builder(
    ) {
        var record: Int = -1
        var lon : Double=0.0
        var lat : Double=0.0
        fun record(record: Int) = apply { this.record = record }
        fun lon(lon: Double)=apply { this.lon=lon }
        fun lat(lat: Double)=apply { this.lat=lat }
        fun build() = Record(record,lon,lat)
    }
}