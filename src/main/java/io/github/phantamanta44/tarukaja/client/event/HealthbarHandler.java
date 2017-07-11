package io.github.phantamanta44.tarukaja.client.event;

import io.github.phantamanta44.tarukaja.client.fx.ParticleHealthbar;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealthbarHandler {

    @SubscribeEvent
    public void onHit(AttackEntityEvent event) {
        ParticleHealthbar.tryDisplay(event.getTarget());
    }

}
