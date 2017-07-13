package io.github.phantamanta44.tarukaja.network;

import io.github.phantamanta44.tarukaja.client.util.PopoffHelper;
import io.github.phantamanta44.tarukaja.util.LowUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SPacketEntityDamage implements IMessage {

    private static final Map<String, DamageSource> GENERIC_SOURCES = Stream.of(
            DamageSource.inFire, DamageSource.lightningBolt, DamageSource.onFire, DamageSource.lava,
            DamageSource.hotFloor, DamageSource.inWall, DamageSource.drown, DamageSource.starve,
            DamageSource.cactus, DamageSource.fall, DamageSource.flyIntoWall, DamageSource.outOfWorld,
            DamageSource.generic, DamageSource.magic, DamageSource.wither, DamageSource.anvil,
            DamageSource.fallingBlock, DamageSource.dragonBreath)
            .collect(Collectors.toMap(DamageSource::getDamageType, s -> s));
    
    private int entityId;
    private DamageSource source;
    private float amount, health;

    public SPacketEntityDamage(EntityLivingBase entity, DamageSource source, float amount) {
        this.entityId = entity.getEntityId();
        this.source = source;
        this.amount = amount;
        this.health = entity.getHealth();
    }

    @Deprecated
    public SPacketEntityDamage() {
        // NO-OP
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        amount = buf.readFloat();
        health = buf.readFloat();
        String type = LowUtils.readStr(buf);
        if ((source = GENERIC_SOURCES.get(type)) == null) {
            byte flag = buf.readByte();
            source = (flag & 1) != 0
                    ? new EntityDamageSource(type, Minecraft.getMinecraft().theWorld.getEntityByID(buf.readInt()))
                    : new DamageSource(type);
            if ((flag & 2) != 0)
                source.setDamageBypassesArmor();
            if ((flag & 4) != 0)
                source.setFireDamage();
            if ((flag & 8) != 0)
                source.setProjectile();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeFloat(amount);
        buf.writeFloat(health);
        LowUtils.writeStr(buf, source.getDamageType());
        if (!GENERIC_SOURCES.containsKey(source.getDamageType())) {
            byte flag = 0;
            if (source instanceof EntityDamageSource)
                flag |= 1;
            if (source.isUnblockable())
                flag |= 2;
            if (source.isFireDamage())
                flag |= 4;
            if (source.isProjectile())
                flag |= 8;
            buf.writeByte(flag);
            if ((flag & 1) != 0)
                buf.writeInt(source.getEntity().getEntityId());
        }
    }

    public static class CHandler implements IMessageHandler<SPacketEntityDamage, IMessage> {

        @Override
        public IMessage onMessage(SPacketEntityDamage msg, MessageContext ctx) {
            EntityLivingBase entity = (EntityLivingBase)Minecraft.getMinecraft().theWorld
                    .getEntityByID(msg.entityId);
            if (entity != null) {
                entity.setHealth(msg.health);
                PopoffHelper.onDamage(entity, msg.source, msg.amount);
            }
            return null;
        }

    }

}
