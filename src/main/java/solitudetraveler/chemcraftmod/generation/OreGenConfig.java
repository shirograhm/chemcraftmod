package solitudetraveler.chemcraftmod.generation;

import net.minecraftforge.common.ForgeConfigSpec;

class OreGenConfig {
    static ForgeConfigSpec.IntValue chance;
    static ForgeConfigSpec.BooleanValue generate_overworld;

    static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("Ore generation config...");

        chance = server
                .comment("Maximum number of ore veins per chunk.")
                .defineInRange("oregen.chance", 10, 1, 100000);
        generate_overworld = server
                .comment("Whether or not you want chemcraft ore generation.")
                .define("oregen.generate_overworld", true);
    }
}
