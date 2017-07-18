package io.github.phantamanta44.tarukaja.attribute;

import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

@FunctionalInterface
public interface IAttributeInstanceProvider {

    IAttributeInstance newInstance(AbstractAttributeMap map);

}
