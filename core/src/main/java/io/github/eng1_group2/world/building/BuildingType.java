package io.github.eng1_group2.world.building;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eng1_group2.registry.HasDependencies;
import io.github.eng1_group2.registry.RegistryObject;
import io.github.eng1_group2.utils.TextureConfig;
import io.github.eng1_group2.utils.Vec2;
import io.github.eng1_group2.world.building.categories.BuildingCategory;

public record BuildingType(String id, String name, TextureConfig texture, Vec2 size,
                           int cost,
                           float buildTime,
                           BuildingCategory category) implements RegistryObject, HasDependencies.Textured {
    public static final String REGISTRY_NAME = "building_type";

    public static final Codec<BuildingType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingType::id),
        Codec.STRING.fieldOf("name").forGetter(BuildingType::name),
        TextureConfig.CODEC.fieldOf("texture").forGetter(BuildingType::texture),
        Vec2.CODEC.fieldOf("size").forGetter(BuildingType::size),
        Codec.INT.fieldOf("cost").forGetter(BuildingType::cost),
        Codec.FLOAT.fieldOf("buildTime").forGetter(BuildingType::buildTime),
        BuildingCategory.REGISTRY_CODEC.fieldOf("category").forGetter(BuildingType::category)
    ).apply(instance, BuildingType::new));

    @Override
    public String texturePath() {
        return texture.path();
    }
}
