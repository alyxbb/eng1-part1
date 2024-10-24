package io.github.eng1_group2.world.building;

import com.badlogic.gdx.graphics.Color;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.GdxCodecs;
import io.github.eng1_group2.utils.Vec2;

public record BuildingType(String id, String name, String texturePath, Vec2 size, Vec2 textureOrigin) implements RegistryObject {

    public static final Codec<BuildingType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingType::id),
        Codec.STRING.fieldOf("name").forGetter(BuildingType::name),
        Codec.STRING.fieldOf("texture").forGetter(BuildingType::texturePath),
        Vec2.CODEC.fieldOf("size").forGetter(BuildingType::size),
        Vec2.CODEC.fieldOf("textureOrigin").forGetter(BuildingType::textureOrigin)
    ).apply(instance, BuildingType::new));

    @Override
    public String getId() {
        return this.id;
    }


}
