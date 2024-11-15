package io.github.eng1_group2.utils.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.github.eng1_group2.registry.Registries;
import io.github.eng1_group2.registry.RegistryOps;

import java.util.ArrayList;
import java.util.List;

public class CodecAssetLoader {
    private final RegistryOps<JsonElement, JsonOps> registryOps;
    private AssetManager manager;
    private List<String> assetList;
    private boolean loaded = false;

    public CodecAssetLoader(Registries registries) {
        this.registryOps = registries.ops(JsonOps.INSTANCE);
    }

    public void prepare(AssetManager manager) {
        if (this.loaded) {
            throw new IllegalStateException("CodecAssetLoader has already been prepared");
        }
        manager.setLoader(String.class, new StringLoader(manager.getFileHandleResolver()));
        String assetList = manager.getFileHandleResolver().resolve("assets.txt").readString();
        this.manager = manager;
        this.assetList = assetList.lines().toList();
        for (String asset : this.assetList) {
            if (asset.endsWith(".json")) {
                manager.load(asset, String.class);
            }
        }
        System.out.print("Preparing registry objects... ");
        manager.finishLoading();
        System.out.println("Done!");
        this.loaded = true;
    }

    public <T> List<T> findByType(String typeName, Codec<T> codec) {
        if (!this.loaded) {
            throw new IllegalStateException("AssetScanner has not been initialised");
        }
        List<T> assets = new ArrayList<>();
        for (String asset : assetList) {
            if (asset.startsWith("data/" + typeName + "/") && asset.endsWith(".json")) {
                // Found one!!!
                String content = this.manager.get(asset);
                JsonElement ele = JsonParser.parseString(content);
                DataResult<T> result = codec.decode(registryOps, ele).map(Pair::getFirst);
                T t = result.getOrThrow(JsonParseException::new);
                assets.add(t);
            }
        }
        return assets;
    }

    private static class StringLoader extends SynchronousAssetLoader<String, StringLoader.StringLoaderParameters> {
        public StringLoader(FileHandleResolver resolver) {
            super(resolver);
        }

        @Override
        public String load(AssetManager assetManager, String fileName, FileHandle file, StringLoaderParameters parameter) {
            return file.readString();
        }

        @Override
        public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, StringLoaderParameters parameter) {
            return new Array<>();
        }

        private static class StringLoaderParameters extends AssetLoaderParameters<String> {
        }
    }
}
