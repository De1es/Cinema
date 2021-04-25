package by.itacademy.andrei_delesevich.cinema.model.user;

public enum UserAccessLevel {
    ADMIN("admin"),
    MANAGER("manager"),
    USER("user");

    String access;

    UserAccessLevel(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }


}



