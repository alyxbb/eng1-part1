package io.github.eng1_group2.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.RegistryEntryCodec;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.registry.TexturedRegistryObject;
import io.github.eng1_group2.utils.Vec2;

public record FeatureType(String id, String name, String texturePath,
                          Vec2 textureOrigin) implements RegistryObject, TexturedRegistryObject.Simple {
    public static final String REGISTRY_NAME = "feature_type";

    public static final Codec<FeatureType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(FeatureType::id),
        Codec.STRING.fieldOf("name").forGetter(FeatureType::name),
        Codec.STRING.fieldOf("texture").forGetter(FeatureType::texturePath),
        Vec2.CODEC.fieldOf("textureOrigin").forGetter(FeatureType::textureOrigin)
    ).apply(instance, FeatureType::new));
    public static final Codec<FeatureType> REGISTRY_CODEC = new RegistryEntryCodec<>(REGISTRY_NAME);
}
