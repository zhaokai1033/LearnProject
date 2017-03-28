package com.zk.sample.module.recycle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GitUser implements Parcelable {

    /**
     * login : zhaokai1033
     * id : 14001437
     * avatar_url : https://avatars1.githubusercontent.com/u/14001437?v=3
     * score : 20.975086
     */

//    private String avatar_url;

    public long id;
    public String login;
    @SerializedName("avatar_url")
    public String avatar_url;
    public double score;

    public GitUser() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeDouble(this.score);
        dest.writeString(this.login);
        dest.writeString(this.avatar_url);
    }

    protected GitUser(Parcel in) {
        this.id = in.readLong();
        this.login = in.readString();
        this.avatar_url = in.readString();
        this.score = in.readDouble();
    }

    public static final Creator<GitUser> CREATOR = new Creator<GitUser>() {
        public GitUser createFromParcel(Parcel source) {
            return new GitUser(source);
        }

        public GitUser[] newArray(int size) {
            return new GitUser[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitUser user = (GitUser) o;

        if (id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        return !(avatar_url != null ? !avatar_url.equals(user.avatar_url) : user.avatar_url != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (avatar_url != null ? avatar_url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GitUser{" +
                "login='" + login + '\'' +
                ", score=" + score +
                '}';
    }
}
