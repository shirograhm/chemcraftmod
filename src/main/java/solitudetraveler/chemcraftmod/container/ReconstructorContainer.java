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
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

import javax.annotation.Nonnull;

public class ReconstructorContainer extends Container {

    ReconstructorTileEntity tileEntity;
    private IItemHandler playerInventory;

    public ReconstructorContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockList.RECONSTRUCTOR_CONTAINER, id);

        tileEntity = (ReconstructorTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_1, 30, 17));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_2, 48, 17));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_3, 66, 17));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_4, 30, 35));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_5, 48, 35));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_6, 66, 35));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_7, 30, 53));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_8, 48, 53));
        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_INPUT_9, 66, 53));

        addSlot(new Slot(tileEntity, ReconstructorTileEntity.RECONSTRUCTOR_OUTPUT, 124, 35));
        layoutPlayerInventorySlots();
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.reconstructor);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
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

    private void addSlotBox(IItemHandler handler) {
        int y = 84;
        int index = 9;

        for (int j = 0; j < 3; j++) {
            index = addSlotRange(handler, index, y);
            y += 18;
        }
    }
}
