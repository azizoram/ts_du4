package cz.cvut.fel.ts_du4.shop;

import cz.cvut.fel.ts_du4.storage.NoItemInStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EShopControllerTest {
    EShopController eshop;
    Item[] storageItems = {
            new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
            new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
            new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
            new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
            new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
            new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
    };


    @BeforeEach
    public void setup() {
        eshop = new EShopController();
        int[] itemCount = {10,10,4,5,10,2};

        for (int i = 0; i < storageItems.length; i++) {
            eshop.getStorage().insertItems(storageItems[i], itemCount[i]);
        }
    }


    @Test
    public void EshopControllerGoodTest() throws Exception {
        assertEquals(10, eshop.getStorage().getItemCount(1));
        ShoppingCart TheCart = new ShoppingCart();

        for (Item storageItem : storageItems) {
            TheCart.addItem(storageItem);
        }

        //Check Items in Cart
        assertEquals(6, TheCart.getItemsCount());
//        for (int i=0; i < newCart.getItemsCount(); i ++){
//            System.out.println(newCart.getCartItems().get(i).getPrice());
//            System.out.println(newCart.getCartItems().get(i).getClass().getSimpleName());
//            System.out.println(newCart.getCartItems().get(i).toString());
//        }
        assertEquals(12510, TheCart.getTotalPrice());

        //check remove Item
        TheCart.removeItem(1);
        assertEquals(5, TheCart.getItemsCount());
        assertEquals(7510, TheCart.getTotalPrice());

        eshop.purchaseShoppingCart(TheCart, "Ramir Azziov", "Thákurova 550");
        assertEquals(10, eshop.getStorage().getItemCount(1));
        assertEquals(9, eshop.getStorage().getItemCount(2));
        assertEquals(3, eshop.getStorage().getItemCount(3));
        assertEquals(4, eshop.getStorage().getItemCount(4));
        assertEquals(9, eshop.getStorage().getItemCount(5));
        assertEquals(1, eshop.getStorage().getItemCount(6));

//        проверка
//        System.out.println(ec.getArchive().getItemPurchaseArchive().toString());
//        for (int i = 1; i < newCart.getCartItems().size(); i++){
//            System.out.println(newCart.getCartItems().get(i).toString());
//        }

        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(2).getCountHowManyTimesHasBeenSold());
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(3).getCountHowManyTimesHasBeenSold());
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(4).getCountHowManyTimesHasBeenSold());
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(5).getCountHowManyTimesHasBeenSold());
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(6).getCountHowManyTimesHasBeenSold());
    }

    @Test
    public void ItemIsNotInStorageCheck() throws NoItemInStorage {
        ShoppingCart myCart = new ShoppingCart();
        myCart.addItem(storageItems[5]);
        eshop.purchaseShoppingCart(myCart, "Azizov Ramir", "Thákurova 550");
        assertEquals(1, eshop.getStorage().getItemCount(6));
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(6).getCountHowManyTimesHasBeenSold());
        eshop.purchaseShoppingCart(myCart, "Azizov Ramir", "Thákurova 550");

        //проверка Итема нет на складе...
        assertThrows(NoItemInStorage.class, () -> {eshop.purchaseShoppingCart(myCart, "Azizov Ramir",
                                                                                        "Thákurova 550");
        });
    }


    @Test
    public void EshopControllerProcessTest() throws Exception {
        ShoppingCart myCart = new ShoppingCart();
        myCart.addItem(storageItems[2]);
        myCart.addItem(storageItems[3]);
        myCart.addItem(storageItems[4]);
        myCart.addItem(storageItems[5]);
        myCart.addItem(storageItems[5]);

        // check Items in MyCart
        assertEquals(5, myCart.getItemsCount());
        // 200 + 350 + 240 + 720 + 720 = 2230;
        assertEquals(2230, myCart.getTotalPrice());

        //item well removed from cart
        myCart.removeItem(3);
        assertEquals(4, myCart.getItemsCount());

        //can't remove item, that's not in the cart
        assertThrows(Exception.class, () -> {myCart.removeItem(100);});

        // 350 + 240 + 720 + 720 = 2030
        assertEquals(2030, myCart.getTotalPrice());

        //purchasing items from cart -> few items in storage
        eshop.purchaseShoppingCart(myCart, "Azizov Ramir", "Thákurova 550");
        assertEquals(10, eshop.getStorage().getItemCount(1));
        assertEquals(10, eshop.getStorage().getItemCount(2));
        assertEquals(4, eshop.getStorage().getItemCount(3));
        assertEquals(9, eshop.getStorage().getItemCount(5));
        assertEquals(0, eshop.getStorage().getItemCount(6));
        //System.out.println(eshop.getArchive().getItemPurchaseArchive().toString());
        assertEquals(3, eshop.getArchive().getItemPurchaseArchive().size());
        assertEquals(1, eshop.getArchive().getItemPurchaseArchive().get(4).getCountHowManyTimesHasBeenSold());

    }
}
