package com.gildedrose;

import java.util.Arrays;

class GildedRose {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured";
    public static final int MAX_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).filter(item -> !checkName(SULFURAS, item)).forEach(
            this::updateQualityForItem);
    }

    private void updateQualityForItem(Item item) {
        decrementSellIn(item);
        if (checkName(AGED_BRIE, item)) {
            incrementQuality(item);
        } else if (checkName(BACKSTAGE_PASSES, item)) {
            incrementQuality(item);
            updateQualtiyForBackstage(item);
        } else if (checkName(CONJURED, item)) {
            decrementQuality(item);
            decrementQuality(item);
        } else {
            decrementQuality(item);
        }
        checkExpired(item);
    }

    private void checkExpired(Item item) {
        if (item.sellIn < 0) {
            updateExpiryBasedOnName(item);
        }
    }

    private void updateExpiryBasedOnName(Item item) {
        if (checkName(AGED_BRIE, item)) {
            incrementQuality(item);
        } else if (checkName(BACKSTAGE_PASSES, item)) {
            item.quality = 0;
        } else {
            decrementQuality(item);
        }
    }

    private void updateQualtiyForBackstage(Item item) {
        if (item.sellIn < 11) {
            incrementQuality(item);
        }
        if (item.sellIn < 6) {
            incrementQuality(item);
        }
    }

    private void incrementQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    private void decrementQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }

    private void decrementSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private boolean checkName(String name, Item item) {
        return item.name.equals(name);
    }
}
