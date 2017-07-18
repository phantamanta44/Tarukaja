package net.minecraft.entity.ai.attributes;

import io.github.phantamanta44.tarukaja.attribute.IAttributeInstanceProvider;

public class AttributeMapHelper {
    
    public static void registerAttribute(AbstractAttributeMap map, IAttribute attribute) {
        if (map.attributesByName.containsKey(attribute.getAttributeUnlocalizedName())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            IAttributeInstance instance = attribute instanceof IAttributeInstanceProvider
                    ? ((IAttributeInstanceProvider)attribute).newInstance(map)
                    : map.createInstance(attribute);
            map.attributesByName.put(attribute.getAttributeUnlocalizedName(), instance);
            map.attributes.put(attribute, instance);
            for (IAttribute iattribute = attribute.getParent(); iattribute != null; iattribute = iattribute.getParent())
                map.descendantsByParent.put(iattribute, attribute);
        }
    }
    
}
