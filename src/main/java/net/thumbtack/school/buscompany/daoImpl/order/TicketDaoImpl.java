//package net.thumbtack.school.buscompany.daoImpl.order;
//
//import net.thumbtack.school.buscompany.dao.Dao;
//import net.thumbtack.school.buscompany.daoImpl.DaoImplBase;
//import net.thumbtack.school.buscompany.exception.ServerException;
//import net.thumbtack.school.buscompany.model.OrderPassenger;
//import net.thumbtack.school.buscompany.model.Passenger;
//import net.thumbtack.school.buscompany.model.Ticket;
//import org.apache.ibatis.session.SqlSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class TicketDaoImpl extends DaoImplBase implements Dao<Ticket> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDaoImpl.class);
//    @Override
//    public Ticket findById(String id) throws ServerException {
//        return null;
//    }
//
//    @Override
//    public List<Ticket> findAll() {
//        return null;
//    }
//
//    @Override
//    public Ticket insert(Ticket ticket) {
//        LOGGER.debug("DAO insert Ticket {}", ticket);
//        try (SqlSession sqlSession = getSession()) {
//            try {
//                Ticket ticketOld = getTicketMapper(sqlSession).findByOrderPassengerId(String.valueOf(ticket.getOrderPassenger().getId()));
//                if (ticketOld != null) {
//                    getTicketMapper(sqlSession).delete(ticketOld);
//                }
//                getTicketMapper(sqlSession).insert(ticket);
//
//            } catch (RuntimeException ex) {
//                LOGGER.info("Can't insert Order {} {}", ticket, ex);
//                sqlSession.rollback();
//                throw ex;
//            }
//            sqlSession.commit();
//        }
//        return ticket;
//    }
//
//    @Override
//    public void remove(Ticket object) {
//
//    }
//
//    @Override
//    public void update(Ticket object) {
//
//    }
//
//    public Passenger getPassenger(String firstName, String lastName, String passport) {
//        LOGGER.debug("DAO get Passenger");
//        try (SqlSession sqlSession = getSession()) {
//            return getTicketMapper(sqlSession).findPassenger(firstName, lastName, passport);
//        } catch (RuntimeException ex) {
//            LOGGER.info("Can't get Passenger {}", ex);
//            throw ex;
//        }
//    }
//
//    public OrderPassenger getOrderPassenger(String orderId, Passenger passenger) {
//        LOGGER.debug("DAO get Passenger");
//        try (SqlSession sqlSession = getSession()) {
//            return getTicketMapper(sqlSession).selectOrderPassenger(orderId, passenger);
//        } catch (RuntimeException ex) {
//            LOGGER.info("Can't get Passenger {}", ex);
//            throw ex;
//        }
//    }
//}
