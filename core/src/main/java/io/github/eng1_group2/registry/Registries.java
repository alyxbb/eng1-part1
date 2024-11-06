package io.github.eng1_group2.registry;

import com.badlogic.gdx.assets.AssetManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import io.github.eng1_group2.utils.loader.CodecAssetLoader;
import io.github.eng1_group2.utils.loader.ObjectLoader;
import io.github.eng1_group2.world.WorldConfig;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.world.building.categories.BuildingCategories;
import io.github.eng1_group2.world.building.categories.BuildingCategory;
import io.github.eng1_group2.world.feature.FeatureType;

import java.util.HashMap;
import java.util.Map;

/**
 * Registries are used to store all game content that needs to be referenced by a global ID, and the {@link Registries}
 * class encapsulates them all for easy access. This is used for things like {@link BuildingType} and {@link FeatureType}.
 *
 * <h1>Types of registries</h1>
 * <p>
 * There are two different types of registry, static and dynamic. Static registries are instances of {@link Registry},
 * while dynamic registries use {@link Registry.Dynamic}.
 * <h2>Static registries</h2>
 * <p>
 * Static registries are used for things that need to be directly referenced in source code, such as
 * {@link BuildingCategory}, as these are required to be referenced by code for things like score calculation.
 * <h2>Dynamic registries</h2>
 * <p>
 * Dynamic registries are used for objects that are designed to be uniform and where individual items do not need to be
 * referenced in code. These are used for things like {@link BuildingType}, where the code only ever needs to reference
 * the full list of items.
 * <p>
 * Dynamic registries are loaded from JSON files in the assets using the built-in libGDX {@link AssetManager}, and then
 * loaded into Java objects using {@link Codec} and {@link RegistryOps}. The ordering of calls to
 * {@link Registry.Dynamic#loadFrom(CodecAssetLoader)} in {@link Registries.Dynamic#loadAllFrom(CodecAssetLoader)} is
 * important if there are dependencies between registries, for example {@link WorldConfig} references
 * {@link FeatureType}, so must be loaded after.
 *
 * @see Registries.Dynamic
 * @see Registry
 * @see Registry.Dynamic
 */
public class Registries {
    private final Map<String, Registry<?>> registries;
    private final Registry<BuildingCategory> buildingCategories;
    private final Dynamic dynamic;

    public Registries() {
        this.registries = new HashMap<>();
        this.buildingCategories = this.storeRegistry(BuildingCategories.REGISTRY);
        this.dynamic = new Dynamic(this);
    }

    private <T extends RegistryObject> Registry<T> storeRegistry(Registry<T> registry) {
        String name = registry.getName();
        if (registries.containsKey(name)) {
            throw new IllegalArgumentException("registry named `" + name + "` has already been created");
        }
        this.registries.put(name, registry);
        return registry;
    }

    private <T extends RegistryObject> Registry.Dynamic<T> createDynamicRegistry(String name, Codec<T> codec) {
        if (registries.containsKey(name)) {
            throw new IllegalArgumentException("registry named `" + name + "` has already been created");
        }
        Registry.Dynamic<T> registry = new Registry.Dynamic<>(name, codec);
        this.registries.put(name, registry);
        return registry;
    }

    public <T, Wrapped extends DynamicOps<T>> RegistryOps<T, Wrapped> ops(Wrapped toWrap) {
        return new RegistryOps<>(toWrap, this);
    }

    public Registry<?> getByName(String name) {
        if (!this.registries.containsKey(name)) {
            throw new IllegalArgumentException("unknown registry `" + name + "`");
        }
        return this.registries.get(name);
    }

    public Registry<BuildingCategory> getBuildingCategories() {
        return buildingCategories;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public static class Dynamic {
        private final Registry.Dynamic<BuildingType> buildingTypes;
        private final Registry.Dynamic<WorldConfig> worldConfigs;
        private final Registry.Dynamic<FeatureType> featureTypes;

        private Dynamic(Registries registries) {
            this.featureTypes = registries.createDynamicRegistry(FeatureType.REGISTRY_NAME, FeatureType.CODEC);
            this.buildingTypes = registries.createDynamicRegistry(BuildingType.REGISTRY_NAME, BuildingType.CODEC);
            this.worldConfigs = registries.createDynamicRegistry(WorldConfig.REGISTRY_NAME, WorldConfig.CODEC);
        }

        public void loadAllFrom(CodecAssetLoader loader) {
            this.featureTypes.loadFrom(loader);
            this.buildingTypes.loadFrom(loader);
            this.worldConfigs.loadFrom(loader);
        }

        public void loadAllTextures(AssetManager manager) {
            ObjectLoader.loadDependencies(manager, this.featureTypes);
            ObjectLoader.loadDependencies(manager, this.buildingTypes);
            ObjectLoader.loadDependencies(manager, this.worldConfigs);
        }

        public void freezeAll() {
            this.featureTypes.freeze();
            this.buildingTypes.freeze();
            this.worldConfigs.freeze();
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
}
