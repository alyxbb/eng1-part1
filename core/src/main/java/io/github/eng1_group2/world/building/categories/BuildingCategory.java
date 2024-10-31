package io.github.eng1_group2.world.building.categories;

import io.github.eng1_group2.registry.RegistryObject;

public class BuildingCategory implements RegistryObject {
    private final String id;
    private final String name;

    public BuildingCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String getName() {
        return name;
    }
}
