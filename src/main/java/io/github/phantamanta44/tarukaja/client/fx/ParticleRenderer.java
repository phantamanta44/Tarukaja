package io.github.phantamanta44.tarukaja.client.fx;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Deque;
import java.util.LinkedList;

public class ParticleRenderer {

    static final Deque<ParticleMod> queue = new LinkedList<>();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Tessellator tess = Tessellator.getInstance();
        while (!queue.isEmpty())
            queue.pop().doActualRender(tess);
    }

}
