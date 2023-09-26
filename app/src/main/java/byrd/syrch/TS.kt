package byrd.syrch

import android.os.Parcel
import android.os.Parcelable

class TS(var dateAdded: String?, var birdName: String?, var location: String?) :
Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dateAdded)
        parcel.writeString(birdName)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TS> {
        override fun createFromParcel(parcel: Parcel): TS {
            return TS(parcel)
        }

        override fun newArray(size: Int): Array<TS?> {
            return arrayOfNulls(size)
        }
    }

}