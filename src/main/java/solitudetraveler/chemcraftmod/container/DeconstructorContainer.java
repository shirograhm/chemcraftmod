package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.handler.DeconstructorRecipeHandler;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;

import javax.annotation.Nonnull;

public class DeconstructorContainer extends Container {

    DeconstructorTileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public DeconstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.DECONSTRUCTOR_CONTAINER, id);

        tileEntity = (DeconstructorTileEntity) world.getTileEntity(pos);
        playerEntity = player;
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_INPUT, 44, 35));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_1, 98, 17));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_2, 98, 35));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_3, 98, 53));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_4, 116, 17));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_5, 116, 35));
        addSlot(new Slot(tileEntity, DeconstructorTileEntity.DECONSTRUCTOR_OUTPUT_6, 116, 53));
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, BlockList.deconstructor);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if(index < DeconstructorTileEntity.NUMBER_DECONSTRUCTOR_SLOTS) {
                if (!this.mergeItemStack(stack, DeconstructorTileEntity.NUMBER_DECONSTRUCTOR_SLOTS, DeconstructorTileEntity.NUMBER_DECONSTRUCTOR_SLOTS + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(DeconstructorRecipeHandler.isDeconstructible(stack.getItem())) {
                    if (!this.mergeItemStack(stack, 0, 1, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    if (!this.mergeItemStack(stack, DeconstructorTileEntity.NUMBER_DECONSTRUCTOR_SLOTS, DeconstructorTileEntity.NUMBER_DECONSTRUCTOR_SLOTS + 36, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        return itemStack;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hot bar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
}
