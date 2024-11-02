package io.github.eng1_group2.world;

import com.badlogic.gdx.graphics.Texture;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.HasDependencies;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.TextureConfig;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.feature.FeatureConfig;

import java.util.List;

public record WorldConfig(
    String id,
    String name,
    Vec2 mapSize,
    TextureConfig backgroundTexture,
    List<FeatureConfig> features,
    TextureConfig incompleteBuilding,
    float gameDuration
) implements RegistryObject, HasDependencies {
    public static final String REGISTRY_NAME = "world";

    public static final Codec<WorldConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(WorldConfig::id),
        Codec.STRING.fieldOf("name").forGetter(WorldConfig::name),
        Vec2.CODEC.fieldOf("map_size").forGetter(WorldConfig::mapSize),
        TextureConfig.CODEC.fieldOf("background_texture").forGetter(WorldConfig::backgroundTexture),
        FeatureConfig.CODEC.listOf().fieldOf("features").forGetter(WorldConfig::features),
        TextureConfig.CODEC.fieldOf("incomplete_building").forGetter(WorldConfig::incompleteBuilding),
        Codec.FLOAT.fieldOf("game_duration").forGetter(WorldConfig::gameDuration)
    ).apply(instance, WorldConfig::new));

    @Override
    public List<Pair<Class<?>, String>> getDependencies() {
        return List.of(
            new Pair<>(Texture.class, this.backgroundTexture().path()),
            new Pair<>(Texture.class, this.incompleteBuilding.path())
        );
    }
}
