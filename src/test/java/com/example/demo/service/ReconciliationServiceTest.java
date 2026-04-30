package com.example.demo.service;


import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReconciliationServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReconciliationService reconciliationService;

    @Test
    void shouldReturnOrderWhenOrderExists() {


        // Tworzenie sztucznego zamówienia
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setOrderNumber("ORD-001");
        mockOrder.setStatus(OrderStatus.PAID);

        // Jeśli order repository zostanie zapytany o order 1L ma zwrocić sztuczne zamówienie
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        // Uruchamiany reconciliation service tylko na metode która powinna nam zwrocić dane ordera
        Order result = reconciliationService.getOrderDetails(1L);

        // czy nie dostał przypadkiem znaczenia NULL
        assertNotNull(result);

        // porównywanie wynników z metody get order details
        assertEquals("ORD-001", result.getOrderNumber());
        assertEquals(OrderStatus.PAID, result.getStatus());

        // sprawdzenie czy serwis wywołał metode findby
        verify(orderRepository, times(1)).findById(1L);

    }
}
