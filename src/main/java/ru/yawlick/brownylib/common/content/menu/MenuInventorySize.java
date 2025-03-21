package ru.yawlick.brownylib.common.content.menu;

public enum MenuInventorySize {
    _1X9_8(9),
    _2X9_17(18),
    _3X9_26(27),
    _4X9_35(36),
    _5X9_44(45),
    _6X9_53(54);

    private int size;

    MenuInventorySize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
