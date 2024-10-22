package io.github.eng1_group2.registry;

import com.badlogic.gdx.graphics.Color;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.BuildingType;

public class Registries {
    private final Registry<BuildingType> buildingTypes;

    public Registries() {
        this.buildingTypes = new Registry<>("building_type");
    }

    public void loadAll() {
        // TODO: implement proper asset loading
        this.buildingTypes.register(new BuildingType("house", "House", Color.RED, new Vec2(2, 2)));
        this.buildingTypes.register(new BuildingType("lecture_theatre", "Lecture Theatre", Color.BLUE, new Vec2(1, 1)));
        this.buildingTypes.register(new BuildingType("cafe", "Cafe", Color.GREEN, new Vec2(3, 1)));
    }

    public void freezeAll() {
        this.buildingTypes.freeze();
    }

    public Registry<BuildingType> getBuildingTypes() {
        return buildingTypes;
    }
}
