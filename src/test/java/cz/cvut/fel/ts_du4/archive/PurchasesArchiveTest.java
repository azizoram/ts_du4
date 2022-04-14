package cz.cvut.fel.ts_du4.archive;

import cz.cvut.fel.ts_du4.shop.Order;
import cz.cvut.fel.ts_du4.shop.ShoppingCart;
import cz.cvut.fel.ts_du4.shop.StandardItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PurchasesArchiveTest {


    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStream =new ByteArrayOutputStream();


    @BeforeEach
    public void setUpForTests() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(printStream);
    }



    @Test
    void printItemPurchaseStatistics() {
        HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchiveEntryHashMap = new HashMap<>();
        ArrayList<Order> ArchiveOfOrders = new ArrayList<>();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new StandardItem(1, "Neco1", 1111, "category1", 1000));
        shoppingCart.addItem(new StandardItem(2, "Neco2", 2222, "category2", 2000));
        shoppingCart.addItem(new StandardItem(3, "Neco3", 3333, "category3", 3000));

        //делаю заказ
        Order order = new Order(shoppingCart, "Azizov Ramir", "Thákurova 550");
        ArchiveOfOrders.add(order);

        // добавляю покупки
        itemPurchaseArchiveEntryHashMap.put(1, new ItemPurchaseArchiveEntry(new
                StandardItem(1, "Neco1", 1111, "category1", 1000)));
        itemPurchaseArchiveEntryHashMap.put(2, new ItemPurchaseArchiveEntry(new
                StandardItem(2, "Neco2", 2222, "category2", 2000)));
        itemPurchaseArchiveEntryHashMap.put(3, new ItemPurchaseArchiveEntry(new
                StandardItem(3, "Neco3", 3333, "category3", 3000)));

        // инициализирую Архим Покупки
        PurchasesArchive purchasesArchive = new PurchasesArchive(itemPurchaseArchiveEntryHashMap, ArchiveOfOrders);

        purchasesArchive.printItemPurchaseStatistics();

        // для ассертов Item with ID 1 added to the shopping cart
        // ITEM PURCHASE STATISTICS:
        // ITEM  Item   ID 1   NAME Name   CATEGORY category   PRICE 1111   LOYALTY POINTS 1000  HAS BEEN SOLD 1 TIMES

        assertEquals("Item with ID 1 added to the shopping cart."+ System.lineSeparator() +
                "Item with ID 2 added to the shopping cart."+ System.lineSeparator() +
                "Item with ID 3 added to the shopping cart."+ System.lineSeparator() +
                "ITEM PURCHASE STATISTICS:"+ System.lineSeparator() +
                "ITEM  Item   ID 1   NAME Neco1   CATEGORY category1   PRICE 1111.0   " +
                        "LOYALTY POINTS 1000   HAS BEEN SOLD 1 TIMES"+ System.lineSeparator() +
                "ITEM  Item   ID 2   NAME Neco2   CATEGORY category2   PRICE 2222.0   " +
                        "LOYALTY POINTS 2000   HAS BEEN SOLD 1 TIMES"+ System.lineSeparator() +
                "ITEM  Item   ID 3   NAME Neco3   CATEGORY category3   PRICE 3333.0   " +
                        "LOYALTY POINTS 3000   HAS BEEN SOLD 1 TIMES" + System.lineSeparator()
                , outputStream.toString());

    }

    @Test
    void getHowManyTimesHasBeenItemSold() {
        ShoppingCart shoppingCart = new ShoppingCart();
        StandardItem standardItem = new StandardItem(1, "Neco1", 1111,
                "category1", 1000);


        // добавим 5 раз в корзину по приколу
        shoppingCart.addItem(standardItem);
        shoppingCart.addItem(standardItem.copy());
        shoppingCart.addItem(standardItem.copy());
        shoppingCart.addItem(standardItem.copy());
        shoppingCart.addItem(standardItem.copy());

        Order order = new Order(shoppingCart, "Azizov Ramir", "Thákurova 550");

        PurchasesArchive purchasesArchive = new PurchasesArchive();
        purchasesArchive.putOrderToPurchasesArchive(order);

        assertEquals(5, purchasesArchive.getHowManyTimesHasBeenItemSold(standardItem));

        // nula krat prodame

        PurchasesArchive purchasesArchive2 = new PurchasesArchive();
        assertEquals(0, purchasesArchive2.getHowManyTimesHasBeenItemSold(standardItem));
    }

    @Test
    void putOrderToPurchasesArchive() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new StandardItem(1, "Neco1", 1111, "category1", 1000));
        shoppingCart.addItem(new StandardItem(2, "Neco2", 2222, "category2", 2000));
        shoppingCart.addItem(new StandardItem(2, "Neco2", 2222, "category2", 2000));
        shoppingCart.addItem(new StandardItem(3, "Neco3", 3333, "category3", 3000));
        shoppingCart.addItem(new StandardItem(3, "Neco3", 3333, "category3", 3000));
        shoppingCart.addItem(new StandardItem(3, "Neco3", 3333, "category3", 3000));

        Order order = new Order(shoppingCart, "Azizov Ramir", "Thákurova 550");

        PurchasesArchive purchasesArchive = new PurchasesArchive();
        purchasesArchive.putOrderToPurchasesArchive(order);

        purchasesArchive.printItemPurchaseStatistics();

        // я устал это писать((((
        assertEquals("Item with ID 1 added to the shopping cart."+ System.lineSeparator() +
                        "Item with ID 2 added to the shopping cart."+ System.lineSeparator() +
                        "Item with ID 2 added to the shopping cart."+ System.lineSeparator() +
                        "Item with ID 3 added to the shopping cart."+ System.lineSeparator() +
                        "Item with ID 3 added to the shopping cart."+ System.lineSeparator() +
                        "Item with ID 3 added to the shopping cart."+ System.lineSeparator() +
                        "ITEM PURCHASE STATISTICS:"+ System.lineSeparator() +
                        "ITEM  Item   ID 1   NAME Neco1   CATEGORY category1   PRICE 1111.0   " +
                        "LOYALTY POINTS 1000   HAS BEEN SOLD 1 TIMES"+ System.lineSeparator() +
                        "ITEM  Item   ID 2   NAME Neco2   CATEGORY category2   PRICE 2222.0   " +
                        "LOYALTY POINTS 2000   HAS BEEN SOLD 2 TIMES"+ System.lineSeparator() +
                        "ITEM  Item   ID 3   NAME Neco3   CATEGORY category3   PRICE 3333.0   " +
                        "LOYALTY POINTS 3000   HAS BEEN SOLD 3 TIMES" + System.lineSeparator()
                , outputStream.toString());
    }
}