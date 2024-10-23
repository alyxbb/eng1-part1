package io.github.eng1_group2.components;

import io.github.eng1_group2.utils.Vec2;

public class BoundingBox {

    // always bottom left
    private final Vec2 origin;
    private final Vec2 size;

    public BoundingBox(Vec2 origin, Vec2 size) {
        this.origin = origin;
        this.size = size;
    }

    public Vec2 getOrigin() {
        return origin;
    }

    public Vec2 getSize() {
        return size;
    }

    public int getBottom() {
        return origin.y();
    }

    public int getLeft() {
        return origin.x();
    }

    public int getTop() {
        return origin.y() + size.y();
    }

    public int getRight() {
        return origin.x() + size.x();
    }


    public boolean intersects(BoundingBox other) {
        return this.getLeft() < other.getRight() &&
            other.getLeft() < this.getRight() &&
            this.getBottom() < other.getTop() &&
            other.getBottom() < this.getTop();
    }
}
