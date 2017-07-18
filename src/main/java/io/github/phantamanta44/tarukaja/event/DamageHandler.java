package io.github.phantamanta44.tarukaja.event;

import io.github.phantamanta44.tarukaja.attribute.BitmaskAttribute;
import io.github.phantamanta44.tarukaja.attribute.TKAttributes;
import io.github.phantamanta44.tarukaja.combat.Affinity;
import io.github.phantamanta44.tarukaja.network.SPacketEntityDamage;
import io.github.phantamanta44.tarukaja.network.TKNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DamageHandler {

    public static final float DAMAGE_FACTOR = 2F;

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) { // TODO Implement absorb and reflect
        EntityLivingBase victim = event.getEntityLiving();
        Affinity attackType = Affinity.resolve(event.getSource());
        if (attackType != null) {
            int bitmask = ((BitmaskAttribute.Instance)victim.getEntityAttribute(TKAttributes.AFF_BLOCK)).getModifiedBitmask();
            if ((bitmask & attackType.ordinal()) != 0) {
                // TODO block
                return;
            }
            Entity source = event.getSource().getEntity();
            if (source != null && source instanceof EntityLivingBase) {
                IAttributeInstance accAttr = ((EntityLivingBase)source).getEntityAttribute(TKAttributes.ACCURACY);
                if (accAttr != null && source.worldObj.rand.nextDouble() < accAttr.getAttributeValue()) {
                    // TODO miss
                    return;
                }
            }
            IAttributeInstance evadeAttr = victim.getEntityAttribute(TKAttributes.EVASION);
            if (evadeAttr != null && victim.worldObj.rand.nextDouble() > evadeAttr.getAttributeValue()) {
                // TODO miss
                return;
            }
            bitmask = ((BitmaskAttribute.Instance)victim.getEntityAttribute(TKAttributes.AFF_RESIST)).getModifiedBitmask();
            bitmask = ((BitmaskAttribute.Instance)victim.getEntityAttribute(TKAttributes.AFF_WEAK)).getModifiedBitmask();
            // TODO resist and weak
        }
        doDamage(event, victim, 1F);
    }

    private static void doDamage(LivingHurtEvent event, EntityLivingBase entity, float factor) {
        float magn = (float)Math.pow(10, Math.floor(Math.log(event.getAmount())) - 1);
        float rand = event.getEntity().worldObj.rand.nextFloat() * magn - magn / 2F;
        event.setAmount(factor * event.getAmount() / DAMAGE_FACTOR + rand);
        TKNetwork.INSTANCE.sendToAllAround(
                new SPacketEntityDamage(entity, event.getSource(), event.getAmount()),
                new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 16));
    }

}
