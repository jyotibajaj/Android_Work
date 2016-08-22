package letsdecode.com.macyapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aashi on 8/21/16.
 */
class FileSize implements Parcelable {
    long size;
    String path;

    public FileSize(long size, String path) {
        this.size = size;
        this.path = path;
    }

    public FileSize() {
    }

    /**
     * Used to give additional hints on how to process the received parcel.
     */
    @Override
    public int describeContents() {
// ignore for now
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeLong(size);
        pc.writeString(path);
    }

    /**
     * Static field used to regenerate object, individually or as arrays
     */
    public static final Parcelable.Creator<FileSize> CREATOR = new Parcelable.Creator<FileSize>() {
        public FileSize createFromParcel(Parcel pc) {
            return new FileSize(pc);
        }

        public FileSize[] newArray(int size) {
            return new FileSize[size];
        }
    };

    /**
     * Ctor from Parcel, reads back fields IN THE ORDER they were written
     */
    public FileSize(Parcel pc) {
        size = pc.readLong();
        path = pc.readString();
    }
}
