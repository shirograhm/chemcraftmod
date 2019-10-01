package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import solitudetraveler.chemcraftmod.block.BlockList;

public class ConstructorTE extends TileEntity implements ITickableTileEntity {

    public ConstructorTE() {
        super(BlockList.CONSTRUCTOR_TE);
    }

    @Override
    public void tick() {
        if (world != null && world.isRemote) {
            System.out.println("HELLO I AM A TE");
        }
    }
}
