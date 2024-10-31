package io.github.eng1_group2.world.building.categories;

import io.github.eng1_group2.registry.Registry;

public class BuildingCategories {
    public static final Registry<BuildingCategory> REGISTRY = new Registry<>("building_category");

    private static final BuildingCategory ACCOMMODATION = register("accommodation", "Accommodation");
    private static final BuildingCategory TEACHING = register("teaching", "Teaching space");
    private static final BuildingCategory NOM_NOMS = register("nom_noms", "Nom Noms");
    private static final BuildingCategory RECREATION = register("recreation", "Recreation");

    private static BuildingCategory register(String id, String name) {
        return REGISTRY.register(new BuildingCategory(id, name));
    }

    static {
        REGISTRY.freeze();
    }

    private BuildingCategories() {}
}
