package com.example.checkbox.checkbox;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkboxes")
public class checkbox implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Naslov")
    private String naslov;
    @ColumnInfo(name = "Datum")
    private String datumKonca;
    @ColumnInfo(name = "Cas")
    private String cas;
    @ColumnInfo(name="CState")
    private int CheckboxState;


    public checkbox(String naslov,int id,String cas,  String datumKonca,int CheckboxState) {
        this.naslov = naslov;
        this.id=id;
        this.datumKonca = datumKonca;
        this.cas=cas;
        this.CheckboxState=CheckboxState;
    }
    @Ignore
    public checkbox() {
    }


    protected checkbox(Parcel in) {
        id = in.readInt();
        naslov = in.readString();
        datumKonca = in.readString();
        cas=in.readString();
    }

    public static final Creator<checkbox> CREATOR = new Creator<checkbox>() {
        @Override
        public checkbox createFromParcel(Parcel in) {
            return new checkbox(in);
        }

        @Override
        public checkbox[] newArray(int size) {
            return new checkbox[size];
        }
    };

    public int getCheckboxState() {
        return CheckboxState;
    }

    public void setCheckboxState(int checkboxState) {
        CheckboxState = checkboxState;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatumKonca() {
        return datumKonca;
    }

    public void setDatumKonca(String datumKonca) {
        this.datumKonca = datumKonca;
    }

    @Override
    public String toString() {
        return "checkbox{" +
                "id=" + id +
                ", naslov='" + naslov + '\'' +
                ", datumKonca='" + datumKonca + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(naslov);
        dest.writeString(datumKonca);
        dest.writeString(cas);
    }
}
