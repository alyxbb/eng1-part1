package io.github.eng1_group2.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.HasDependencies;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.TextureConfig;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.List;

public record WorldConfig(
    String id,
    String name,
    Vec2 mapSize,
    TextureConfig backgroundTexture,
    List<FeatureConfig> features,
    TextureConfig incompleteBuilding,
    float gameDuration,
    SoundConfig soundConfig
) implements RegistryObject, HasDependencies {
    public static final String REGISTRY_NAME = "world";

    public static final Codec<WorldConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(WorldConfig::id),
        Codec.STRING.fieldOf("name").forGetter(WorldConfig::name),
        Vec2.CODEC.fieldOf("map_size").forGetter(WorldConfig::mapSize),
        TextureConfig.CODEC.fieldOf("background_texture").forGetter(WorldConfig::backgroundTexture),
        FeatureConfig.CODEC.listOf().fieldOf("features").forGetter(WorldConfig::features),
        TextureConfig.CODEC.fieldOf("incomplete_building").forGetter(WorldConfig::incompleteBuilding),
        Codec.FLOAT.fieldOf("game_duration").forGetter(WorldConfig::gameDuration),
        SoundConfig.CODEC.fieldOf("sound").forGetter(WorldConfig::soundConfig)
    ).apply(instance, WorldConfig::new));

    @Override
    public List<Pair<Class<?>, String>> getDependencies() {
        List<Pair<Class<?>, String>> list = new ArrayList<>();
        list.add(new Pair<>(Texture.class, this.backgroundTexture().path()));
        list.add(new Pair<>(Texture.class, this.incompleteBuilding.path()));
        list.addAll(soundConfig.getDependencies());

        return list;
    }

    public record SoundConfig(
        String buildError,
        String buildSuccess,
        String buildComplete
    ) {
        public static final Codec<SoundConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("build_error").forGetter(SoundConfig::buildError),
            Codec.STRING.fieldOf("build_success").forGetter(SoundConfig::buildSuccess),
            Codec.STRING.fieldOf("build_complete").forGetter(SoundConfig::buildComplete)
        ).apply(instance, SoundConfig::new));

        public List<Pair<Class<?>, String>> getDependencies() {
            return List.of(
                new Pair<>(com.badlogic.gdx.audio.Sound.class, buildError),
                new Pair<>(com.badlogic.gdx.audio.Sound.class, buildSuccess),
                new Pair<>(com.badlogic.gdx.audio.Sound.class, buildComplete)
            );
        }
    }
}
