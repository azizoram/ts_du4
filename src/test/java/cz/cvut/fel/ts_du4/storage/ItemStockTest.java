package cz.cvut.fel.ts_du4.storage;
import cz.cvut.fel.ts_du4.shop.StandardItem;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class ItemStockTest {

    private int count = 0;


    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3"})
    void TestIncreaseItemCount(int x) {
        StandardItem item = new StandardItem(1, "neco", 1111, "category", 1111);

        ItemStock stock1 = new ItemStock(item);

        for (int i = 0; i < 5; i++){
            stock1.IncreaseItemCount(x);
        }
        assertEquals(x * 5, stock1.getCount());
        System.out.println(stock1.getCount());
    }

    @ParameterizedTest
    @CsvSource(value = {"10", "20", "30"})
    void TestDecreaseItemCount(int x) {

        StandardItem item = new StandardItem(1, "neco", 1111, "category", 1111);
        ItemStock stock1 = new ItemStock(item);
        ItemStock stock2 = new ItemStock(item);

        for (int i = 0; i < 5; i++){
            stock1.decreaseItemCount(x);
            System.out.println(stock1.getCount());
        }
        assertEquals(-(x * 5), stock1.getCount());
        System.out.println(stock1.getCount());
    }

    @Test
    @Order(1)
    void TestGetCount() {
        StandardItem item = new StandardItem(1, "neco", 1111, "category", 1111);
        ItemStock stock = new ItemStock(item);
        assertEquals(0, stock.getCount());
    }

    @Test
    @Order(2)
    void TestGetItem() {
        StandardItem item = new StandardItem(1, "neco", 1111, "category", 1111);
        ItemStock stock = new ItemStock(item);
        assertEquals(item, stock.getItem());

    }
}