package com.okay.sampletamplate.sample.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * @author Created by yuetao
 * @date 2019-08-27 16:59
 * @email yuetao@okay.cn
 *
 * @desc 账号实体类
 */
public class AccountInfo implements Parcelable {
    public String id;

    public String name;

    public String token;
    //表示账号当前状态（-2:停用 -1:待激活 0:异常 1:正常 ）
    public int status = 1;

    public AccountInfo(String id, String name, String token, int status) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountInfo)) return false;
        AccountInfo account = (AccountInfo) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                Objects.equals(token, account.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, token);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.token);
        dest.writeInt(this.status);
    }

    public AccountInfo() {
    }

    protected AccountInfo(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.token = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
