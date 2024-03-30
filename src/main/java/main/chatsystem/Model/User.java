package main.chatsystem.Model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("nickname")
    private final String nickname;
    @SerializedName("password")
    private final String password;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, password);
    }
}
