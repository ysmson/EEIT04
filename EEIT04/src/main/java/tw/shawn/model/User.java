package tw.shawn.model;

public class User {
    private int userId;
    private String username;
    private String password;

    // 建構子 (constructor)
    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 你可以在這裡加入其他與使用者相關的屬性和方法
}