package io.github.phantamanta44.tarukaja.client;

import io.github.phantamanta44.tarukaja.CommonProxy;
import io.github.phantamanta44.tarukaja.client.event.HealthbarHandler;
import io.github.phantamanta44.tarukaja.client.fx.ParticleRenderer;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void onInit() {
        super.onInit();
        MinecraftForge.EVENT_BUS.register(new ParticleRenderer());
        MinecraftForge.EVENT_BUS.register(new HealthbarHandler());
    }

}
