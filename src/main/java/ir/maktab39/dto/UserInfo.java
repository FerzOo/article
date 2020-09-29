package ir.maktab39.dto;

public class UserInfo {
    private String username;

    @Override
    public String toString() {
        return "username='" + username + '\'';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
