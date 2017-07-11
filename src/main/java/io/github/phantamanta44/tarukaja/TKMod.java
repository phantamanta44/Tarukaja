package io.github.phantamanta44.tarukaja;

import io.github.phantamanta44.tarukaja.constant.LangConst;
import io.github.phantamanta44.tarukaja.constant.TKConst;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TKConst.MOD_ID, version = TKConst.VERSION)
public class TKMod {

    @Mod.Instance(TKConst.MOD_ID)
    public static TKMod INSTANCE;

    @SidedProxy(
            serverSide = "io.github.phantamanta44.tarukaja.CommonProxy",
            clientSide = "io.github.phantamanta44.tarukaja.client.ClientProxy")
    public static CommonProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger(TKConst.MOD_ID);
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabFinalVoyage();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit();
    }

    private static class CreativeTabFinalVoyage extends CreativeTabs {

        public CreativeTabFinalVoyage() {
            super(LangConst.CREATIVE_TAB_NAME);
        }

        @Override
        public Item getTabIconItem() {
            return Items.BLAZE_POWDER; // TODO replace?
        }

    }

}
