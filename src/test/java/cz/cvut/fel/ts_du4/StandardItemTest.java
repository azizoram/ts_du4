package cz.cvut.fel.ts_du4;

import cz.cvut.fel.ts_du4.shop.StandardItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class StandardItemTest{




    @Test
    public void constructor(){

        StandardItem konstfirst = new StandardItem(1, "Name", 1111, "category", 1000);

        int id = 1;
        String name = "Name";
        float price = 1111;
        String category = "category";
        int loyaltyPoints = 1000;
        StandardItem konst = new StandardItem(id, name, price, category, loyaltyPoints);

        assertEquals(konst.toString(), konstfirst.toString());
    }

    // another way to test
    @Test
    public void constructor2(){

        // Hodnoty
        int id = 1;
        String name = "Name";
        float price = 1111;
        String category = "category";
        int loyaltyPoints = 1000;
        StandardItem standardItem = new StandardItem(id, name, price, category, loyaltyPoints);

        // Asserts
        assertEquals(id, standardItem.getID());
        assertEquals(name, standardItem.getName());
        assertEquals(price, standardItem.getPrice());
        assertEquals(category, standardItem.getCategory());
        assertEquals(loyaltyPoints, standardItem.getLoyaltyPoints());

    }

    @Test
    public void copyTest(){
        StandardItem standardItem = new StandardItem(1, "Name", 1111, "category", 1000);
        StandardItem copyItem = standardItem.copy();
        assertEquals(standardItem.toString(), copyItem.toString());
    }


    // another way to test
    @Test
    public void copyTest2(){
        int id = 1;
        String name = "Name";
        float price = 1111;
        String category = "category";
        int loyaltyPoints = 1000;
        StandardItem standardItem = new StandardItem(id, name, price, category, loyaltyPoints);
        StandardItem copyItem = standardItem.copy();

        assertEquals(copyItem.getID(), standardItem.getID());
        assertEquals(copyItem.getName(), standardItem.getName());
        assertEquals(copyItem.getPrice(), standardItem.getPrice());
        assertEquals(copyItem.getCategory(), standardItem.getCategory());
        assertEquals(copyItem.getLoyaltyPoints(), standardItem.getLoyaltyPoints());

    }

    @ParameterizedTest(name = "{0} is  id, {1} is Name, {2} is price, {3} is category, {4} are LoyaltyPoints, " +
            "{5} is id, {6} is Name, " + "{7} is price, {8} is category, " +
            "{9} are LoyaltyPoints, {10} Expected ")
    @CsvSource(value = {
            "1, Ivan, 1111, category1, 1000, 1, Ivan, 1111, category1, 1000, true",
            "1, Name, 1111, category1, 1000, 2, Name2, 2222, category2, 2000, false",
            "3, Name3, 1111, category1, 1000, 2, Name2, 2222, category2, 2000, false",
            "4, Name4, 4444, category4, 4000, 4, Name4, 4444, category4, 4000, true",
    }, ignoreLeadingAndTrailingWhitespace = true)
    public void equalsTest(int id1, String name1, float price1, String category1,
                           int loyaltyPoints1, int id2, String name2, float price2, String category2,
                           int loyaltyPoints2, boolean expected) {
        StandardItem firstItem = new StandardItem(id1, name1, price1, category1, loyaltyPoints1);
        StandardItem secondItem = new StandardItem(id2, name2, price2, category2, loyaltyPoints2);
        boolean neco = firstItem.equals(secondItem);
        assertEquals(expected, neco);

        // alternative method

//        Assertions.assertTrue(firstItem.equals(secondItem));
    }
}
