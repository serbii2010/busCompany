package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.account.Admin;
import net.thumbtack.school.buscompany.model.account.Client;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.apache.ibatis.annotations.*;

public interface ClientMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO client (account_id, email, phone) " +
            "VALUES (#{id}, #{email}, #{phone})")
    Integer insert(Client client);

    @Select("SELECT * FROM client LEFT JOIN account ON account.id=account_id WHERE account_id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "userType", javaType = UserTypeEnum.class, column = "user_type")
    })
    Client findClient(Account account);

    @Update("UPDATE client SET " +
            "email=#{email}, " +
            "phone=#{phone} " +
            "WHERE id=#{id}")
    Integer update(Client client);

    @Delete("DELETE FROM client WHERE account_id=#{id}")
    Integer deleteByAccount(Account account);
}
