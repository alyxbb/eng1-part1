package io.github.eng1_group2.world;

import com.mojang.datafixers.util.Pair;
import io.github.eng1_group2.world.building.BuildingType;
import io.github.eng1_group2.world.building.categories.BuildingCategory;

import java.util.*;

public class WorldStats {
    private final Map<BuildingCategory, Integer> buildingsConstructedByCategory;
    private int balanceSpent = 0;

    public WorldStats() {
        buildingsConstructedByCategory = new HashMap<>();
    }

    public void onBuildingConstructing(BuildingType building) {
        this.balanceSpent += building.cost();
    }

    public void onBuildingConstructed(BuildingType building) {
        this.buildingsConstructedByCategory.merge(building.category(), 1, Integer::sum);
    }

    public Map<BuildingCategory, Integer> getBuildingsConstructedByCategory() {
        return Collections.unmodifiableMap(buildingsConstructedByCategory);
    }

    public List<Pair<String, Integer>> getAllStats() {
        List<Pair<String, Integer>> stats = new ArrayList<>();
        for (var entry : buildingsConstructedByCategory.entrySet()) {
            stats.add(new Pair<>(entry.getKey().name() + " constructed", entry.getValue()));
        }
        stats.add(new Pair<>("money spent", this.balanceSpent));
        stats.sort(Comparator.comparing(Pair::getFirst));
        return stats;
    }

    public int getBalanceSpent() {
        return balanceSpent;
    }

    public String formatStats() {
        StringBuilder builder = new StringBuilder();
        for (var stat : getAllStats()) {
            builder.append(stat.getFirst()).append(": ").append(stat.getSecond()).append('\n');
        }
        return builder.toString();
    }
}
