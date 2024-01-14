package me.dreamdevs.randomlootentity.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
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
}