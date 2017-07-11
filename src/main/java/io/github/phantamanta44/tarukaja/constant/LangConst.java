package io.github.phantamanta44.tarukaja.constant;

import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class LangConst {

    public static final String CREATIVE_TAB_NAME = TKConst.MOD_ID;

    public static String get(String key, Object... args) {
        return String.format(I18n.translateToLocal(key), args);
    }

}
