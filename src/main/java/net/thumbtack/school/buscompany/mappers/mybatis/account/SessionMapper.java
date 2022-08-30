package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.Session;
import org.apache.ibatis.annotations.*;

public interface SessionMapper {
    @Select("SELECT * FROM session WHERE session_id=#{id}")
    Session getAccountBySessionId(String id);

    @Select("SELECT * FROM session WHERE account_id=id")
    Session getAccountByAccountId(int id);

    @Delete("DELETE FROM session WHERE id=#{id}")
    Integer delete(Session session);

    @Update("UPDATE session SET " +
            "account_id=#{account.id}, " +
            "session_id=#{sessionId}, " +
            "last_action=#{lastAction}, " +
            "WHERE id=#{id}")
    Integer update(Session session);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO session (account_id, session_id, last_action) " +
            "VALUES (#{account.id}, #{sessionId}, #{lastAction})")
    void insert(Session session);
}
