package me.dreamdevs.randomlootentity.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EntityType;

import java.util.LinkedList;
import java.util.List;

public class RandomEntity {

	private @Getter final EntityType entityType;
	private @Getter final List<RandomItem> randomItems;

	private @Getter @Setter boolean clearDefaultDrops;
	private @Getter @Setter boolean experienceDrops;

	public RandomEntity(EntityType entityType) {
		this.entityType = entityType;
		this.randomItems = new LinkedList<>();
	}

}