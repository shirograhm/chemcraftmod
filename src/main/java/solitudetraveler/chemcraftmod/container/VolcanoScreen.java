package solitudetraveler.chemcraftmod.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.VolcanoTileEntity;

public class VolcanoScreen extends ContainerScreen<VolcanoContainer> {

    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/volcano_gui.png");
    private VolcanoTileEntity volcanoTE;

    public VolcanoScreen(VolcanoContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        volcanoTE = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Mini Volcano Experiment", 5, 5, 0x4dc1ff);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 5, 41, 0x969696);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        int pixelHeight = (int) (volcanoTE.getCurrentTimeLeftScaled() * 17) - 1;
        this.blit(relX + 84, relY + 36 - pixelHeight, 177, 16 - pixelHeight, 8, 17);
    }
}
