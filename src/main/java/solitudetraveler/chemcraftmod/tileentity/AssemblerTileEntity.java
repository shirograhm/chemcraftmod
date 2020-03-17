package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.container.AssemblerContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AssemblerTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int ASSEMBLER_PROTON_INPUT = 0;
    public static final int ASSEMBLER_NEUTRON_INPUT = 1;
    public static final int ASSEMBLER_ELECTRON_INPUT = 2;
    public static final int ASSEMBLER_OUTPUT = 3;
    public static final int NUMBER_ASSEMBLER_SLOTS = 4;

    int storedProtons;
    int storedNeutrons;
    int storedElectrons;

    public AssemblerTileEntity() {
        super(BlockVariables.ASSEMBLER_TILE_TYPE, NUMBER_ASSEMBLER_SLOTS);

        storedProtons = 0;
        storedNeutrons = 0;
        storedElectrons = 0;
    }

    @Override
    public void tick() {
        // World missing, return
        if(world == null) return;
        // Client-side, return
        if(world.isRemote) return;



        super.tick();
    }

    public void createElement() {
        // Create Element
    }

    @Override
    public void read(CompoundNBT tag) {
        this.storedProtons = tag.getInt("storedProtons");
        this.storedNeutrons = tag.getInt("storedNeutrons");
        this.storedElectrons = tag.getInt("storedElectrons");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("storedProtons", storedProtons);
        tag.putInt("storedNeutrons", storedNeutrons);
        tag.putInt("storedElectrons", storedElectrons);

        return super.write(tag);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        ResourceLocation location = getType().getRegistryName();
        return new StringTextComponent(location != null ? location.getPath() : "");
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        if(world == null) return null;
        return new AssemblerContainer(i, world, pos, playerInventory, playerEntity);
    }
}
