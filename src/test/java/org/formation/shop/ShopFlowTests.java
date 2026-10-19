package org.formation.shop;

import org.formation.shop.catalog.Catalog;
import org.formation.shop.catalog.internal.CoffeeRepository;
import org.formation.shop.inventory.internal.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShopFlowTests {

    @Autowired
    private Catalog catalog;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private StockRepository stockRepository;

    @Test
    void creatingCoffeeShouldTriggerStockCreationViaEvent() {
        Long coffeeId = catalog.createCoffee("Espresso", 10);

        assertThat(coffeeRepository.findById(coffeeId)).isPresent();
        assertThat(stockRepository.findAll())
                .anyMatch(stock -> stock.getCoffeeId().equals(coffeeId) && stock.getQuantity() == 0);
    }
}
