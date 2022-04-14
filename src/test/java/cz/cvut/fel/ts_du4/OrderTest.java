package cz.cvut.fel.ts_du4;


import cz.cvut.fel.ts_du4.shop.Order;
import cz.cvut.fel.ts_du4.shop.ShoppingCart;
import cz.cvut.fel.ts_du4.shop.StandardItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderTest {

    @Test
    @org.junit.jupiter.api.Order(1)
    public void ConstrictorTest(){

        ShoppingCart cart = new ShoppingCart();
        // Customer
        int id = 1;
        String Name = "Ramir";
        String Address = "Thákurova 550";

        // Item
        String ItemName = "Something";
        float price = 1111;
        String category = "category";
        int LoyaltyPoints = 1000;

        StandardItem standardItem = new StandardItem(id, ItemName, price, category, LoyaltyPoints);
        StandardItem standardItem2 = new StandardItem(id, ItemName, price, category, LoyaltyPoints);
        cart.addItem(standardItem);
        cart.addItem(standardItem);

        int CountOfItems = 2;
        int state = 0;
        int index = 0;
        Order order = new Order(cart, Name, Address, state);

        // Asserts
        assertEquals(order.getCustomerName(), Name);
        assertEquals(order.getCustomerAddress(), Address);
        assertEquals(order.getState(), state);
        assertEquals(order.getItems().get(index), cart.getCartItems().get(index));
        assertEquals(order.getItems().size(), CountOfItems);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void ConstrictorTestWithoutState(){

        ShoppingCart cart = new ShoppingCart();
        // Customer
        int id = 1;
        String Name = "Ramir";
        String Address = "Thákurova 550";

        // Item
        String ItemName = "Something";
        float price = 1111;
        String category = "category";
        int LoyaltyPoints = 1000;

        StandardItem standardItem = new StandardItem(id, ItemName, price, category, LoyaltyPoints);
        StandardItem standardItem2 = new StandardItem(id, ItemName, price, category, LoyaltyPoints);
        cart.addItem(standardItem);
        cart.addItem(standardItem);

        int CountOfItems = 2;
        int state = 0;
        int index = 0;
        Order order = new Order(cart, Name, Address, state);

        // Asserts
        assertEquals(order.getCustomerName(), Name);
        assertEquals(order.getCustomerAddress(), Address);
        assertEquals(order.getItems().get(index), cart.getCartItems().get(index));
        assertEquals(order.getItems().size(), CountOfItems);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void NullTest(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Order(null, null, null, 1);
        });
    }

}
