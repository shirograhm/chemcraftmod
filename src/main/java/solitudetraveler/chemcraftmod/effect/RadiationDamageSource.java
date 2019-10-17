package solitudetraveler.chemcraftmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class RadiationDamageSource extends DamageSource {
    public RadiationDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    @Override
    public boolean isUnblockable() {
        return true;
    }

    @Override
    public boolean isMagicDamage() {
        return true;
    }

    @Nonnull
    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        return new StringTextComponent(entityLivingBaseIn.getName().toString() + "died of radiation.");
    }
}
