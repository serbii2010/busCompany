package net.thumbtack.school.buscompany.utils;

// REVU и так ясно, что enum. Просто UserType
// и не в utils, а в model его. Это часть модели
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
