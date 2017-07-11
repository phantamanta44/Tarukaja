package io.github.phantamanta44.tarukaja.client.util;

import io.github.phantamanta44.tarukaja.client.fx.ParticleHealthbar;
import io.github.phantamanta44.tarukaja.client.fx.ParticlePopoff;
import io.github.phantamanta44.tarukaja.client.fx.ParticlePopoffDistorted;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

public class PopoffUtils {

    public static void onDamage(EntityLivingBase entity, DamageSource source, float amount) {
        if (source != DamageSource.generic && entity != Minecraft.getMinecraft().thePlayer) {
            boolean canShield = entityCanShield(entity, source);
            if (entity.isEntityInvulnerable(source)
                    || (source.isFireDamage() && entity.isPotionActive(MobEffects.FIRE_RESISTANCE))
                    || (canShield && source.isProjectile())
                    || (entity.isPotionActive(MobEffects.RESISTANCE) && entity.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() >= 4)) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePopoff(entity, 0, 3));
            } else if ((float)entity.hurtResistantTime > (float)entity.maxHurtResistantTime / 2.0F) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePopoff(entity, 1, 3));
            } else if (canShield
                    || ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)) {
                System.out.println("anvil");
                ParticleHealthbar.tryDisplay(entity);
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePopoffDistorted(entity, 2));
            } else if (entity.isEntityAlive()) {
                ParticleHealthbar.tryDisplay(entity);
            }
        }
    }

    private static boolean entityCanShield(EntityLivingBase entity, DamageSource source) {
        if (!source.isUnblockable() && entity.isActiveItemStackBlocking()) {
            Vec3d srcLoc = source.getDamageLocation();
            if (srcLoc != null) {
                Vec3d facingDir = entity.getLook(1.0F);
                Vec3d facingPos = srcLoc.subtractReverse(new Vec3d(entity.posX, entity.posY, entity.posZ)).normalize();
                facingPos = new Vec3d(facingPos.xCoord, 0.0D, facingPos.zCoord);
                if (facingPos.dotProduct(facingDir) < 0.0D)
                    return true;
            }
        }
        return false;
    }

}
