package io.github.eng1_group2.utils.loader;

import com.badlogic.gdx.assets.AssetManager;
import io.github.eng1_group2.registry.HasDependencies;
import io.github.eng1_group2.registry.Registry;
import io.github.eng1_group2.registry.RegistryObject;

public class ObjectLoader {
    private ObjectLoader() {
    }

    public static <T extends HasDependencies & RegistryObject> void loadDependencies(AssetManager manager, Registry<T> registry) {
        for (T entry : registry) {
            for (var pair : entry.getDependencies()) {
                manager.load(pair.getSecond(), pair.getFirst());
            }
        }
        System.out.print("Loading dependencies for registry `" + registry.getName() + "`... ");
        manager.finishLoading();
        System.out.println("Done!");
    }
}
