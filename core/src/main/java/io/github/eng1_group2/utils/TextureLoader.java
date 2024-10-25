package io.github.eng1_group2.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import io.github.eng1_group2.registry.Registry;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.registry.TexturedRegistryObject;

public class TextureLoader {
    private TextureLoader() {
    }

    public static <T extends TexturedRegistryObject & RegistryObject> void loadTextures(AssetManager manager, Registry<T> registry) {
        for (T entry : registry) {
            for (String texture : entry.getTexturePaths()) {
                manager.load(texture, Texture.class);
            }
        }
        System.out.print("Loading textures for registry `" + registry.getName() + "`... ");
        manager.finishLoading();
        System.out.println("Done!");
    }
}
