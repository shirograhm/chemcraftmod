package solitudetraveler.chemcraftmod.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.container.VolcanoContainer;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.VolcanoTileEntity;

public class VolcanoScreen extends ContainerScreen<VolcanoContainer> {

    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/volcano_gui.png");
    private VolcanoTileEntity volcanoTileEntity;

    public VolcanoScreen(VolcanoContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        volcanoTileEntity = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Mini Volcano Experiment", 7, 6, ScreenColors.volcano_title);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 7, 43, ScreenColors.inventory_title);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(volcanoTileEntity.isRunning()) {
            int currentHeight = Math.round(volcanoTileEntity.getCurrentTimeLeftScaled() * 17);
            this.blit(relX + 84, relY + 37 - currentHeight, 176, 16 - currentHeight, 8, currentHeight);
        }
    }
}
