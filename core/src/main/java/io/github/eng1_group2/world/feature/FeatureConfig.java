package io.github.eng1_group2.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.utils.Vec2;

public record FeatureConfig(
        FeatureType type,
        Vec2 position,
        Vec2 size
) {
    public static final Codec<FeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FeatureType.REGISTRY_CODEC.fieldOf("type").forGetter(FeatureConfig::type),
            Vec2.CODEC.fieldOf("position").forGetter(FeatureConfig::position),
            Vec2.CODEC.fieldOf("size").forGetter(FeatureConfig::size)
    ).apply(instance, FeatureConfig::new));
}
