package io.github.eng1_group2.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/** config for a texture. used in any config files that need to pass textures so the texture path and origin are linked.
 * @param path the path to the texture file
 * @param origin the first pixel of the texture in the file. 0,0 is the top left.
 */
public record TextureConfig(
    String path,
    Vec2 origin
) {
    public static final Codec<TextureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("path").forGetter(TextureConfig::path),
        Vec2.CODEC.fieldOf("origin").forGetter(TextureConfig::origin)
    ).apply(instance, TextureConfig::new));

    public TextureRegion getTextureRegion(AssetManager assetManager, Vec2 size) {
        return new TextureRegion(
            assetManager.get(path, Texture.class),
            origin.x(),
            origin.y(),
            size.x(),
            size.y()
        );
    }
}
