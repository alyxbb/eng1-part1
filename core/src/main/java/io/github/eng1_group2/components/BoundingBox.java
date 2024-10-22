package io.github.eng1_group2.components;

import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.Building;

public class BoundingBox {

    //always bottom left
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

    public Vec2 getFarCorner() {
        return origin.add(size);
    }

    public boolean intersects(BoundingBox other) {
        if (this.origin.x() < other.getFarCorner().x() &&
            other.origin.x() < this.getFarCorner().x() &&
            this.origin.y() < other.getFarCorner().y() &&
            other.origin.y() < this.getFarCorner().y()) {
            return true;
        }
        return false;
    }
}
