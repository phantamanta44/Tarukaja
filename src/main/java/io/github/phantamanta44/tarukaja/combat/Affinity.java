package io.github.phantamanta44.tarukaja.combat;

import net.minecraft.util.DamageSource;

public enum Affinity {

    PHYS(null),
    GUN(null),

    FIRE(Ailment.BURN),
    ICE(Ailment.FREEZE),
    ELEC(Ailment.SHOCK),
    WIND(null),
    PSY(null),
    NUCLEAR(null),
    BLESS(null),
    CURSE(null),
    ALMIGHTY(null);

    public final Ailment ailment;

    Affinity(Ailment ailment) {
        this.ailment = ailment;
    }

    public static Affinity resolve(DamageSource source) {

    }

}
