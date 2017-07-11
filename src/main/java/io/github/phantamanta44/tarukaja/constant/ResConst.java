package io.github.phantamanta44.tarukaja.constant;

import net.minecraft.util.ResourceLocation;

public class ResConst {

    public static final ResourceLocation TEX_WIDGETS = getLocation("textures/gui/widgets.png");
    public static final ResourceLocation TEX_TEXTS = getLocation("textures/gui/texts.png");

    public static ResourceLocation getLocation(String location) {
        return new ResourceLocation(TKConst.MOD_ID, location);
    }

}
