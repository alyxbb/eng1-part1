package io.github.eng1_group2.world.building;

import io.github.eng1_group2.components.BoundingBox;
import io.github.eng1_group2.utils.Vec2;

public class Building {
    private final BuildingType type;
    private final BoundingBox boundingBox;

    public Building(BuildingType type, Vec2 origin) {
        this.type = type;
        this.boundingBox = new BoundingBox(origin, type.size());
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /*public BuildingType getType() {
        return type;
    }*/


}
