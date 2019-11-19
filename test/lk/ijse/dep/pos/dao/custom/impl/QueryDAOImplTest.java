package lk.ijse.dep.pos.dao.custom.impl;


import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.hibernate.Session;

import java.util.List;

class QueryDAOImplTest {
    public static void main(String[] args) throws Exception {
        new QueryDAOImplTest().getOrdersInfo3();
    }

    void getOrdersInfo3() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        QueryDAOImpl queryDAO = new QueryDAOImpl();
        queryDAO.setSession(session);

        session.beginTransaction();
        List<CustomEntity> ordersInfo3 = queryDAO.getOrdersInfo3();

        session.getTransaction().commit();
    }
}