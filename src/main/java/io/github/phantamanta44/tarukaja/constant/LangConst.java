package io.github.phantamanta44.tarukaja.constant;

import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class LangConst {

    public static final String CREATIVE_TAB_NAME = TKConst.MOD_ID;

    public static final String ATTR_KEY = TKConst.MOD_ID + ".attribute.";

    public static final String ATTR_ACCURACY = ATTR_KEY + "accuracy";
    public static final String ATTR_EVASION = ATTR_KEY + "evasion";

    public static final String ATTR_WEAK = ATTR_KEY + "weak";
    public static final String ATTR_RESIST = ATTR_KEY + "resist";
    public static final String ATTR_ABSORB = ATTR_KEY + "absorb";
    public static final String ATTR_BLOCK = ATTR_KEY + "block";
    public static final String ATTR_REFLECT = ATTR_KEY + "reflect";

    public static String get(String key, Object... args) {
        return String.format(I18n.translateToLocal(key), args);
    }

}
