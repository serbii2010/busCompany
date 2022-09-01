package net.thumbtack.school.buscompany.model;

public enum UserType {
    ADMIN("admin"),
    CLIENT("client");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
