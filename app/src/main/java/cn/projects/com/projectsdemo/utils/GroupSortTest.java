package cn.projects.com.projectsdemo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengxing on 17/3/2.
 */

public class GroupSortTest {

    public void testList() {
        List<Order> list = new ArrayList<Order>();
        Order o1 = new Order("10086", "3", (long) 1008603);
        Order o2 = new Order("10086", "2", (long) 1008602);
        Order o3 = new Order("10086", "1", (long) 1008601);
        Order o4 = new Order("10010", "1", (long) 1001001);
        Order o5 = new Order("10000", "3", (long) 1000003);
        Order o6 = new Order("10010", "3", (long) 1001003);
        Order o7 = new Order("10000", "1", (long) 1000001);
        Order o8 = new Order("10000", "2", (long) 1000002);
        Order o9 = new Order("10010", "2", (long) 1001002);

        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        list.add(o5);
        list.add(o6);
        list.add(o7);
        list.add(o8);
        list.add(o9);

        Map<String, List<Order>> map = OrderSortByGroup(list);

        for (Map.Entry<String, List<Order>> entry : map.entrySet()) {
            for (Order o : entry.getValue()) {
                System.out.println(entry.getKey() + "--" + o.getSeq() + "--" + o.getStatusId());
            }
        }
    }

    public Map<String, List<Order>> OrderSortByGroup(List<Order> list) {
        Map<String, List<Order>> map = new HashMap<String, List<Order>>();
        for (Order order : list) {
            List<Order> staList = map.get(order.orderNo);
            if (staList == null) {
                staList = new ArrayList<Order>();
            }
            staList.add(order);

            Collections.sort(staList, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return o1.getSeq().compareTo(o2.getSeq());
                }
            });

            map.put(order.orderNo, staList);
        }
        return map;
    }

    class Order {
        String orderNo;    //订单号
        String seq;        //订单节点序号
        Long statusId;    //订单节点主键

        public Order() {
        }

        public Order(String orderNo, String seq, Long statusId) {
            super();
            this.orderNo = orderNo;
            this.seq = seq;
            this.statusId = statusId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public Long getStatusId() {
            return statusId;
        }

        public void setStatusId(Long statusId) {
            this.statusId = statusId;
        }
    }
}
