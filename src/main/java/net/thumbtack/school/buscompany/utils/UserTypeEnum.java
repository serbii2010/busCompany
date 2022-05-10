package net.thumbtack.school.buscompany.utils;

public enum UserTypeEnum {
    ADMIN("admin"),
    CLIENT("client");

    private String type;

    UserTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
