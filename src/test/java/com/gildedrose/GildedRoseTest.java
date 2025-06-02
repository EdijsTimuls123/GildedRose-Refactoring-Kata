package com.gildedrose;

import com.gildedrose.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    @Test
    void agedBrieIncreasesInQualityBeforeSellDate() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, items[0].quality);  // quality increases by 1
        assertEquals(1, items[0].sellIn);   // sellIn decreases by 1
    }

    @Test
    void agedBrieIncreasesTwiceAsFastAfterSellDate() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, items[0].quality);  // quality increases by 2 (expired)
        assertEquals(-1, items[0].sellIn);  // sellIn decreases by 1
    }

    @Test
    void backstagePassesIncreaseInQualityMoreCloseToSellDate() {
        // More than 10 days: +1 quality
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, items[0].quality);
        assertEquals(14, items[0].sellIn);

        // Between 6 and 10 days: +2 quality
        items[0].sellIn = 10;
        items[0].quality = 20;
        app.updateQuality();
        assertEquals(22, items[0].quality);
        assertEquals(9, items[0].sellIn);

        // Between 1 and 5 days: +3 quality
        items[0].sellIn = 5;
        items[0].quality = 20;
        app.updateQuality();
        assertEquals(23, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    @Test
    void backstagePassesDropToZeroAfterConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);  // quality drops to 0 after sellIn < 0
        assertEquals(-1, items[0].sellIn);
    }

    @Test
    void sulfurasQualityNeverChanges() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(0, items[0].sellIn);
    }

    @Test
    void sulfurasQualityMustAlwaysBe80Throws() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 79) };
        GildedRose app = new GildedRose(items);
        assertThrows(IllegalStateException.class, app::updateQuality);
    }

    @Test
    void agedBrieQualityNegativeInitial_cappedToZero() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, -10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality, "Quality should be capped at 0 if initially negative");
    }

    @Test
    void qualityNeverMoreThanFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, items[0].quality); // quality capped at 50

        items[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50);
        app = new GildedRose(new Item[] {items[0]});
        app.updateQuality();
        assertEquals(50, items[0].quality); // quality capped at 50
    }

    @Test
    void unknownItemThrows() {
        Item[] items = new Item[] { new Item("Random Item", 5, 5) };
        GildedRose app = new GildedRose(items);
        assertThrows(IllegalArgumentException.class, app::updateQuality);
    }

    @Test
    void updateQuality_withMixedItems_updatesAllCorrectly() {
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 0),
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80)
        };

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertAll(
            () -> assertEquals(1, items[0].quality, "Aged Brie quality should increase by 1"),
            () -> assertEquals(11, items[1].quality, "Backstage pass quality should increase by 1"),
            () -> assertEquals(80, items[2].quality, "Sulfuras quality should remain at 80"),
            () -> assertEquals(1, items[0].sellIn, "Aged Brie sellIn should decrease by 1"),
            () -> assertEquals(10, items[1].sellIn, "Backstage pass sellIn should decrease by 1"),
            () -> assertEquals(0, items[2].sellIn, "Sulfuras sellIn should remain unchanged")
        );
    }

    @Test
    void agedBrieMultiDayUpdate_qualityCappedAtFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);

        for (int day = 0; day < 100; day++) {
            app.updateQuality();
        }

        assertEquals(50, items[0].quality, "Aged Brie quality should not exceed 50");
        assertTrue(items[0].sellIn < 0, "SellIn should be negative after many days");
    }

    @Test
    void agedBrieNegativeStart_recoversToFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, -10) };
        GildedRose app = new GildedRose(items);

        for (int day = 0; day < 100; day++) {
            app.updateQuality();
        }

        assertEquals(50, items[0].quality, "Even starting negative, quality should be capped at 50");
        assertEquals(-95, items[0].sellIn, "SellIn should decrease by 1 each day");
    }

    @Test
    void backstagePassesMultiDayUpdate_dropsToZeroAfterConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);

        for (int i = 0; i < 20; i++) {
            app.updateQuality();
        }

        assertEquals(0, items[0].quality, "Backstage pass should drop to 0 after concert");
        assertTrue(items[0].sellIn < 0, "SellIn should be negative after concert");
    }

    @Test
    void sulfurasMultiDayUpdate_neverChanges() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        for (int i = 0; i < 50; i++) {
            app.updateQuality();
        }

        assertEquals(80, items[0].quality, "Sulfuras quality should remain 80");
        assertEquals(0, items[0].sellIn, "Sulfuras sellIn should remain unchanged");
    }
}
