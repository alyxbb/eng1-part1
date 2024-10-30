package io.github.eng1_group2.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.RegistryEntryCodec;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.registry.TexturedRegistryObject;
import io.github.eng1_group2.utils.TextureConfig;

import java.util.List;

public record FeatureType(String id, String name,
                          TextureConfig texture) implements RegistryObject, TexturedRegistryObject {
    public static final String REGISTRY_NAME = "feature_type";

    public static final Codec<FeatureType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(FeatureType::id),
        Codec.STRING.fieldOf("name").forGetter(FeatureType::name),
        TextureConfig.CODEC.fieldOf("texture").forGetter(FeatureType::texture)
    ).apply(instance, FeatureType::new));
    public static final Codec<FeatureType> REGISTRY_CODEC = new RegistryEntryCodec<>(REGISTRY_NAME);

    @Override
    public List<String> getTexturePaths() {
        return List.of(texture.path());
    }
}
