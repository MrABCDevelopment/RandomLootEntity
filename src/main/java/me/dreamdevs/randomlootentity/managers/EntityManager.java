package me.dreamdevs.randomlootentity.managers;

import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.objects.RandomEntity;
import me.dreamdevs.randomlootentity.objects.RandomItem;
import me.dreamdevs.randomlootentity.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class EntityManager {

	private final List<RandomEntity> randomEntities;

	private final File entitiesFile = new File(RandomLootEntityMain.getInstance().getDataFolder(), "entities.yml");

	public EntityManager() {
		this.randomEntities = new LinkedList<>();

		if (!entitiesFile.exists()) {
			RandomLootEntityMain.getInstance().saveResource(entitiesFile.getPath(), true);
		}

		this.load();
	}

	public void load() {
		this.randomEntities.clear();

		FileConfiguration config = YamlConfiguration.loadConfiguration(entitiesFile);
		ConfigurationSection section = config.getConfigurationSection("entites");

		for (String key : section.getKeys(false)) {
			if (EntityType.valueOf(key.toUpperCase()) == null) {
				continue;
			}

			RandomEntity randomEntity = new RandomEntity(EntityType.valueOf(key.toUpperCase()));

			randomEntity.setClearDefaultDrops(section.getBoolean(key+".ClearDefaultDrops",false));
			randomEntity.setExperienceDrops(section.getBoolean(key+".ClearDefaultExperience",false));

			for (String item : section.getStringList(key+".Drops")) {
				String[] strings = item.split(":");

				ItemStack itemStack = ItemUtil.parsedBasicItem(strings[0], Integer.parseInt(strings[1]));

				RandomItem randomItem = new RandomItem(itemStack, Double.parseDouble(strings[2]));
				randomEntity.getRandomItems().add(randomItem);
			}

		}
	}

	public RandomEntity getRandomEntity(EntityType entityType) {
		return randomEntities.stream()
				.filter(randomEntity -> randomEntity.getEntityType().equals(entityType))
				.findFirst()
				.orElse(null);
	}

}