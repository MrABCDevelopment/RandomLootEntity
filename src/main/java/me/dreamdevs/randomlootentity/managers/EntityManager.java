package me.dreamdevs.randomlootentity.managers;

import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.objects.RandomEntity;
import me.dreamdevs.randomlootentity.objects.RandomItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class EntityManager {

	private final List<RandomEntity> randomEntities;

	private final File entitiesFile = new File(RandomLootEntityMain.getInstance().getDataFolder(), "entities.yml");

	public EntityManager() {
		this.randomEntities = new LinkedList<>();

		if (!entitiesFile.exists()) {
			RandomLootEntityMain.getInstance().saveResource("entities.yml", true);
		}

		this.load();
	}

	public void load() {
		this.randomEntities.clear();

		FileConfiguration config = YamlConfiguration.loadConfiguration(entitiesFile);
		ConfigurationSection section = config.getConfigurationSection("entities");

		for (String key : section.getKeys(false)) {
			if (EntityType.valueOf(key.toUpperCase()) == null) {
				continue;
			}

			RandomEntity randomEntity = new RandomEntity(EntityType.valueOf(key.toUpperCase()));

			randomEntity.setClearDefaultDrops(section.getBoolean(key+".ClearDefaultDrops",false));
			randomEntity.setExperienceDrops(section.getBoolean(key+".ClearDefaultExperience",false));
			randomEntity.setDropsIntoInventory(section.getBoolean(key+".DropsIntoInventory", false));
			randomEntity.setAdditionalExp(section.getInt(key+".AdditionalExp", 0));

			for (String item : section.getStringList(key+".Drops")) {
				Optional<RandomItem> randomItem = Optional.ofNullable(RandomLootEntityMain.getInstance().getItemManager().getRandomItemById(item));
				randomItem.ifPresent(value -> randomEntity.getRandomItems().add(value));
			}

			randomEntities.add(randomEntity);
		}
	}

	public RandomEntity getRandomEntity(EntityType entityType) {
		return randomEntities.stream()
				.filter(randomEntity -> randomEntity.getEntityType().equals(entityType))
				.findFirst()
				.orElse(null);
	}

}