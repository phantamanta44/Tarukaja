package io.github.phantamanta44.tarukaja.combat;

import io.github.phantamanta44.tarukaja.attribute.TKAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.DamageSource;

import java.util.Collections;
import java.util.EnumSet;

public enum Ailment {

    BURN(Affinity.WIND, Affinity.NUCLEAR) {
        @Override
        public void tick(EntityLivingBase target) {
            target.attackEntityFrom(DamageSource.onFire, 3F);
        }
    },
    FREEZE(Affinity.PHYS, Affinity.NUCLEAR) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(ATTR_FREEZE_MOVEMENT_SPEED);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ATTR_FREEZE_MOVEMENT_SPEED);
        }
    },
    SHOCK(Affinity.NUCLEAR) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(ATTR_SHOCK_ATTACK_SPEED);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(ATTR_SHOCK_ATTACK_SPEED);
        }
    },
    DIZZY(Affinity.PSY, Affinity.PHYS) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(TKAttributes.ACCURACY).applyModifier(ATTR_DIZZY_ACCURACY);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(TKAttributes.ACCURACY).removeModifier(ATTR_DIZZY_ACCURACY);
        }
    },
    FORGET(Affinity.PSY), // TODO Implement
    SLEEP(Affinity.PSY, Affinity.PHYS) { // TODO Wake on taking damage
        @Override
        public void tick(EntityLivingBase target) {
            target.heal(target.getMaxHealth() / 20F);
            // TODO Restore SP
        }
    },
    CONFUSE(Affinity.PSY), // Implement
    DOWN() {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ATTR_DOWN_ARMOUR);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ATTR_DOWN_ARMOUR);
        }
    },
    FEAR(Affinity.PSY) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(ATTR_FEAR_MOVEMENT_SPEED);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(ATTR_FEAR_MOVEMENT_SPEED);
        }
    },
    DESPAIR(Affinity.PSY) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(ATTR_DESPAIR_ATTACK_SPEED);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(ATTR_DESPAIR_ATTACK_SPEED);
        }

        @Override
        public void tick(EntityLivingBase target) {
            // TODO Drain SP
            target.attackEntityFrom(DamageSource.outOfWorld, 6F);
        }
    },
    RAGE(Affinity.PSY) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(ATTR_RAGE_BUFF);
            target.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ATTR_RAGE_DEBUFF);
            target.getEntityAttribute(TKAttributes.ACCURACY).applyModifier(ATTR_RAGE_DEBUFF);
            target.getEntityAttribute(TKAttributes.EVASION).applyModifier(ATTR_RAGE_DEBUFF);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(ATTR_RAGE_BUFF);
            target.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ATTR_RAGE_DEBUFF);
            target.getEntityAttribute(TKAttributes.ACCURACY).removeModifier(ATTR_RAGE_DEBUFF);
            target.getEntityAttribute(TKAttributes.EVASION).removeModifier(ATTR_RAGE_DEBUFF);
        }
    },
    BRAINWASH(Affinity.PSY), // TODO Find some way to use this
    HUNGER(Affinity.PSY) {
        @Override
        public void onApply(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(ATTR_HUNGER_ATTACK_DAMAGE);
        }

        @Override
        public void onCleanse(EntityLivingBase target) {
            target.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(ATTR_HUNGER_ATTACK_DAMAGE);
        }
    };

    private static final AttributeModifier ATTR_FREEZE_MOVEMENT_SPEED = new AttributeModifier("Freeze", -1D, 2).setSaved(true);
    private static final AttributeModifier ATTR_SHOCK_ATTACK_SPEED = new AttributeModifier("Shock", -0.9D, 2).setSaved(true);
    private static final AttributeModifier ATTR_DIZZY_ACCURACY = new AttributeModifier("Dizzy", -0.6D, 0).setSaved(true);
    private static final AttributeModifier ATTR_DOWN_ARMOUR = new AttributeModifier("Down", -0.5D, 2).setSaved(true);
    private static final AttributeModifier ATTR_FEAR_MOVEMENT_SPEED = new AttributeModifier("Fear", -0.5D, 2).setSaved(true);
    private static final AttributeModifier ATTR_DESPAIR_ATTACK_SPEED = new AttributeModifier("Despair", -0.9D, 2).setSaved(true);
    private static final AttributeModifier ATTR_RAGE_BUFF = new AttributeModifier("Rage Buff", 0.3D, 2).setSaved(true);
    private static final AttributeModifier ATTR_RAGE_DEBUFF = new AttributeModifier("Rage Debuff", -0.3D, 2).setSaved(true);
    private static final AttributeModifier ATTR_HUNGER_ATTACK_DAMAGE = new AttributeModifier("Hunger", -0.5D, 2).setSaved(true);

    final EnumSet<Affinity> technicals;

    Ailment(Affinity... technicals) {
        this.technicals = EnumSet.noneOf(Affinity.class);
        Collections.addAll(this.technicals, technicals);
    }

    public boolean inflictsTechnical(Affinity affinity) {
        return technicals.contains(affinity);
    }

    public void onApply(EntityLivingBase target) {
        // NO-OP
    }

    public void onCleanse(EntityLivingBase target) {
        // NO-OP
    }

    public void tick(EntityLivingBase target) {
        // NO-OP
    }

}
