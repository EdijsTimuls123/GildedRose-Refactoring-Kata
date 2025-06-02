package com.gildedrose.model;

public enum ItemType {
    AGED_BRIE("Aged Brie"),
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    BACKSTAGE_PASS("Backstage passes to a TAFKAL80ETC concert");

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public static ItemType fromName(String name) {
        for (ItemType type : values()) {
            if (name != null && name.startsWith(type.name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown item type: " + name);
    }
}
