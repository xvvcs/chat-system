package main.chatsystem.Model;

import com.google.gson.annotations.SerializedName;

public record User(@SerializedName("nickname") String nickname, @SerializedName("password") String password)
{

    @Override public String nickname()
    {
        return nickname;
    }

    @Override public String password()
    {
        return password;
    }

    @Override public String toString()
    {
        return "User{" + "nickname='" + nickname + '\'' + ", password='" + password + '\'' + '}';
    }

}
