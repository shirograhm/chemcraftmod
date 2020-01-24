package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import solitudetraveler.chemcraftmod.tileentity.AcceleratorTileEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class AcceleratorBlock extends Block {
    public AcceleratorBlock(ResourceLocation name, Block.Properties props) {
        super(props);

        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        );
        setRegistryName(name);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AcceleratorTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(acceleratorIsBuilt(worldIn, pos) && !worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
            return true;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(tileEntity instanceof AcceleratorTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (AcceleratorTileEntity) tileEntity);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        if(blockState != null) {
            blockState = blockState.with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
        }
        return blockState;
    }

    // Test for built particle accelerator
    private boolean acceleratorIsBuilt(World world, BlockPos blockPos) {
        Direction dir = world.getBlockState(blockPos).get(BlockStateProperties.HORIZONTAL_FACING);
        ArrayList<BlockPos> positionsToCheck = new ArrayList<>();

        int minX = 0;
        int maxX = 0;
        int minZ = 0;
        int maxZ = 0;

        switch(dir) {
            case NORTH:
                minX = -3;
                maxX = 3;
                minZ = 0;
                maxZ = 6;
                break;
            case SOUTH:
                minX = -3;
                maxX = 3;
                minZ = -6;
                maxZ = 0;
                break;
            case EAST:
                minX = -6;
                maxX = 0;
                minZ = -3;
                maxZ = 3;
                break;
            case WEST:
                minX = 0;
                maxX = 6;
                minZ = -3;
                maxZ = 3;
                break;
            default:
                LOGGER.debug("Invalid direction on block placed at " + blockPos.toString());
                break;
        }

        for(int x = minX; x <= maxX; x++) {
            BlockPos temp1 = blockPos.add(x, 0, minZ);
            BlockPos temp2 = blockPos.add(x, 0, maxZ);

            if (temp1.compareTo(blockPos) != 0) positionsToCheck.add(temp1);
            if (temp2.compareTo(blockPos) != 0) positionsToCheck.add(temp2);
        }

        for(int z = minZ; z <= maxZ; z++) {
            BlockPos temp1 = blockPos.add(minX, 0, z);
            BlockPos temp2 = blockPos.add(maxX, 0, z);

            if (temp1.compareTo(blockPos) != 0) positionsToCheck.add(temp1);
            if (temp2.compareTo(blockPos) != 0) positionsToCheck.add(temp2);
        }

        for(BlockPos bp : positionsToCheck) {
            Block check = world.getBlockState(bp).getBlock();

            if(!(check instanceof ElectromagnetBlock)) return false;
        }
        // If all electro blocks, return true
        return true;
    }
}
