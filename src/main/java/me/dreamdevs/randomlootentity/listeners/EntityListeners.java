package me.dreamdevs.randomlootentity.listeners;

import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.objects.RandomEntity;
import me.dreamdevs.randomlootentity.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListeners implements Listener {

	@EventHandler
	public void onDeathEntity(EntityDeathEvent event) {
		if (RandomLootEntityMain.getInstance().getEntityManager().getRandomEntity(event.getEntityType()) != null) {
			RandomEntity randomEntity = RandomLootEntityMain.getInstance().getEntityManager().getRandomEntity(event.getEntityType());

			if (!randomEntity.isClearDefaultDrops()) {
				event.getDrops().clear();
			}

			if (!randomEntity.isExperienceDrops()) {
				event.setDroppedExp(0);
			}

			if (!randomEntity.getRandomItems().isEmpty()) {
				randomEntity.getRandomItems().stream().filter(randomItem -> Util.chance(randomItem.getChance()))
						.forEachOrdered(randomItem -> event.getDrops().add(randomItem.getItemStack()));
			}
		}
	}

}