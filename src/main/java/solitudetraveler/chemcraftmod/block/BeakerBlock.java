package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import solitudetraveler.chemcraftmod.tileentity.BeakerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BeakerBlock extends Block {
    private static final VoxelShape SHAPE = VoxelShapes.or(
        Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 1.0D, 12.0D),
        Block.makeCuboidShape(4.0D, 1.0D, 3.0D, 12.0D, 12.0D, 4.0D),
        Block.makeCuboidShape(12.0D, 1.0D, 4.0D, 13.0D, 12.0D, 12.0D),
        Block.makeCuboidShape(4.0D, 1.0D, 12.0D, 12.0D, 12.0D, 13.0D),
        Block.makeCuboidShape(3.0D, 1.0D, 4.0D, 4.0D, 12.0D, 12.0D)
    );

    public BeakerBlock(ResourceLocation name, Block.Properties properties) {
        super(properties);

        setRegistryName(name);
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BeakerTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
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
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(tileEntity instanceof BeakerTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (BeakerTileEntity) tileEntity);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }
}
