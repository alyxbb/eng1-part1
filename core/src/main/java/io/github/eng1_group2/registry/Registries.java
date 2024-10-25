package io.github.eng1_group2.registry;

import com.badlogic.gdx.assets.AssetManager;
import com.mojang.serialization.Codec;
import io.github.eng1_group2.utils.CodecAssetLoader;
import io.github.eng1_group2.utils.TextureLoader;
import io.github.eng1_group2.world.WorldConfig;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.world.feature.FeatureType;

import java.util.HashMap;
import java.util.Map;

public class Registries {
    private final Map<String, Registry<?>> registries;
    private final Registry<BuildingType> buildingTypes;
    private final Registry<WorldConfig> worldConfigs;
    private final Registry<FeatureType> featureTypes;

    public Registries() {
        this.registries = new HashMap<>();
        this.buildingTypes = this.createRegistry(BuildingType.REGISTRY_NAME, BuildingType.CODEC);
        this.featureTypes = this.createRegistry(FeatureType.REGISTRY_NAME, FeatureType.CODEC);
        this.worldConfigs = this.createRegistry(WorldConfig.REGISTRY_NAME, WorldConfig.CODEC);
    }

    private <T extends RegistryObject> Registry<T> createRegistry(String name, Codec<T> codec) {
        if (registries.containsKey(name)) {
            throw new IllegalArgumentException("registry named `" + name + "` has already been created");
        }
        Registry<T> registry = new Registry<>(name, codec);
        this.registries.put(name, registry);
        return registry;
    }

    public void loadAllFrom(CodecAssetLoader loader) {
        this.buildingTypes.loadFrom(loader);
        this.featureTypes.loadFrom(loader);
        this.worldConfigs.loadFrom(loader);
    }

    public void loadAllTextures(AssetManager manager) {
        TextureLoader.loadTextures(manager, this.buildingTypes);
        TextureLoader.loadTextures(manager, this.featureTypes);
        TextureLoader.loadTextures(manager, this.worldConfigs);
    }

    public void freezeAll() {
        this.buildingTypes.freeze();
        this.worldConfigs.freeze();
        this.featureTypes.freeze();
    }

    public Registry<?> getByName(String name) {
        if (!this.registries.containsKey(name)) {
            throw new IllegalArgumentException("unknown registry `" + name + "`");
        }
        return this.registries.get(name);
    }

    public Registry<BuildingType> getBuildingTypes() {
        return buildingTypes;
    }

    public Registry<FeatureType> getFeatureTypes() {
        return featureTypes;
    }

    public Registry<WorldConfig> getWorldConfigs() {
        return worldConfigs;
    }
}
