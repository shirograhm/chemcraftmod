package solitudetraveler.chemcraftmod.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import solitudetraveler.chemcraftmod.ChemCraftMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RadiationEffect extends Effect {
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
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(EffectList.RADIATION_SOURCE, amplifier);
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
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
        return true;
    }
}
