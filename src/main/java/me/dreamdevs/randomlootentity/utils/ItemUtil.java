package me.dreamdevs.randomlootentity.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@UtilityClass
public class ItemUtil {

	private final String parseError = "&cCannot parse item with type: %MATERIAL%";

	public static ItemStack parsedBasicItem(String material, int amount) {
		try {
			return new ItemStack(Objects.requireNonNull(Material.getMaterial(material.toUpperCase())), amount);
		} catch (NullPointerException e) {
			Util.sendPluginMessage(parseError.replace("%MATERIAL%", material));
			return null;
		}
	}

	public static EquipmentSlot getEquipmentSlot(ItemStack itemStack) {
		if (itemStack.getType().name().contains("SWORD")) {
			return EquipmentSlot.HAND;
		}

		if (itemStack.getType().name().contains("HELMET")) {
			return EquipmentSlot.HEAD;
		}

		if (itemStack.getType().name().contains("CHESTPLATE")) {
			return EquipmentSlot.CHEST;
		}

		if (itemStack.getType().name().contains("LEGGINGS")) {
			return EquipmentSlot.LEGS;
		}

		if (itemStack.getType().name().contains("BOOTS")) {
			return EquipmentSlot.FEET;
		}

		return EquipmentSlot.HAND;
	}
}