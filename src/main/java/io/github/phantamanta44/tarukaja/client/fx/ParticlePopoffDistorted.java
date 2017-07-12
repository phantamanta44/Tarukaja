package io.github.phantamanta44.tarukaja.client.fx;

import io.github.phantamanta44.tarukaja.constant.ResConst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ParticlePopoffDistorted extends ParticleMod {

    private final double vMin, vMax;

    public ParticlePopoffDistorted(EntityLivingBase entity, int yIndex) {
        super(entity.getEntityWorld(), entity.posX, entity.posY + entity.getEyeHeight() + 1, entity.posZ);
        this.vMin = yIndex * 64 / 256D;
        this.vMax = (yIndex + 1) * 64 / 256D;
        this.particleMaxAge = 32;
        this.particleScale = 0;
        this.particleAlpha = 1F;
    }

    @Override
    public void onUpdate() {
        if (particleAge++ >= particleMaxAge)
            setExpired();
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (particleAge < 6) {
            particleScale += 1.75F;
            posY += 0.08D;
        } else if (particleAge > 26) {
            particleAlpha -= 0.166F;
            posY += 0.08D;
        }
    }

    @Override
    protected void render(Tessellator tess, Entity entity, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResConst.TEX_TEXTS);
        GlStateManager.color(1F, 1F, 1F, particleAlpha);
        tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        long time = System.currentTimeMillis();
        Vec3d[] vertices = calculateVertices(
                0.03F * MathHelper.cos((time % 225) * 0.0279F),
                1F + 0.03F * MathHelper.cos((time % 175) * 0.0359F),
                0.03F * MathHelper.sin((time % 150) * 0.0419F),
                0.5F + 0.03F * MathHelper.sin((time % 200) * 0.0314F));
        tess.getBuffer().pos(vertices[0].xCoord, vertices[0].yCoord, vertices[0].zCoord).tex(1D, vMax).endVertex();
        tess.getBuffer().pos(vertices[1].xCoord, vertices[1].yCoord, vertices[1].zCoord).tex(1D, vMin).endVertex();
        tess.getBuffer().pos(vertices[2].xCoord, vertices[2].yCoord, vertices[2].zCoord).tex(0.5D, vMin).endVertex();
        tess.getBuffer().pos(vertices[3].xCoord, vertices[3].yCoord, vertices[3].zCoord).tex(0.5D, vMax).endVertex();

        vertices = calculateVertices(0, 1, 0, 0.5D);
        tess.getBuffer().pos(vertices[0].xCoord, vertices[0].yCoord, vertices[0].zCoord).tex(0.5D, vMax).endVertex();
        tess.getBuffer().pos(vertices[1].xCoord, vertices[1].yCoord, vertices[1].zCoord).tex(0.5D, vMin).endVertex();
        tess.getBuffer().pos(vertices[2].xCoord, vertices[2].yCoord, vertices[2].zCoord).tex(0D, vMin).endVertex();
        tess.getBuffer().pos(vertices[3].xCoord, vertices[3].yCoord, vertices[3].zCoord).tex(0D, vMax).endVertex();

        tess.draw();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

}
