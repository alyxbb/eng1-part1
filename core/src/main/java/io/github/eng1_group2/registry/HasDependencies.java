package io.github.eng1_group2.registry;

import com.badlogic.gdx.graphics.Texture;
import com.mojang.datafixers.util.Pair;

import java.util.List;

public interface HasDependencies {
    List<Pair<Class<?>, String>> getDependencies();

    // TODO: make this work with texture configs
    interface Textured extends HasDependencies {
        String texturePath();

        @Override
        default List<Pair<Class<?>, String>> getDependencies() {
            return List.of(new Pair<>(Texture.class, this.texturePath()));
        }
    }
}
