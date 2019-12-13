package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class ElectromagnetBlock extends Block {
    public ElectromagnetBlock(ResourceLocation name, Block.Properties props) {
        super(props);

        setRegistryName(name);
    }
}
