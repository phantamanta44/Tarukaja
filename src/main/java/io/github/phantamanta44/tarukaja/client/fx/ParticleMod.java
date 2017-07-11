package io.github.phantamanta44.tarukaja.client.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ParticleMod extends Particle {

    private Entity entity;
    private float partialTicks, rotX, rotZ, rotYZ, rotXY, rotXZ;

    protected ParticleMod(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }

    public void doActualRender(Tessellator tess) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.depthMask(false);
        render(tess, entity, partialTicks);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
    }

    protected Vec3d[] calculateVertices() {
        return calculateVertices(0, 1, 0, 1);
    }

    protected Vec3d[] calculateVertices(double offX, double scaleX, double offY, double scaleY) { // TODO offY
        float x = (float)(prevPosX + (posX - prevPosX) * (double)partialTicks - interpPosX);
        float y = (float)(prevPosY + (posY - prevPosY) * (double)partialTicks - interpPosY);
        float z = (float)(prevPosZ + (posZ - prevPosZ) * (double)partialTicks - interpPosZ);
        float scale = particleScale / 10F;
        return new Vec3d[] {
                new Vec3d(
                        x + (rotX * scale * (-scaleX + offX) - rotXY * scale * scaleY),
                        y - rotZ * scale * scaleY,
                        z + (rotYZ * scale * (-scaleX + offX) - rotXZ * scale * scaleY)),
                new Vec3d(
                        x + (rotX * scale * (-scaleX + offX) + rotXY * scale * scaleY),
                        y + rotZ * scale * scaleY,
                        z + (rotYZ * scale * (-scaleX + offX) + rotXZ * scale * scaleY)),
                new Vec3d(
                        x + (rotX * scale * (scaleX + offX) + rotXY * scale * scaleY),
                        y + rotZ * scale * scaleY,
                        z + (rotYZ * scale * (scaleX + offX) + rotXZ * scale * scaleY)),
                new Vec3d(
                        x + (rotX * scale * (scaleX + offX) - rotXY * scale * scaleY),
                        y - rotZ * scale * scaleY,
                        z + (rotYZ * scale * (scaleX + offX) - rotXZ * scale * scaleY))
        };
    }

    protected abstract void render(Tessellator tess, Entity entity, float partialTicks);

    @Override
    public void renderParticle(VertexBuffer buf, Entity entity, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
        this.entity = entity;
        this.partialTicks = partialTicks;
        this.rotX = rotX;
        this.rotZ = rotZ;
        this.rotYZ = rotYZ;
        this.rotXY = rotXY;
        this.rotXZ = rotXZ;
        ParticleRenderer.queue.offer(this);
    }

}
