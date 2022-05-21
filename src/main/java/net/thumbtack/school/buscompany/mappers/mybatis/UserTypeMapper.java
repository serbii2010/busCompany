package net.thumbtack.school.buscompany.mappers.mybatis;

import net.thumbtack.school.buscompany.model.UserType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserTypeMapper {
    @Select("SELECT * FROM user_type WHERE type=#{type}")
    UserType findByType(@Param("type") String type);

    @Select("SELECT * FROM user_type WHERE id=#{id}")
    UserType findById(@Param("id") int id);
}
