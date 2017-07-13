package io.github.phantamanta44.tarukaja.client.fx;

import io.github.phantamanta44.tarukaja.constant.ResConst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class ParticleHealthbar extends ParticleMod {

    private static final Map<Entity, ParticleHealthbar> targets = new HashMap<>();

    public static void tryDisplay(EntityLivingBase target) {
        ParticleHealthbar particle = targets.get(target);
        if (particle != null)
            particle.refreshTimer();
        else
            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleHealthbar(target));
    }

    private final EntityLivingBase target;
    private float healthOffset, slowHealthOffset;

    public ParticleHealthbar(EntityLivingBase entity) {
        super(entity.getEntityWorld(), entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        this.target = entity;
        this.particleMaxAge = 36;
        this.particleScale = 5;
        this.healthOffset = this.slowHealthOffset = 34 * entity.getHealth() / entity.getMaxHealth();
        targets.put(entity, this);
    }

    @Override
    public void onUpdate() {
        if (particleAge++ >= particleMaxAge)
            setExpired();
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        setPosition(target.posX, target.posY + target.getEyeHeight(), target.posZ);
        healthOffset = 34 * target.getHealth() / target.getMaxHealth();
        if (slowHealthOffset > healthOffset)
            slowHealthOffset--;
        else
            slowHealthOffset = healthOffset;
    }

    @Override
    protected void render(Tessellator tess, Entity entity, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResConst.TEX_WIDGETS);
        boolean alive = target.isEntityAlive();
        double shakeOffsetX = 0D, shakeOffsetY = 0D;
        if (target.hurtTime > 0) {
            shakeOffsetX = 0.15D - 0.3D * rand.nextDouble();
            shakeOffsetY = 0.15D - 0.3D * rand.nextDouble();
            renderLayer(tess, 1, 0, 80, shakeOffsetX, shakeOffsetY);
            if (alive)
                refreshTimer();
        } else {
            renderLayer(tess, 0, 0, 80, 0, 0);
        }
        if (alive) {
            if (slowHealthOffset > healthOffset)
                renderLayer(tess, 1, 1, 16 + slowHealthOffset, shakeOffsetX, shakeOffsetY);
            renderLayer(tess, 0, 1, 16 + healthOffset, shakeOffsetX, shakeOffsetY);
        } else {
            renderLayer(tess, 2, 1, 80, shakeOffsetX, shakeOffsetY);
        }
    }

    private void renderLayer(Tessellator tess, int tIndX, int tIndY, double xAmt, double shakeOffsetX, double shakeOffsetY) {
        tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        double xMin = tIndX * 80, yMin = tIndY * 80;
        double xMax = (xMin + xAmt) / 256D, yMax = (yMin + 80) / 256D;
        double xFactor = (xAmt) / 80D;
        xMin /= 256D;
        yMin /= 256D;
        Vec3d[] vertices = calculateVertices(1 - xFactor + shakeOffsetX - 1.5D, xFactor, shakeOffsetY + 0.5D, 1, 0.75F);
        tess.getBuffer().pos(vertices[0].xCoord, vertices[0].yCoord, vertices[0].zCoord).tex(xMax, yMax).endVertex();
        tess.getBuffer().pos(vertices[1].xCoord, vertices[1].yCoord, vertices[1].zCoord).tex(xMax, yMin).endVertex();
        tess.getBuffer().pos(vertices[2].xCoord, vertices[2].yCoord, vertices[2].zCoord).tex(xMin, yMin).endVertex();
        tess.getBuffer().pos(vertices[3].xCoord, vertices[3].yCoord, vertices[3].zCoord).tex(xMin, yMax).endVertex();
        tess.draw();
    }

    private void refreshTimer() {
        particleAge = 0;
    }

    @Override
    public void setExpired() {
        targets.remove(target);
        super.setExpired();
    }

}
