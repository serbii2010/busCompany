package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
    @Insert("INSERT INTO `account` (login, password, first_name, last_name, patronymic, " +
                "email, phone, position, user_type_id) " +
            "VALUES ( #{account.login}, #{account.password}, #{account.firstName}, #{account.lastName}, #{account.patronymic}, " +
                "#{account.email}, #{account.phone}, #{account.position}, #{account.userType} )")
    @Options(useGeneratedKeys = true, keyProperty = "account.id")
    Integer insert(@Param("account") Account account);

    @Select("SELECT id, login, password, first_name as firstName, last_name as lastName, patronymic, " +
                "email, phone, position, user_type_id as userType " +
            "FROM account WHERE login=#{login}")
    Account getByLogin(String login);
}
