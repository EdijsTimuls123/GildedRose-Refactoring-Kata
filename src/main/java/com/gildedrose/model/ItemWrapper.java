package com.gildedrose.model;
public class ItemWrapper {
    private final Item item;

    public ItemWrapper(Item item) {
        this.item = item;
    }

    public String getName() {
        return item.name;
    }

    public int getSellIn() {
        return item.sellIn;
    }

    public void setSellIn(int sellIn) {
        item.sellIn = sellIn;
    }

    public void decreaseSellIn(int amount) {
        item.sellIn -= amount;
    }

    public int getQuality() {
        return item.quality;
    }

    public void setQuality(int quality) {
        item.quality = Math.max(0, Math.min(50, quality));
    }

    public void increaseQuality(int amount) {
        setQuality(getQuality() + amount);
    }

    public void decreaseQuality(int amount) {
        setQuality(getQuality() - amount);
    }

    public Item getItem() {
        return item;
    }
}
