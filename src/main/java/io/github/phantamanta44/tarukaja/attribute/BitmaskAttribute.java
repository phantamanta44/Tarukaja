package io.github.phantamanta44.tarukaja.attribute;

import io.github.phantamanta44.tarukaja.TKMod;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class BitmaskAttribute implements IAttribute, IAttributeInstanceProvider {

    private final String name;
    private final int defaultValue;

    public BitmaskAttribute(String name, int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getAttributeUnlocalizedName() {
        return name;
    }

    @Override
    public double clampValue(double value) {
        return validateMask(Double.valueOf(value).intValue());
    }

    public int validateMask(int mask) {
        return mask;
    }

    @Override
    public double getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean getShouldWatch() {
        return true;
    }

    @Nullable
    @Override
    public IAttribute getParent() {
        return null;
    }

    @Override
    public IAttributeInstance newInstance(AbstractAttributeMap map) {
        return new Instance(map, this);
    }

    public static class Instance extends ModifiableAttributeInstance {

        private Field fieldNeedsUpdate;
        private int cachedValue;

        public Instance(AbstractAttributeMap containing, IAttribute attribute) {
            super(containing, attribute);
        }

        @Override
        public double getAttributeValue() {
            return getModifiedBitmask();
        }

        public int getModifiedBitmask() {
            if (fieldNeedsUpdate == null) {
                try {
                    fieldNeedsUpdate = ReflectionHelper.findField(ModifiableAttributeInstance.class, "field_111133_g", "needsUpdate");
                    fieldNeedsUpdate.setAccessible(true);
                } catch (Exception e) {
                    TKMod.LOGGER.error("Failed to retrieve ModifiableAttributeInstance#needsValue!", e);
                }
            }
            try {
                if (fieldNeedsUpdate.getBoolean(this)) {
                    cachedValue = computeValue();
                    fieldNeedsUpdate.set(this, true);
                }
            } catch (Exception e) {
                TKMod.LOGGER.error("Failed to access ModifiableAttributeInstance#needsValue!", e);
            }
            return cachedValue;
        }

        private int computeValue() {
            int value = getModifiersByOperation(0).stream()
                    .mapToInt(m -> Double.valueOf(m.getAmount()).intValue())
                    .reduce(0, (a, b) -> a | b);
            value = getModifiersByOperation(3).stream()
                    .mapToInt(m -> Double.valueOf(m.getAmount()).intValue())
                    .reduce(value, (a, b) -> a & ~b);
            value = getModifiersByOperation(4).stream()
                    .mapToInt(m -> Double.valueOf(m.getAmount()).intValue())
                    .reduce(value, (a, b) -> a & b);
            return ((BitmaskAttribute)getAttribute()).validateMask(value);
        }

    }

}
