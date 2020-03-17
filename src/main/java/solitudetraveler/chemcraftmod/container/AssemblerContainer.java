package solitudetraveler.chemcraftmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import solitudetraveler.chemcraftmod.block.BlockList;
import solitudetraveler.chemcraftmod.block.BlockVariables;
import solitudetraveler.chemcraftmod.tileentity.AssemblerTileEntity;

import javax.annotation.Nonnull;

public class AssemblerContainer extends Container {
    public AssemblerTileEntity tileEntity;

    private IItemHandler playerInventory;

    public AssemblerContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
        super(BlockVariables.ASSEMBLER_CONTAINER, id);

        tileEntity = (AssemblerTileEntity) world.getTileEntity(pos);
        playerInventory = new InvWrapper(playerInv);

        addSlot(new Slot(tileEntity, AssemblerTileEntity.ASSEMBLER_PROTON_INPUT, 0, 0));
        addSlot(new Slot(tileEntity, AssemblerTileEntity.ASSEMBLER_NEUTRON_INPUT, 20, 0));
        addSlot(new Slot(tileEntity, AssemblerTileEntity.ASSEMBLER_ELECTRON_INPUT, 40, 0));
        addSlot(new Slot(tileEntity, AssemblerTileEntity.ASSEMBLER_OUTPUT, 60, 0));
        layoutPlayerInventorySlots(8, 76);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockList.assembler);
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
