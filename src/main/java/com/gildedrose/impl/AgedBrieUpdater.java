package com.gildedrose.impl;

import com.gildedrose.model.ItemWrapper;
import com.gildedrose.service.ItemUpdater;

/**
 * Updates "Aged Brie" items.
 *
 * Rules:
 * - "Aged Brie" increases in quality as it ages.
 * - Quality increases by 1 each day before sell-by date.
 * - After sell-by date, quality increases twice as fast (by 2).
 * - Quality is capped at 50.
 * - SellIn decreases by 1 each day.
 */
public class AgedBrieUpdater implements ItemUpdater {

    // Quality increase by 1 before sell-by date
    private static final int NORMAL_QUALITY_INCREASE = 1;
    // Quality increase by 2 after sell-by date (expired)
    private static final int EXPIRED_QUALITY_INCREASE = 2;

    @Override
    public void update(ItemWrapper item) {
        // Decrease sellIn value by 1 to represent a day passing
        item.decreaseSellIn(1);

        // Determine the quality increase amount based on sellIn value
        // If the item is expired (sellIn < 0), increase quality by 2, else by 1
        int increase = item.getSellIn() < 0 ? EXPIRED_QUALITY_INCREASE : NORMAL_QUALITY_INCREASE;

        // Increase quality, capped internally at 50 by ItemWrapper
        item.increaseQuality(increase);
    }
}
