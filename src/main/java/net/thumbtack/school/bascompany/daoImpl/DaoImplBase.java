package net.thumbtack.school.bascompany.daoImpl;

import net.thumbtack.school.bascompany.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }



}