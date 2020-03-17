package solitudetraveler.chemcraftmod.generation;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import solitudetraveler.chemcraftmod.block.BlockList;

public class OreGeneration {

    public static void setupOreGeneration() {
        if(OreGenConfig.generate_overworld.get()) {
            for(Biome biome : ForgeRegistries.BIOMES) {
                // Dolostone generation
                addOreGenerationToBiome(biome, 48, 48, 96, 18, BlockList.dolostone.getDefaultState(), OreGenConfig.large_vein.get());
                // Copper ore generation
                addOreGenerationToBiome(biome, 0, 0, 76, 12, BlockList.copper_ore.getDefaultState(), OreGenConfig.large_vein.get());
                // Tin ore generation
                addOreGenerationToBiome(biome, 0, 0, 76, 12, BlockList.tin_ore.getDefaultState(), OreGenConfig.large_vein.get());
                // Aluminium ore generation
                addOreGenerationToBiome(biome, 0, 0, 64, 8, BlockList.aluminium_ore.getDefaultState(), OreGenConfig.medium_vein.get());
                // Lead ore generation
                addOreGenerationToBiome(biome, 0, 0, 48, 4, BlockList.lead_ore.getDefaultState(), OreGenConfig.large_vein.get());
                // Nickel ore generation
                addOreGenerationToBiome(biome, 0, 0, 44, 4, BlockList.nickel_ore.getDefaultState(), OreGenConfig.medium_vein.get());
                // Silver ore generation
                addOreGenerationToBiome(biome, 0, 0, 32, 3, BlockList.silver_ore.getDefaultState(), OreGenConfig.medium_vein.get());
                // Platinum ore generation
                addOreGenerationToBiome(biome, 0, 0, 24, 2, BlockList.platinum_ore.getDefaultState(), OreGenConfig.medium_vein.get());
                // Chromium ore generation
                addOreGenerationToBiome(biome, 0, 0, 16, 1, BlockList.chromium_ore.getDefaultState(), OreGenConfig.small_vein.get());
            }
        }
    }

    private static void addOreGenerationToBiome(Biome biome, int topOffset, int bottomOffset, int maximum, int count, BlockState blockState, int chance) {
        CountRangeConfig placement = new CountRangeConfig(count, bottomOffset, topOffset, maximum);
        OreFeatureConfig config = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, blockState, chance);

        biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, config, Placement.COUNT_RANGE, placement));
    }
}
