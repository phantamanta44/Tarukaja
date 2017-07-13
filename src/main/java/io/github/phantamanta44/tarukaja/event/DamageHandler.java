package io.github.phantamanta44.tarukaja.event;

import io.github.phantamanta44.tarukaja.network.SPacketEntityDamage;
import io.github.phantamanta44.tarukaja.network.TKNetwork;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DamageHandler {

    public static final float DAMAGE_FACTOR = 2F;

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        float magn = (float)Math.pow(10, Math.floor(Math.log(event.getAmount())) - 1);
        float rand = event.getEntity().worldObj.rand.nextFloat() * magn - magn / 2F;
        event.setAmount(event.getAmount() / DAMAGE_FACTOR + rand);
        EntityLivingBase victim = event.getEntityLiving();
        TKNetwork.INSTANCE.sendToAllAround(
                new SPacketEntityDamage(victim, event.getSource(), event.getAmount()),
                new NetworkRegistry.TargetPoint(victim.dimension, victim.posX, victim.posY, victim.posZ, 16));
    }

}
