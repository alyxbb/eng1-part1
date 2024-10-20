package io.github.eng1_group2.world.building;

public class Building {
    private final BuildingType type;

    public Building(BuildingType type) {
        this.type = type;
    }

    public BuildingType getType() {
        return type;
    }
}
