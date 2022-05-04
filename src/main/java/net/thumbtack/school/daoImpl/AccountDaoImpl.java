package net.thumbtack.school.daoImpl;

import net.thumbtack.school.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountDaoImpl extends DaoImplBase implements Dao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Override
    public Object findById(String id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void insert(Object object) {

    }

    @Override
    public void remove(Object object) {

    }
}
