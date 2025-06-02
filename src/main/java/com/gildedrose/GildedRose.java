package com.gildedrose;

import com.gildedrose.impl.*;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemWrapper;
import com.gildedrose.model.ItemType;
import com.gildedrose.service.ItemUpdater;

import java.util.HashMap;
import java.util.Map;

public class GildedRose {
    private final Item[] items;
    private final Map<ItemType, ItemUpdater> updaterMap = new HashMap<>();

    public GildedRose(Item[] items) {
        this.items = items;
        updaterMap.put(ItemType.AGED_BRIE, new AgedBrieUpdater());
        updaterMap.put(ItemType.SULFURAS, new SulfurasUpdater());
        updaterMap.put(ItemType.BACKSTAGE_PASS, new BackstagePassUpdater());
        updaterMap.put(ItemType.CONJURED, new ConjuredItemUpdater());
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemType type = ItemType.fromName(item.name);
            updaterMap.get(type).update(new ItemWrapper(item));
        }
    }
}
