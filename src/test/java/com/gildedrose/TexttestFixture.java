package com.gildedrose;

import com.gildedrose.model.Item;

import java.util.List;

public class TexttestFixture {
    public static void main(String[] args) {
        int days = 10;  // default number of days
        if (args.length > 0) {
            try {
                days = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number of days argument, using default 10.");
            }
        }

        List<Item> items = List.of(
            new Item("Aged Brie", 2, 0),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Conjured Mana Cake", 3, 6)
        );

        GildedRose app = new GildedRose(items.toArray(new Item[0]));

        for (int day = 1; day <= days; day++) {
            System.out.println("-------- day " + day + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();

            app.updateQuality();
        }
    }
}
