package io.github.eng1_group2.registry;

import io.github.eng1_group2.utils.CodecAssetLoader;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.world.feature.FeatureType;

public class Registries {
    private final Registry<BuildingType> buildingTypes;
    private final Registry<FeatureType> featureTypes;

    public Registries() {
        this.buildingTypes = new Registry<>("building_type", BuildingType.CODEC);
        this.featureTypes = new Registry<>("feature_type", FeatureType.CODEC);
    }

    public void loadAllFrom(CodecAssetLoader loader) {
        this.buildingTypes.loadFrom(loader);
        this.featureTypes.loadFrom(loader);
    }

    public void freezeAll() {
        this.buildingTypes.freeze();
        this.featureTypes.freeze();
    }

    public Registry<BuildingType> getBuildingTypes() {
        return buildingTypes;
    }

    public Registry<FeatureType> getFeatureTypes() {
        return featureTypes;
    }
}
