package io.github.phantamanta44.tarukaja.network;

import io.github.phantamanta44.tarukaja.constant.TKConst;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class TKNetwork {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(TKConst.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(SPacketEntityDamage.CHandler.class, SPacketEntityDamage.class, 0, Side.CLIENT);
    }

}
