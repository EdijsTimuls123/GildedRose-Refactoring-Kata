package com.gildedrose.impl;

import com.gildedrose.model.ItemWrapper;
import com.gildedrose.service.ItemUpdater;

/**
 * Updates "Backstage passes to a TAFKAL80ETC concert" items.
 *
 * Rules:
 * - Quality increases by 1 when sell-in is more than 10 days.
 * - Quality increases by 2 when sell-in is between 6 and 10 days (inclusive lower bound).
 * - Quality increases by 3 when sell-in is between 1 and 5 days (inclusive lower bound).
 * - Quality drops to 0 after the concert (sell-in < 0).
 * - Quality is capped at 50 (enforced in ItemWrapper).
 * - SellIn decreases by 1 each day.
 */
public class BackstagePassUpdater implements ItemUpdater {

    private static final int QUALITY_DROP_AFTER_CONCERT = 0;

    // Thresholds are exclusive upper bounds for increments calculation
    private static final int SELLIN_THRESHOLD_HIGH = 6;    // <6 days left → high increment
    private static final int SELLIN_THRESHOLD_MEDIUM = 11; // <11 days left → medium increment

    @Override
    public void update(ItemWrapper item) {
        int sellIn = item.getSellIn();

        // Base quality increment
        int increment = 1;

        if (sellIn < SELLIN_THRESHOLD_HIGH) {
            // 1 to 5 days left → increment by 3 total (1 base + 2 extra)
            increment += 2;
        } else if (sellIn < SELLIN_THRESHOLD_MEDIUM) {
            // 6 to 10 days left → increment by 2 total (1 base + 1 extra)
            increment += 1;
        }

        // Increase quality by calculated increment, capped internally at 50 by ItemWrapper
        item.increaseQuality(increment);

        // Decrease sellIn by 1 day
        item.decreaseSellIn(1);

        // After concert, quality drops to 0
        if (item.getSellIn() < 0) {
            item.setQuality(QUALITY_DROP_AFTER_CONCERT);
        }
    }
}
