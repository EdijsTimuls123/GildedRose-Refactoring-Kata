package com.gildedrose.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemWrapperTest {

    @Test
    void getSetSellIn_modifiesUnderlyingItem() {
        Item item = new Item("Test Item", 10, 20);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.setSellIn(7);
        assertEquals(7, wrapper.getSellIn());
        assertEquals(7, item.sellIn);

        wrapper.decreaseSellIn(2);
        assertEquals(5, wrapper.getSellIn());
        assertEquals(5, item.sellIn);
    }

    @Test
    void getSetQuality_withinBounds() {
        Item item = new Item("Test Item", 5, 10);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.setQuality(15);
        assertEquals(15, wrapper.getQuality());
        assertEquals(15, item.quality);
    }

    @Test
    void setQuality_enforcesUpperAndLowerBounds() {
        Item item = new Item("Test Item", 5, 10);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.setQuality(55);
        assertEquals(50, wrapper.getQuality());

        wrapper.setQuality(-10);
        assertEquals(0, wrapper.getQuality());
    }

    @Test
    void increaseQuality_doesNotExceed50() {
        Item item = new Item("Test Item", 5, 49);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.increaseQuality(5);
        assertEquals(50, wrapper.getQuality());
    }

    @Test
    void decreaseQuality_doesNotGoBelow0() {
        Item item = new Item("Test Item", 5, 1);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.decreaseQuality(3);
        assertEquals(0, wrapper.getQuality());
    }

    @Test
    void getItem_returnsOriginalItem() {
        Item item = new Item("Test Item", 5, 10);
        ItemWrapper wrapper = new ItemWrapper(item);

        assertSame(item, wrapper.getItem());
    }
}
