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

public class ParticleDamage extends ParticleMod {

    private final int[] digits;

    public ParticleDamage(EntityLivingBase entity, float amount) {
        super(entity.getEntityWorld(), entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        this.digits = Integer.toString(MathHelper.ceiling_float_int(amount)).chars().map(Character::getNumericValue).toArray();
        this.particleMaxAge = 32;
        this.particleAlpha = 0F;
        this.particleScale = 3F;
    }

    @Override
    public void onUpdate() {
        if (particleAge++ >= particleMaxAge)
            setExpired();
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (particleAge < 5)
            particleAlpha += 0.25F;
    }

    @Override
    protected void render(Tessellator tess, Entity entity, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResConst.TEX_NUMBERS);
        GlStateManager.color(1F, 1F, 1F, particleAlpha);
        tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        double xBase = (double)digits.length / 2D;
        double yOffset = Math.max(3 - particleAge, 0) / 1.5D;
        int dir = 1;
        for (int i = 0; i < digits.length; i++) {
            double u = digits[i] % 4;
            double v = (digits[i] - u) * 0.078125D;
            u /= 4D;
            Vec3d[] vertices = calculateVertices(xBase - i * 1.1D, 1, yOffset * dir, 1.3D, 0.725F);
            tess.getBuffer().pos(vertices[0].xCoord, vertices[0].yCoord, vertices[0].zCoord).tex(u + 0.25D, v + 0.3125D).endVertex();
            tess.getBuffer().pos(vertices[1].xCoord, vertices[1].yCoord, vertices[1].zCoord).tex(u + 0.25D, v).endVertex();
            tess.getBuffer().pos(vertices[2].xCoord, vertices[2].yCoord, vertices[2].zCoord).tex(u, v).endVertex();
            tess.getBuffer().pos(vertices[3].xCoord, vertices[3].yCoord, vertices[3].zCoord).tex(u, v + 0.3125D).endVertex();
            dir *= -1;
        }

        tess.draw();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

}
