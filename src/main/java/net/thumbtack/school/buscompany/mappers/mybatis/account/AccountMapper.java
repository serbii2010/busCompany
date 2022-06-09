package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.Account;
import net.thumbtack.school.buscompany.model.UserType;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {
    @Insert("INSERT INTO `account` (login, password, first_name, last_name, patronymic, " +
            "email, phone, position, user_type_id) " +
            "VALUES ( #{account.login}, #{account.password}, #{account.firstName}, #{account.lastName}, #{account.patronymic}, " +
            "#{account.email}, #{account.phone}, #{account.position}, #{account.userType.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "account.id")
    Integer insert(@Param("account") Account account);

    @Select("SELECT * FROM account WHERE id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type_id",
                    one = @One(select = "getUserType"))
    })
    Account getById(String id);

    @Select("SELECT * FROM account WHERE user_type_id=#{userType}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type_id",
                    one = @One(select = "getUserType"))
    })
    List<Account> getByUserType(Integer userType);

    @Select("SELECT * FROM account WHERE login=#{login}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type_id",
                    one = @One(select = "getUserType"))
    })
    Account getByLogin(String login);

    @Select("SELECT * FROM user_type WHERE id=#{userType}")
    UserType getUserType(String userType);

    @Update("UPDATE account SET " +
            "first_name=#{account.firstName}, " +
            "last_name=#{account.lastName}, " +
            "patronymic=#{account.patronymic}, " +
            "position=#{account.position}, " +
            "password=#{account.password}, " +
            "email=#{account.email}, " +
            "phone=#{account.phone} " +
            "WHERE id=#{account.id}")
    Integer update(@Param("account") Account account);

    @Delete("DELETE FROM account WHERE id=#{account.id}")
    void delete(@Param("account") Account account);

    @Delete("DELETE FROM account")
    Integer deleteAll();
}
