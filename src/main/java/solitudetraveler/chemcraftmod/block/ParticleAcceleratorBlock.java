package solitudetraveler.chemcraftmod.block;

import net.minecraft.block.Block;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import solitudetraveler.chemcraftmod.tileentity.ParticleAcceleratorTileEntity;

import javax.annotation.Nullable;

public class ParticleAcceleratorBlock extends Block {
    public ParticleAcceleratorBlock(ResourceLocation name, Block.Properties props) {
        super(props);

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
}
