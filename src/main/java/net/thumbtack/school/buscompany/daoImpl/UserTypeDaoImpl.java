package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.dao.Dao;
import net.thumbtack.school.buscompany.model.UserType;
import net.thumbtack.school.buscompany.utils.UserTypeEnum;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTypeDaoImpl extends DaoImplBase implements Dao<UserType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTypeDaoImpl.class);
    @Override
    public UserType findById(String id) {
        LOGGER.debug("DAO get UserType by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getUserTypeMapper(sqlSession).findById(Integer.parseInt(id));
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get School by Id {} {}", id, ex);
            throw ex;
        }
    }

    public UserType findByType(UserTypeEnum userTypeEnum) {
        LOGGER.debug("DAO get UserType by type {}", userTypeEnum.getType());
        try (SqlSession sqlSession = getSession()) {
            return getUserTypeMapper(sqlSession).findByType(userTypeEnum.getType());
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get School by type {} {}", userTypeEnum.getType(), ex);
            throw ex;
        }
    }

    @Override
    public List<UserType> findAll() {
        return null;
    }

    @Override
    public UserType insert(UserType object) {
        return null;
    }

    @Override
    public void remove(UserType object) {

    }

    @Override
    public void update(UserType object) {

    }
}
