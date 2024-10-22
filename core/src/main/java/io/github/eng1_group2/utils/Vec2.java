package io.github.eng1_group2.utils;

public record Vec2(int x, int y) {
    public Vec2 add(Vec2 other) {
        return new Vec2(x+other.x,y+ other.y());
    }
}
