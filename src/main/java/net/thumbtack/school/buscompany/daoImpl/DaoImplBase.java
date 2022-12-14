package net.thumbtack.school.buscompany.daoImpl;

import net.thumbtack.school.buscompany.mappers.mybatis.account.AccountMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.account.AdminMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.account.ClientMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.account.SessionMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.order.OrderMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.order.PassengerMapper;
import net.thumbtack.school.buscompany.mappers.mybatis.trip.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoImplBase {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    protected SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }

    protected AccountMapper getAccountMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AccountMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected ClientMapper getClientMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ClientMapper.class);
    }

    protected AdminMapper getAdminMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AdminMapper.class);
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

    protected PlaceMapper getPlaceMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PlaceMapper.class);
    }

}