package lk.ijse.dep.pos.business.custom.impl;


import lk.ijse.dep.pos.dto.OrderDTO2;

import java.util.List;

class OrderBOImplTest {
    public static void main(String[] args) throws Exception {
        OrderBOImplTest orderBOImplTest = new OrderBOImplTest();
        orderBOImplTest.getOrderInfo();
    }

    void getOrderInfo() throws Exception {
        OrderBOImpl orderBO = new OrderBOImpl();
        List<OrderDTO2> orderInfo = orderBO.getOrderInfo();

    }
}