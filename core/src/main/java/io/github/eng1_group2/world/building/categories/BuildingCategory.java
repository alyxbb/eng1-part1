package io.github.eng1_group2.world.building.categories;

import com.badlogic.gdx.graphics.Color;
import com.mojang.serialization.Codec;
import io.github.eng1_group2.registry.RegistryEntryCodec;
import io.github.eng1_group2.registry.RegistryObject;

public class BuildingCategory implements RegistryObject {
    public static final String REGISTRY_NAME = "building_category";
    public static final Codec<BuildingCategory> REGISTRY_CODEC = new RegistryEntryCodec<>(REGISTRY_NAME);

    private final String id;
    private final String name;
    private final Color color;

    public BuildingCategory(String id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public String id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
