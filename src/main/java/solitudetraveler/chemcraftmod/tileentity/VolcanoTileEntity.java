package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.VolcanoContainer;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class VolcanoTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int VOLCANO_SLOT_1 = 0;
    public static final int VOLCANO_SLOT_2 = 1;
    public static final int NUMBER_VOLCANO_SLOTS = 2;

    private static final int VOLCANO_RUN_TIME = 60;

    private int currentTimeLeft;

    public VolcanoTileEntity() {
        super(BlockList.VOLCANO_TILE_TYPE, NUMBER_VOLCANO_SLOTS);

        currentTimeLeft = -1;
    }

    private boolean checkRequirements(Item item1, Item item2) {
        return (item1.equals(ItemList.baking_soda) && item2.equals(ItemList.vinegar)) ||
                (item1.equals(ItemList.vinegar) && item2.equals(ItemList.baking_soda));
    }

    public double getCurrentTimeLeftScaled() {
        if(currentTimeLeft < 0) return 0.0;

        return (VOLCANO_RUN_TIME - currentTimeLeft) * 1.0 / VOLCANO_RUN_TIME;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If on client, return
        if(world.isRemote) return;

        boolean requirementsMet = checkRequirements(inventory.getStackInSlot(VOLCANO_SLOT_1).getItem(), inventory.getStackInSlot(VOLCANO_SLOT_2).getItem());

        if(requirementsMet) {
            // Begin running volcano ticks if not running right now
            if(currentTimeLeft < 0) {
                currentTimeLeft = VOLCANO_RUN_TIME;
            } else {
                currentTimeLeft--;
            }
            // Remove items inside volcano if time is up
            if(currentTimeLeft == 0) {
                inventory.extractItem(VOLCANO_SLOT_1, 1, false);
                inventory.extractItem(VOLCANO_SLOT_2, 1, false);
                // make particles
                Random rand = new Random();
                double xSpeed = (rand.nextDouble() - 0.5) / 2;
                double zSpeed = (rand.nextDouble() - 0.5) / 2;
                BlockPos start = pos.add(0, 0.75, 0);

                // Generate particles for the volcano when reaction completes
                for (int i = 0; i < 20; i++) {
                    world.addParticle(ParticleTypes.POOF, true, start.getX(), start.getY(), start.getZ(), xSpeed, 0.25, zSpeed);
                }
            }
        }
        else {
            // If requirements not met, reset current time left
            currentTimeLeft = -1;
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        currentTimeLeft = tag.getInt("timeLeft");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("timeLeft", currentTimeLeft);

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
        return new VolcanoContainer(i, world, pos, playerInventory, playerEntity);
    }
}
