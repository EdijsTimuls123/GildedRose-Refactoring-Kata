package com.gildedrose;

import com.gildedrose.model.Item;
import com.gildedrose.model.ItemWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    @Test
    void agedBrieIncreasesInQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, items[0].quality);
        assertEquals(1, items[0].sellIn);
    }

    @Test
    void backstagePassesIncreaseInQuality() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11, items[0].quality);
    }

    @Test
    void conjuredDegradesTwiceAsFast() {
        Item[] items = new Item[] {
            new Item("Conjured", 5, 10)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8, items[0].quality);
    }

    @Test
    void sulfurasDoesNotChange() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 0, 80)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(0, items[0].sellIn);
    }

    @Test
    void sulfurasQualityMustAlwaysBe80Throws() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 10, 79)
        };
        GildedRose app = new GildedRose(items);
        assertThrows(IllegalStateException.class, app::updateQuality);
    }

    @Test
    void unknownItemThrows() {
        Item[] items = new Item[] {
            new Item("Random Item", 5, 5)
        };
        GildedRose app = new GildedRose(items);
        assertThrows(IllegalArgumentException.class, app::updateQuality);
    }

    @Test
    void updateQuality_withMixedItems_updatesAllCorrectly() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 0), // should increase in quality
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10), // should increase by 1
            new Item("Conjured", 5, 10), // should decrease by 2
            new Item("Sulfuras, Hand of Ragnaros", 0, 80) // should remain unchanged
        };

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertAll(
            () -> assertEquals(1, items[0].quality, "Aged Brie quality should increase by 1"),
            () -> assertEquals(11, items[1].quality, "Backstage pass quality should increase by 1"),
            () -> assertEquals(8, items[2].quality, "Conjured item quality should decrease by 2"),
            () -> assertEquals(80, items[3].quality, "Sulfuras quality should remain at 80"),
            () -> assertEquals(1, items[0].sellIn, "Aged Brie sellIn should decrease by 1"),
            () -> assertEquals(10, items[1].sellIn, "Backstage pass sellIn should decrease by 1"),
            () -> assertEquals(4, items[2].sellIn, "Conjured sellIn should decrease by 1"),
            () -> assertEquals(0, items[3].sellIn, "Sulfuras sellIn should remain unchanged")
        );
    }

    @Test
    void itemWrapperProperlyWrapsAndMutatesItem() {
        Item item = new Item("Test Item", 10, 20);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.decreaseSellIn(1);
        wrapper.increaseQuality(5);
        wrapper.decreaseQuality(3);

        assertAll(
            () -> assertEquals("Test Item", wrapper.getName()),
            () -> assertEquals(9, wrapper.getSellIn()),
            () -> assertEquals(22, wrapper.getQuality()),
            () -> assertEquals("Test Item", item.name),
            () -> assertEquals(9, item.sellIn),
            () -> assertEquals(22, item.quality)
        );
    }

    @Test
    void itemWrapperEnforcesQualityBounds() {
        Item item = new Item("Quality Bounds", 5, 49);
        ItemWrapper wrapper = new ItemWrapper(item);

        wrapper.increaseQuality(5); // should cap at 50
        assertEquals(50, wrapper.getQuality());

        wrapper.decreaseQuality(55); // should not go below 0
        assertEquals(0, wrapper.getQuality());
    }
}
