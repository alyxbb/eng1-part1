package io.github.eng1_group2.world.building.categories;

import com.badlogic.gdx.graphics.Color;
import com.mojang.serialization.Codec;
import io.github.eng1_group2.registry.RegistryEntryCodec;
import io.github.eng1_group2.registry.RegistryObject;

public record BuildingCategory(String id, String name, Color color) implements RegistryObject {
    public static final String REGISTRY_NAME = "building_category";
    public static final Codec<BuildingCategory> REGISTRY_CODEC = new RegistryEntryCodec<>(REGISTRY_NAME);

}
