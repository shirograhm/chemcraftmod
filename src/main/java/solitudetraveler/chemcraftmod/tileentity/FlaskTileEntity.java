package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.FlaskContainer;
import solitudetraveler.chemcraftmod.handler.FlaskRecipeHandler;
import solitudetraveler.chemcraftmod.item.CompoundItem;
import solitudetraveler.chemcraftmod.item.ElementItem;
import solitudetraveler.chemcraftmod.item.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class FlaskTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final int FLASK_INPUT_SLOT_1 = 0;
    public static final int FLASK_INPUT_SLOT_2 = 1;
    public static final int FLASK_INPUT_SLOT_3 = 2;
    public static final int FLASK_INPUT_SLOT_4 = 3;
    public static final int FLASK_INPUT_SLOT_5 = 4;
    public static final int FLASK_OUTPUT_SLOT_1 = 5;
    public static final int FLASK_OUTPUT_SLOT_2 = 6;

    public static final int NUMBER_FLASK_SLOTS = 7;

    private IItemHandler inventory = new ItemStackHandler(NUMBER_FLASK_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            Item item = stack.getItem();

            return (item instanceof CompoundItem || item instanceof ElementItem);
        }
    };
    private LazyOptional<IItemHandler> inventoryHandler = LazyOptional.of(() -> inventory);

    public FlaskTileEntity() {
        super(BlockList.FLASK_TILE_TYPE);
    }

    @Override
    public void tick() {
        // Check for all possible recipes
        ItemStackHandler handler = (ItemStackHandler) inventory;

        ItemStack[] inStacks = new ItemStack[]{
                handler.getStackInSlot(FLASK_INPUT_SLOT_1),
                handler.getStackInSlot(FLASK_INPUT_SLOT_2),
                handler.getStackInSlot(FLASK_INPUT_SLOT_3),
                handler.getStackInSlot(FLASK_INPUT_SLOT_4),
                handler.getStackInSlot(FLASK_INPUT_SLOT_5)
        };
        ItemStack[] outStacks = FlaskRecipeHandler.getOutputForInputArray(inStacks);

        if(!FlaskRecipeHandler.areOutputsEqual(outStacks, FlaskRecipeHandler.emptyOutput)) {
            handler.setStackInSlot(FLASK_OUTPUT_SLOT_1, outStacks[0]);
            handler.setStackInSlot(FLASK_OUTPUT_SLOT_2, outStacks[1]);
        }

        boolean hasBakingSoda = false;
        boolean hasVinegar = false;

        for(ItemStack stack : inStacks) {
            if(stack.getItem() == ItemList.sodium_bicarbonate) hasBakingSoda = true;
            if(stack.getItem() == ItemList.acetic_acid) hasVinegar = true;
        }

        if(hasBakingSoda && hasVinegar) {
            if(world != null && !world.isRemote) playEruption();
        }
    }

    private void playEruption() {
        Random rand = new Random();

        world.addParticle(null, true,
                pos.getX(),pos.getY() + 1, pos.getZ(),
                rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT compoundNBT = tag.getCompound("inv");
        inventoryHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(compoundNBT));

        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compoundNBT);
        });
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FlaskContainer(i, world, pos, playerInventory, playerEntity);
    }
}
