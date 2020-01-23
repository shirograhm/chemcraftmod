package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.container.GeneratorContainer;
import solitudetraveler.chemcraftmod.item.AtomicItem;
import solitudetraveler.chemcraftmod.item.ElementItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.item.Items.*;

public class GeneratorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int GENERATOR_INPUT = 0;
    public static final int NUMBER_GENERATOR_SLOTS = 1;
    private static Map<Item, Integer> fuelTimes = generateFuelTimes();

    private ItemStackHandler inventory;

    private boolean isPowered;
    private int powerRemaining;

    private static Map<Item, Integer> generateFuelTimes() {
        Map<Item, Integer> generatorTimeForItems = new LinkedHashMap<>();

        // Coal and charcoal
        generatorTimeForItems.put(COAL, 800);
        generatorTimeForItems.put(CHARCOAL, 800);
        // Stripped logs and logs
        for(Item i : ItemTags.LOGS.getAllElements()) {
            generatorTimeForItems.put(i, 240);
        }
        // Stripped wood and wood
        for(Item i : ItemTags.PLANKS.getAllElements()) {
            generatorTimeForItems.put(i, 60);
        }
        // Small wood items
        generatorTimeForItems.put(STICK, 30);

        return generatorTimeForItems;
    }

    public GeneratorTileEntity() {
        super(BlockList.GENERATOR_TILE_TYPE);

        inventory = generateInventory();
        this.isPowered = false;
        this.powerRemaining = 0;
    }

    private ItemStackHandler generateInventory() {
        return new ItemStackHandler(NUMBER_GENERATOR_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == GENERATOR_INPUT) {
                    return fuelTimes.containsKey(stack.getItem());
                }
                return false;
            }
        };
    }

    public boolean isPowered() {
        return isPowered;
    }

    public int powerRemaining() {
        return powerRemaining;
    }

    @Override
    public void tick() {

    }

    @Override
    public void read(CompoundNBT tag) {
        inventory.deserializeNBT(tag.getCompound("inv"));
        this.isPowered = tag.getBoolean("isPowered");
        this.powerRemaining = tag.getInt("powerRemaining");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", inventory.serializeNBT());
        tag.putBoolean("isPowered", isPowered);
        tag.putInt("powerRemaining", powerRemaining);

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
        if(world != null) {
            return new GeneratorContainer(i, world, pos, playerInventory, playerEntity);
        }
        return null;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(GENERATOR_INPUT) == ItemStack.EMPTY;
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
