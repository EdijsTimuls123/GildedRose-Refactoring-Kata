package com.gildedrose.impl;

import com.gildedrose.model.ItemWrapper;
import com.gildedrose.service.ItemUpdater;

/**
 * Updates "Conjured" items.
 *
 * Rules:
 * - "Conjured" items degrade in quality twice as fast as normal items.
 * - Quality decreases by 2 each day before sell-by date.
 * - Quality decreases by 4 each day after sell-by date.
 * - Quality cannot go below 0.
 * - SellIn decreases by 1 each day.
 */
public class ConjuredItemUpdater implements ItemUpdater {

    private static final int NORMAL_DEGRADATION = 2;
    private static final int EXPIRED_DEGRADATION = 4;

    @Override
    public void update(ItemWrapper item) {
        item.decreaseSellIn(1);

        int degradation = item.getSellIn() < 0 ? EXPIRED_DEGRADATION : NORMAL_DEGRADATION;
        item.decreaseQuality(degradation);
    }
}
