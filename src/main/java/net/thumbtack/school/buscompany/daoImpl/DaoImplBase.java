package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.mappers.mybatis.account.AccountMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.account.UserTypeMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.order.OrderMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.order.OrderPassengerMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.order.PassengerMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.trip.*;
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

    protected BusMapper getBusMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(BusMapper.class);
    }

    protected StationMapper getStationMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(StationMapper.class);
    }

    protected ScheduleMapper getScheduleMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ScheduleMapper.class);
    }

    protected TripMapper getTripMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(TripMapper.class);
    }

    protected DateTripMapper getDateTripMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DateTripMapper.class);
    }

    protected OrderMapper getOrderMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(OrderMapper.class);
    }

    protected PassengerMapper getPassengerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PassengerMapper.class);
    }

    protected OrderPassengerMapper getOrderPassengerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(OrderPassengerMapper.class);
    }
}