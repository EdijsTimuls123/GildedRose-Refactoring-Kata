package com.gildedrose.impl;

import com.gildedrose.model.ItemWrapper;
import com.gildedrose.service.ItemUpdater;

/**
 * Updates "Backstage passes" items.
 *
 * Rules:
 * - Quality increases as the sell-in date approaches.
 * - Quality increases by 1 when sell-in is greater than 10 days.
 * - Quality increases by 2 when sell-in is 6 to 10 days inclusive.
 * - Quality increases by 3 when sell-in is 1 to 5 days inclusive.
 * - Quality drops to 0 after the concert (sell-in < 0).
 * - Quality is capped at 50 before the concert.
 * - SellIn decreases by 1 each day.
 */
public class BackstagePassUpdater implements ItemUpdater {

    private static final int QUALITY_DROP_AFTER_CONCERT = 0;
    private static final int HIGH_QUALITY_INCREMENT = 3;
    private static final int MEDIUM_QUALITY_INCREMENT = 2;
    private static final int LOW_QUALITY_INCREMENT = 1;

    private static final int SELLIN_THRESHOLD_HIGH = 5;  // <= 5 days left
    private static final int SELLIN_THRESHOLD_MEDIUM = 10; // <= 10 days left
    @Override
    public void update(ItemWrapper item) {
        item.decreaseSellIn(1);

        if (item.getSellIn() < 0) {
            item.setQuality(QUALITY_DROP_AFTER_CONCERT);
            return;
        }

        int increment = calculateQualityIncrement(item.getSellIn());
        item.increaseQuality(increment);
    }

    private int calculateQualityIncrement(int sellIn) {
        if (sellIn < SELLIN_THRESHOLD_HIGH) {
            return HIGH_QUALITY_INCREMENT;
        }
        if (sellIn < SELLIN_THRESHOLD_MEDIUM) {
            return MEDIUM_QUALITY_INCREMENT;
        }
        return LOW_QUALITY_INCREMENT;
    }
}
