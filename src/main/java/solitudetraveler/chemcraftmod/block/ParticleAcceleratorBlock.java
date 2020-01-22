package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import solitudetraveler.chemcraftmod.tileentity.ParticleAcceleratorTileEntity;

import javax.annotation.Nullable;

public class ParticleAcceleratorBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public ParticleAcceleratorBlock(ResourceLocation name, Block.Properties props) {
        super(props);

        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
        setRegistryName(name);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ParticleAcceleratorTileEntity();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
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

            if(tileEntity instanceof ParticleAcceleratorTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (ParticleAcceleratorTileEntity) tileEntity);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // Test for built particle accelerator
    private boolean acceleratorIsBuilt(World world, BlockPos blockPos) {
        Block[] blocksToCheck = new Block[] {
                world.getBlockState(blockPos.add(-1, -1, 0)).getBlock(),
                world.getBlockState(blockPos.add(1, -1, 0)).getBlock(),
                world.getBlockState(blockPos.add(0, -1, 0)).getBlock(),
                world.getBlockState(blockPos.add(0, -1, -1)).getBlock(),
                world.getBlockState(blockPos.add(0, -1, 1)).getBlock()
        };

        for(Block b : blocksToCheck) {
            if(!(b instanceof ElectromagnetBlock)) {
                return false;
            }
        }
        return true;
    }
}
