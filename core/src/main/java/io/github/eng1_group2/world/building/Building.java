package io.github.eng1_group2.world.building;

import io.github.eng1_group2.utils.Vec2;

public class Building {
    private final BuildingType type;

    public Vec2 getOrigin() {
        return origin;
    }

    private Vec2 origin;

    public Building(BuildingType type,Vec2 origin) {
        this.type = type;
        this.origin = origin;
    }

    public BuildingType getType() {
        return type;
    }
}
