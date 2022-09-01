package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import org.apache.ibatis.annotations.*;

public interface AdminMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO admin (account_id, position) " +
            "VALUES (#{id}, #{position})")
    Integer insert(Admin client);

    @Select("SELECT * FROM admin LEFT JOIN account ON account.id=admin.account_id WHERE account_id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type")
    })
    Admin findAdmin(Account account);

    @Update("UPDATE admin SET " +
            "position=#{position} " +
            "WHERE id=#{id}")
    Integer update(Admin admin);

    @Delete("DELETE FROM admin WHERE account_id=#{id}")
    Integer deleteByAccount(Account account);

    @Select("SELECT count(id) FROM admin")
    Integer getCount();

    @Update("ALTER TABLE admin AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
