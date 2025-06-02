package com.gildedrose.impl;

import com.gildedrose.model.ItemWrapper;
import com.gildedrose.service.ItemUpdater;

/**
 * Updates "Sulfuras, Hand of Ragnaros" items.
 *
 * Rules:
 * - Legendary item: quality and sell-in do not change.
 * - Quality is always 80.
 * - If quality is not 80, throws an IllegalStateException.
 */
public class SulfurasUpdater implements ItemUpdater {
    @Override
    public void update(ItemWrapper item) {
        // Legendary item does not change sellIn or quality
        if (item.getQuality() != 80) {
            throw new IllegalStateException("Sulfuras quality must always be 80.");
        }
    }
}
