package com.gildedrose;

import com.gildedrose.model.Item;
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
}
