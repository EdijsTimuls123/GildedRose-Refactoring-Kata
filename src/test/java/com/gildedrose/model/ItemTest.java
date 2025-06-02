package com.gildedrose.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void constructor_initializesFieldsCorrectly() {
        Item item = new Item("Aged Brie", 5, 10);
        assertEquals("Aged Brie", item.name);
        assertEquals(5, item.sellIn);
        assertEquals(10, item.quality);
    }

    @Test
    void toString_returnsCorrectFormat() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        assertEquals("Sulfuras, Hand of Ragnaros, 0, 80", item.toString());
    }
}
