package io.github.phantamanta44.tarukaja;

import io.github.phantamanta44.tarukaja.event.DamageHandler;
import io.github.phantamanta44.tarukaja.network.TKNetwork;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void onPreInit() {
        TKNetwork.init();
    }

    public void onInit() {
        MinecraftForge.EVENT_BUS.register(new DamageHandler());
    }

    public void onPostInit() {
        // NO-OP
    }

}
