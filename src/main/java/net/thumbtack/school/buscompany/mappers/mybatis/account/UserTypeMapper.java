package net.thumbtack.school.buscompany.mappers.mybatis.account;

import net.thumbtack.school.buscompany.model.UserType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserTypeMapper {
    @Select("SELECT * FROM user_type WHERE type=#{type}")
    UserType findByType(@Param("type") String type);

    @Select("SELECT * FROM user_type WHERE id=#{id}")
    UserType findById(@Param("id") int id);

    @Delete("DELETE FROM user_type")
    Integer deleteAll();
}
