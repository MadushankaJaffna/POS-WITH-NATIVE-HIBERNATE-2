package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.xml.crypto.dsig.Transform;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {
        return (CustomEntity) session.createQuery("SELECT NEW lk.ijse.dep.pos.entity.CustomEntity(o.id, c.id,c.name,o.date) FROM Customer c INNER JOIN c.orders o WHERE o.id=?1")
                .setParameter(1, orderId)
                .uniqueResult();
    }

    @Override
    public CustomEntity getOrderInfo2(int orderId) throws Exception {
        return session.createQuery("SELECT O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unitPrice) AS Total  FROM Customer C INNER JOIN C.`Order` O  INNER JOIN O.OrderDetail OD WHERE O.id=?1 GROUP BY orderId",
                CustomEntity.class).setParameter(1, orderId).uniqueResult();
    }

    @Override
    public List<CustomEntity> getOrdersInfo3() throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT O.id as orderId, Cu.id as customerId, Cu.name as customerName, O.date as orderDate, SUM(OD.qty * OD.unit_price) AS orderTotal FROM Customer Cu INNER JOIN " +
                "`Order` O ON Cu.id=O.customer_id INNER JOIN OrderDetail OD ON O.id = OD.order_id GROUP BY O.id");

        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();
        return list;
    }
}
