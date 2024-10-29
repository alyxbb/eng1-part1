package io.github.eng1_group2.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TextureConfig (
    String path,
    Vec2 origin
) {
    public static final Codec<TextureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("path").forGetter(TextureConfig::path),
        Vec2.CODEC.fieldOf("origin").forGetter(TextureConfig::origin)
    ).apply(instance, TextureConfig::new));
}
