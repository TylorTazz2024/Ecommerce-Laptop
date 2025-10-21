//package za.ac.cput.factory;
//
//import org.junit.jupiter.api.Test;
//import za.ac.cput.domain.Order;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OrderFactoryTest {
//
//    @Test
//    void testCreateOrderSuccess() {
//        Order order = OrderFactory.createOrder(1, new Date(), "Pending", 500.00);
//
//        assertNotNull(order);
//        assertEquals(1, order.getOrderID());
//        assertEquals("Pending", order.getStatus());
//        assertEquals(500.00, order.getTotalAmount());
//    }
//
//    @Test
//    void testCreateOrderFailure() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () ->
//                OrderFactory.createOrder(1, null, "Pending", 500.00));
//
//        assertEquals("Order date is required", exception.getMessage());
//    }
//}
