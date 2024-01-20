package me.dreamdevs.randomlootentity.objects;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class RandomItem {

	private @Getter final String id;

	private @Getter ItemStack itemStack;
	private @Getter double chance;

	public RandomItem(String id, ItemStack itemStack, double chance) {
		this.id = id;
		this.itemStack = itemStack;
		this.chance = chance;
	}

}