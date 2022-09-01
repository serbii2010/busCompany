package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.model.UserType;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {
    @Insert("INSERT INTO `account` (login, password, first_name, last_name, patronymic, user_type) " +
            "VALUES ( #{account.login}, #{account.password}, #{account.firstName}, #{account.lastName}, " +
            "#{account.patronymic}, #{account.userType} )")
    @Options(useGeneratedKeys = true, keyProperty = "account.id")
    Integer insert(@Param("account") Account account);

    @Select("SELECT * FROM account WHERE id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type")
    })
    Account getById(String id);

    @Select("SELECT * FROM account WHERE user_type='client'")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type")
    })
    List<Client> getClients();

    @Select("SELECT * FROM account WHERE login=#{login}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", column = "user_type")
    })
    Account getByLogin(String login);

    @Update("UPDATE account SET " +
            "first_name=#{account.firstName}, " +
            "last_name=#{account.lastName}, " +
            "patronymic=#{account.patronymic}, " +
            "password=#{account.password} " +
            "WHERE id=#{account.id}")
    Integer update(@Param("account") Account account, String accountId);

    @Delete("DELETE FROM account WHERE id=#{account.id}")
    void delete(@Param("account") Account account);

    @Delete("DELETE FROM account")
    Integer deleteAll();

    @Update("ALTER TABLE account AUTO_INCREMENT = 1")
    void resetAutoIncrement();
}
