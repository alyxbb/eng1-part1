package io.github.eng1_group2.world.building;

import io.github.eng1_group2.utils.Vec2;

public class Building {
    private final BuildingType type;
    private final Vec2 origin;

    public Building(BuildingType type, Vec2 origin) {
        this.type = type;
        this.origin = origin;
    }

    public Vec2 getOrigin() {
        return origin;
    }

    public BuildingType getType() {
        return type;
    }

    public boolean intersects(Building other) {
        if (this.origin.x() < other.origin.x() + other.type.size().x() &&
            other.origin.x() < this.origin.x() + this.type.size().x() &&
            this.origin.y() < other.origin.y() + other.type.size().y() &&
            other.origin.y() < this.origin.y() + this.type.size().y()) {
            return true;
        }
        return false;
    }
}
