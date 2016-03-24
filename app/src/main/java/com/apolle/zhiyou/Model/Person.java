package com.apolle.zhiyou.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by huangtao on 2016/3/2217:20.
 * modify by huangtao on 17:20
 */
public class Person implements Serializable, Parcelable{

    private String uid;
    private String username;
    private String nickname;
    private String headpic;

    public Person() {
    }
    public Person(Parcel in){
          this.uid=in.readString();
          this.username=in.readString();
          this.nickname=in.readString();
          this.headpic=in.readString();

    }

    public Person(String username, String nickname, String headpic, String uid) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.headpic = headpic;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadpic() {
        return headpic;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(headpic);
    }
    public static final Parcelable.Creator<Person> CREATOR=new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
              Person person=new Person(source);
            return person;
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

}
