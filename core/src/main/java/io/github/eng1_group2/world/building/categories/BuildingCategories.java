package io.github.eng1_group2.world.building.categories;

import com.badlogic.gdx.graphics.Color;
import io.github.eng1_group2.registry.Registry;

public class BuildingCategories {
    public static final Registry<BuildingCategory> REGISTRY = new Registry<>(BuildingCategory.REGISTRY_NAME);

    private static final BuildingCategory ACCOMMODATION = register("accommodation", "Accommodation", Color.BLUE);
    private static final BuildingCategory TEACHING = register("teaching", "Teaching space", Color.RED);
    private static final BuildingCategory NOM_NOMS = register("nom_noms", "Nom Noms", Color.GREEN);
    private static final BuildingCategory RECREATION = register("recreation", "Recreation", Color.MAGENTA);

    private static BuildingCategory register(String id, String name, Color colour) {
        return REGISTRY.register(new BuildingCategory(id, name, colour));
    }

    static {
        REGISTRY.freeze();
    }

    private BuildingCategories() {}
}
