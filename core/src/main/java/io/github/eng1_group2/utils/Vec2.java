package io.github.eng1_group2.utils;

import com.mojang.serialization.Codec;

import java.util.List;

public record Vec2(int x, int y) {
    public static final Codec<Vec2> CODEC = Codec.INT.listOf(2, 2).xmap(
        l -> new Vec2(l.get(0), l.get(1)),
        v -> List.of(v.x, v.y)
    );

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 add(int i) {
        return new Vec2(this.x+i, this.y+ i);
    }

    public Vec2 sub(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public Vec2 sub(int i) {
        return new Vec2(this.x - i, this.y - i);
    }

    public Vec2 mul(int i) {
        return new Vec2(x * i, y * i);
    }
}
