package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ConstructorContainer extends Container {

    TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ConstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.CONSTRUCTOR_CONTAINER, id);

        tileEntity = world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        if(tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 30, 17));
                addSlot(new SlotItemHandler(h, 1, 48, 17));
                addSlot(new SlotItemHandler(h, 2, 66, 17));
                addSlot(new SlotItemHandler(h, 3, 30, 35));
                addSlot(new SlotItemHandler(h, 4, 48, 35));
                addSlot(new SlotItemHandler(h, 5, 66, 35));
                addSlot(new SlotItemHandler(h, 6, 30, 53));
                addSlot(new SlotItemHandler(h, 7, 48, 53));
                addSlot(new SlotItemHandler(h, 8, 66, 53));

                addSlot(new SlotItemHandler(h, 9, 124, 35));
            });
        }
        layoutPlayerInventorySlots();
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos()), playerIn, BlockList.constructor);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if(index < 10) {
                if(!this.mergeItemStack(stack, 10, 46, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemStack);
            } else {
                if(index < 37) {
                    if(!this.mergeItemStack(stack, 37, 46, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index < 46 && !this.mergeItemStack(stack, 10, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if(stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemStack;
    }


    private void layoutPlayerInventorySlots() {
        // Player inventory
        addSlotBox(playerInventory);

        addSlotRange(playerInventory, 0, 142);
    }

    private int addSlotRange(IItemHandler handler, int index, int y) {
        int x = 8;

        for (int i = 0; i < 9; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += 18;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler) {
        int y = 84;
        int index = 9;

        for (int j = 0; j < 3; j++) {
            index = addSlotRange(handler, index, y);
            y += 18;
        }
        return index;
    }
}
