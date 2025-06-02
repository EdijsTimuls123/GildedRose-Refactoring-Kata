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

    private static final int NORMAL_QUALITY_INCREASE = 1;
    private static final int EXPIRED_QUALITY_INCREASE = 2;

    @Override
    public void update(ItemWrapper item) {
        item.decreaseSellIn(1);

        int increase = item.getSellIn() < 0 ? EXPIRED_QUALITY_INCREASE : NORMAL_QUALITY_INCREASE;
        item.increaseQuality(increase);
    }
}
