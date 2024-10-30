package io.github.eng1_group2.registry;

import java.util.List;

public interface TexturedRegistryObject {
    List<String> getTexturePaths();

    // TODO: make this work with texture configs
    interface Simple extends TexturedRegistryObject {
        String texturePath();

        @Override
        default List<String> getTexturePaths() {
            return List.of(this.texturePath());
        }
    }
}
