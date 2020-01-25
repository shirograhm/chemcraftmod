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
import solitudetraveler.chemcraftmod.block.BlockVariables;
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

    private boolean isRunning;
    private int currentTimeLeft;

    public VolcanoTileEntity() {
        super(BlockVariables.VOLCANO_TILE_TYPE, NUMBER_VOLCANO_SLOTS);

        isRunning = false;
        currentTimeLeft = -1;
    }

    private boolean checkRequirements() {
        Item item1 = inventory.getStackInSlot(VOLCANO_SLOT_1).getItem();
        Item item2 = inventory.getStackInSlot(VOLCANO_SLOT_2).getItem();

        return item1.equals(ItemList.vinegar) && item2.equals(ItemList.baking_soda);
    }

    public float getCurrentTimeLeftScaled() {
        return (VOLCANO_RUN_TIME - currentTimeLeft) * 1.0f / VOLCANO_RUN_TIME;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If on client, return
        if(world.isRemote) return;

        if(checkRequirements()) {
            // Begin running volcano ticks if not running right now
            if(!isRunning) {
                isRunning = true;
                currentTimeLeft = VOLCANO_RUN_TIME;
            }
            else {
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
            isRunning = false;
            currentTimeLeft = 0;
        }

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        isRunning = tag.getBoolean("isRunning");
        currentTimeLeft = tag.getInt("timeLeft");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putBoolean("isRunning", isRunning);
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
