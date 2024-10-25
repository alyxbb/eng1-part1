package io.github.eng1_group2.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.registry.TexturedRegistryObject;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.feature.FeatureConfig;

import java.util.List;

public record WorldConfig(
        String id,
        String name,
        Vec2 mapSize,
        String backgroundTexture,
        List<FeatureConfig> features
) implements RegistryObject, TexturedRegistryObject {
    public static final String REGISTRY_NAME = "world";

    public static final Codec<WorldConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(WorldConfig::id),
            Codec.STRING.fieldOf("name").forGetter(WorldConfig::name),
            Vec2.CODEC.fieldOf("map_size").forGetter(WorldConfig::mapSize),
            Codec.STRING.fieldOf("background_texture").forGetter(WorldConfig::backgroundTexture),
            FeatureConfig.CODEC.listOf().fieldOf("features").forGetter(WorldConfig::features)
    ).apply(instance, WorldConfig::new));

    @Override
    public List<String> getTexturePaths() {
        return List.of(
            this.backgroundTexture
        );
    }
}
