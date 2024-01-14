package me.dreamdevs.randomlootentity.objects;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class RandomItem {

	private @Getter ItemStack itemStack;
	private @Getter double chance;

	public RandomItem(ItemStack itemStack, double chance) {
		this.itemStack = itemStack;
		this.chance = chance;
	}

}