package solitudetraveler.chemcraftmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.container.GeneratorContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.item.Items.*;

public class GeneratorTileEntity extends BasicTileEntity implements INamedContainerProvider {
    public static final int GENERATOR_INPUT = 0;
    public static final int NUMBER_GENERATOR_SLOTS = 1;

    private static Map<Item, Integer> fuelTimes = generateFuelTimes();

    private static final int MAX_POWER_CAPACITY = 28800;

    private boolean isPowered;
    private int powerRemaining;

    private static Map<Item, Integer> generateFuelTimes() {
        Map<Item, Integer> generatorTimeForItems = new LinkedHashMap<>();

        // Coal and charcoal
        generatorTimeForItems.put(COAL_BLOCK, 7200);
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
        super(BlockVariables.GENERATOR_TILE_TYPE, NUMBER_GENERATOR_SLOTS);

        this.isPowered = false;
        this.powerRemaining = 0;
    }

    public boolean isPowered() {
        return isPowered;
    }

    public float getPowerLevelScaled() {
        return 1.0f * powerRemaining / MAX_POWER_CAPACITY;
    }

    @Override
    public void tick() {
        // If world is null, return
        if(world == null) return;
        // If client, return
        if(world.isRemote) return;

        ItemStack inputStack = inventory.getStackInSlot(GENERATOR_INPUT);
        Item inputItem = inputStack.getItem();

        // If input item is valid and powerRemaining + added power <= MAX POWER
        if(fuelTimes.containsKey(inputItem) && powerRemaining + fuelTimes.get(inputItem) <= MAX_POWER_CAPACITY) {
            // Process fuel item
            powerRemaining += fuelTimes.get(inputItem);
            // Remove 1 item from fuel input slot
            inventory.setStackInSlot(GENERATOR_INPUT, ItemHandlerHelper.copyStackWithSize(inputStack, inputStack.getCount() - 1));
            // Turn on power
            isPowered = true;
        }
        // Reduce power
        powerRemaining--;
        // Check if power runs out
        if(powerRemaining == 0) {
            isPowered = false;
        }

        // Update block state with isPowered value (for texture updates based on blockstate)
        BlockState blockState = world.getBlockState(pos);
        world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, isPowered));

        super.tick();
    }

    @Override
    public void read(CompoundNBT tag) {
        this.isPowered = tag.getBoolean("isPowered");
        this.powerRemaining = tag.getInt("powerRemaining");

        super.read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT tag) {
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
}
