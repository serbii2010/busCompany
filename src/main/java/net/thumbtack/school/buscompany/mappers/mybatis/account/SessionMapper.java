package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.Session;
import net.thumbtack.school.buscompany.model.account.Account;
import net.thumbtack.school.buscompany.model.UserType;
import org.apache.ibatis.annotations.*;

public interface SessionMapper {
    @Select("SELECT * FROM session WHERE session_id=#{id}")
    @Results(value = {
            @Result(property = "sessionId", column = "session_id"),
            @Result(property = "lastAction", column = "last_action"),
            @Result(property = "account", javaType = Account.class, column = "account_id",
                    one = @One(select = "selectAccount"))
    })
    Session getAccountBySessionId(String id);

    @Select("SELECT * FROM session WHERE account_id=#{id}")
    @Results(value = {
            @Result(property = "sessionId", column = "session_id"),
            @Result(property = "lastAction", column = "last_action"),
            @Result(property = "account", javaType = Account.class, column = "account_id",
                    one = @One(select = "selectAccount"))
    })
    Session getAccountByAccountId(int id);

    @Delete("DELETE FROM session WHERE id=#{id}")
    Integer delete(Session session);

    @Update("UPDATE session SET " +
            "account_id=#{account.id}, " +
            "session_id=#{sessionId}, " +
            "last_action=#{lastAction} " +
            "WHERE id=#{id}")
    Integer update(Session session);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO session (account_id, session_id, last_action) " +
            "VALUES (#{account.id}, #{sessionId}, #{lastAction})")
    void insert(Session session);

    @Select("SELECT * FROM account WHERE id=#{id}")
    @Results(value = {
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "userType", javaType = UserType.class, column = "user_type")
    })
    Account selectAccount(String id);
}
