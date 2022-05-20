package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {
    @Insert("INSERT INTO `account` (login, password, first_name, last_name, patronymic, " +
                "email, phone, position, user_type_id) " +
            "VALUES ( #{account.login}, #{account.password}, #{account.firstName}, #{account.lastName}, #{account.patronymic}, " +
                "#{account.email}, #{account.phone}, #{account.position}, #{account.userType} )")
    @Options(useGeneratedKeys = true, keyProperty = "account.id")
    Integer insert(@Param("account") Account account);

    @Select("SELECT id, login, password, first_name as firstName, last_name as lastName, patronymic, " +
                "email, phone, position, user_type_id as userType " +
            "FROM account WHERE id=#{id}")
    Account getById(String id);

    @Select("SELECT id, login, password, first_name as firstName, last_name as lastName, patronymic, " +
            "email, phone, position, user_type_id as userType " +
            "FROM account WHERE user_type_id=#{userType}")
    List<Account> getByUserType(Integer userType);

    @Select("SELECT id, login, password, first_name as firstName, last_name as lastName, patronymic, " +
                "email, phone, position, user_type_id as userType " +
            "FROM account WHERE login=#{login}")
    Account getByLogin(String login);

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
    void delete(@Param("account")Account account);
}
