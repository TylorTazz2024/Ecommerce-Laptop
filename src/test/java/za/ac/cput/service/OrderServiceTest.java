//package za.ac.cput.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import za.ac.cput.domain.Order;
//import za.ac.cput.repository.OrderRepository;
//
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private Order order;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        order = OrderFactory.createOrder(1, new Date(), "Pending", 300.00);
//    }
//
//    @Test
//    void testSaveOrder() {
//        when(orderRepository.save(order)).thenReturn(order);
//
//        Order saved = orderService.save(order);
//
//        assertNotNull(saved);
//        assertEquals(1, saved.getOrderID());
//        verify(orderRepository, times(1)).save(order);
//    }
//
//    @Test
//    void testFindById() {
//        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
//
//        Optional<Order> found = orderService.findById(1);
//
//        assertTrue(found.isPresent());
//        assertEquals("Pending", found.get().getStatus());
//    }
//}
