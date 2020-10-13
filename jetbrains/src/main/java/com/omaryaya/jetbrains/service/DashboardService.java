package com.omaryaya.jetbrains.service;

import java.util.List;

import com.omaryaya.jetbrains.model.OrderStatusCount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private OrderService ordersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemService itemsService;

    public Long getYearProfit() {
        return itemsService.getYearProfit();
    }

    public List<?> getProfitsForOrders() {
        return itemsService.getCostsOfAllOrders();
    }

	public List<OrderStatusCount> groupOrdersByStatus() {
		return ordersService.groupOrdersByStatus();
	}


}
