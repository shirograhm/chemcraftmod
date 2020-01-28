package solitudetraveler.chemcraftmod.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.container.ReconstructorContainer;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.ReconstructorTileEntity;

public class ReconstructorScreen extends ContainerScreen<ReconstructorContainer> {
    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/reconstructor_gui.png");
    private ReconstructorTileEntity reconstructorTE;

    public ReconstructorScreen(ReconstructorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        reconstructorTE = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Molecular Reconstructor", 5, 5, ScreenColors.GREEN);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 5, 72, ScreenColors.GREY);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(reconstructorTE.getBlockState().get(BlockStateProperties.POWERED)) {
            this.blit(relX + 89, relY + 33, 176, 17, 24, 17);
        }
    }
}
