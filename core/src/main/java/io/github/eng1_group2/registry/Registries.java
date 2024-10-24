package io.github.eng1_group2.registry;

import io.github.eng1_group2.utils.CodecAssetLoader;
import io.github.eng1_group2.world.building.BuildingType;

public class Registries {
    private final Registry<BuildingType> buildingTypes;

    public Registries() {
        this.buildingTypes = new Registry<>("building_type", BuildingType.CODEC);
    }

    public void loadAllFrom(CodecAssetLoader loader) {
        this.buildingTypes.loadFrom(loader);
    }

    public void freezeAll() {
        this.buildingTypes.freeze();
    }

    public Registry<BuildingType> getBuildingTypes() {
        return buildingTypes;
    }
}
