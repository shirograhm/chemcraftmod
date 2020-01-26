package solitudetraveler.chemcraftmod.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.main.ChemCraftMod;

import javax.annotation.Nonnull;

public class RadiationEffect extends Effect {
    private final ResourceLocation iconTexture = new ResourceLocation(ChemCraftMod.MOD_ID, "textures/mob_effect/radiation.png");

    public RadiationEffect(ResourceLocation name) {
        super(EffectType.HARMFUL, 5149514);

        setRegistryName(name);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Radiation";
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Radiation");
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getTextureManager().bindTexture(iconTexture);
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z) {
        gui.getMinecraft().getTextureManager().bindTexture(iconTexture);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(EffectList.RADIATION_SOURCE, amplifier);
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int shift = 50 >> amplifier;
        if(shift > 0) {
            return duration % shift == 0;
        }
        return true;
    }
}
