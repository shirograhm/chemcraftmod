package solitudetraveler.chemcraftmod.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;
import solitudetraveler.chemcraftmod.tileentity.DeconstructorTileEntity;

public class DeconstructorScreen extends ContainerScreen<DeconstructorContainer> {

    private ResourceLocation GUI = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/gui/deconstructor_gui.png");
    private DeconstructorTileEntity deconstructorTE;

    public DeconstructorScreen(DeconstructorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        deconstructorTE = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Molecular Deconstructor", 5, 5, 0xff9a4d);
        drawString(Minecraft.getInstance().fontRenderer, "Inventory", 5, 72, 0x969696);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        if(deconstructorTE.isDeconstructing()) {
            this.blit(relX + 67, relY + 34, 176, 17, 24, 17);

            int k = (int) (deconstructorTE.getDeconstructionTimeScaled() * 23);
            this.blit(relX + 67, relY + 34, 176, 0, k + 1, 17);
        }
    }
}
