package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.VolcanoContainer;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class VolcanoTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int VOLCANO_SLOT_1 = 0;
    public static final int VOLCANO_SLOT_2 = 1;
    public static final int NUMBER_VOLCANO_SLOTS = 2;

    private ItemStackHandler inventory;

    public VolcanoTileEntity() {
        super(BlockList.VOLCANO_TILE_TYPE);

        inventory = generateInventory();
    }

    private ItemStackHandler generateInventory() {
         return new ItemStackHandler(NUMBER_VOLCANO_SLOTS) {
             @Override
             protected void onContentsChanged(int slot) {
                 markDirty();
             }

             @Override
             public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                 Item item = stack.getItem();
                 return (item.equals(ItemList.sodium_bicarbonate) ||
                         item.equals(ItemList.acetic_acid));
             }
         };
    }

    private boolean checkRequirements(Item item1, Item item2) {
        return (item1.equals(ItemList.sodium_bicarbonate) && item2.equals(ItemList.acetic_acid)) ||
                (item1.equals(ItemList.acetic_acid) && item2.equals(ItemList.sodium_bicarbonate));
    }

    @Override
    public void tick() {
        // CLIENT SIDE
        if (world != null && !world.isRemote) {
            return;
        }

        // SERVER SIDE
        if(checkRequirements(inventory.getStackInSlot(VOLCANO_SLOT_1).getItem(), inventory.getStackInSlot(VOLCANO_SLOT_2).getItem())) {
            Random rand = new Random();

            for (int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.POOF, true,
                        this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5,
                        (rand.nextDouble() - 0.5) / 4, 0.15, (rand.nextDouble() - 0.5) / 4);
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventory.deserializeNBT(compoundNBT);

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT compoundNBT = inventory.serializeNBT();
        tag.put("inv", compoundNBT);

        return super.write(tag);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        return new VolcanoContainer(i, world, pos, playerInventory, playerEntity);
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.getStackInSlot(index);
        inventory.setStackInSlot(index, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - count));

        return ItemHandlerHelper.copyStackWithSize(stack, count);
    }

    @Nonnull
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory.getStackInSlot(index);
        inventory.setStackInSlot(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        inventory.setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
