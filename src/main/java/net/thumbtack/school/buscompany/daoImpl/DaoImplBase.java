package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.mappers.mybatis.AccountMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.UserTypeMapper;
import net.thumbtack.school.buscompany.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Component
public class DaoImplBase {

    protected SqlSession getSession() {
        SqlSessionFactory factory = MyBatisUtils.getSqlSessionFactory();
        return factory.openSession();
    }

    protected AccountMapper getAccountMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AccountMapper.class);
    }

    protected UserTypeMapper getUserTypeMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserTypeMapper.class);
    }
}