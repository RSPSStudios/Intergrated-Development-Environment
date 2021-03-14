package com.javatar.model;

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

public class ModelPatch {

    private final int index;
    private final int offset;

    public ModelPatch(int index, int offset) {
        this.index = index;
        this.offset = offset;
    }

    public int getIndex() {
        return index;
    }

    public int getOffset() {
        return offset;
    }
}
