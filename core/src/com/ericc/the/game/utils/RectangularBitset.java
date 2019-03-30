package com.ericc.the.game.utils;

import com.ericc.the.game.utils.GridPoint;
import com.badlogic.gdx.utils.Bits;

public class RectangularBitset {

    protected final int width;
    protected final int height;
    protected final Bits backingBitset;

    public RectangularBitset(int width, int height) {
        this.width = width;
        this.height = height;
        this.backingBitset = new Bits(width * height);
    }

    private void checkAssertionsForPoint(int x, int y) {
        assert (x >= 0 && x < width);
        assert (y >= 0 && y < height);
    }

    private void checkAssertionsForSize(RectangularBitset other) {
        assert (other.getWidth() == this.getWidth());
        assert (other.getHeight() == this.getHeight());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean get(int x, int y) {
        checkAssertionsForPoint(x, y);
        return backingBitset.get(x * width + y);
    }

    public boolean get(GridPoint point) {
        return get(point.x, point.y);
    }

    public void set(int x, int y) {
        checkAssertionsForPoint(x, y);
        backingBitset.set(x * width + y);
    }

    public void set(GridPoint point) {
        set(point.x, point.y);
    }

    public void assign(int x, int y, boolean value) {
        checkAssertionsForPoint(x, y);
        if (value) {
            backingBitset.set(x * width + y);
        } else {
            backingBitset.clear(x * width + y);
        }
    }

    public void flip(GridPoint point) {
        flip(point.x, point.y);
    }

    public void flip(int x, int y) {
        checkAssertionsForPoint(x, y);
        if (get(x, y)) {
            backingBitset.set(x * width + y);
        } else {
            backingBitset.clear(x * width + y);
        }
    }

    public void assign(GridPoint point, boolean value) {
        assign(point.x, point.y, value);
    }

    public void clear(int x, int y) {
        checkAssertionsForPoint(x, y);
        backingBitset.clear(x * width + y);
    }

    public void clear(GridPoint point) {
        clear(point.x, point.y);
    }

    public void or(RectangularBitset other) {
        checkAssertionsForSize(other);
        this.backingBitset.or(other.backingBitset);
    }

    public void and(RectangularBitset other) {
        checkAssertionsForSize(other);
        this.backingBitset.and(other.backingBitset);
    }

    public void xor(RectangularBitset other) {
        checkAssertionsForSize(other);
        this.backingBitset.xor(backingBitset);
    }

    public void setAll() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                set(i, j);
            }
        }
    }
}
