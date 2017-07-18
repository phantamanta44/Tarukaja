package io.github.phantamanta44.tarukaja.attribute;

import io.github.phantamanta44.tarukaja.constant.LangConst;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class TKAttributes {

    public static final IAttribute ACCURACY = new RangedAttribute(null, LangConst.ATTR_ACCURACY, 1D, 0D, 1D);
    public static final IAttribute EVASION = new RangedAttribute(null, LangConst.ATTR_EVASION, 0D, 0D, 1D);

    public static final IAttribute AFF_WEAK = new BitmaskAttribute(LangConst.ATTR_WEAK, 0);
    public static final IAttribute AFF_RESIST = new BitmaskAttribute(LangConst.ATTR_RESIST, 0);
    public static final IAttribute AFF_ABSORB = new BitmaskAttribute(LangConst.ATTR_ABSORB, 0);
    public static final IAttribute AFF_BLOCK = new BitmaskAttribute(LangConst.ATTR_BLOCK, 0);
    public static final IAttribute AFF_REFLECT = new BitmaskAttribute(LangConst.ATTR_REFLECT, 0);

}
