package me.dreamdevs.randomlootentity.managers;

import lombok.Getter;
import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.objects.RandomItem;
import me.dreamdevs.randomlootentity.utils.ColourUtil;
import me.dreamdevs.randomlootentity.utils.ItemUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ItemManager {

	private @Getter final List<RandomItem> randomItems;
	private final File file = new File(RandomLootEntityMain.getInstance().getDataFolder(), "items.yml");

	public ItemManager(JavaPlugin plugin) {
		this.randomItems = new LinkedList<>();

		if (!file.exists()) {
			plugin.saveResource("items.yml",true);
		}

		this.load();
	}

	public void load() {
		this.randomItems.clear();

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection section = config.getConfigurationSection("Items");

		for (String key : section.getKeys(false)) {
			ItemStack itemStack = ItemUtil.parsedBasicItem(section.getString(key+".Material","STONE"), section.getInt(key+".Amount",1));

			ItemMeta itemMeta = itemStack.getItemMeta();

			if (section.getString(key+".DisplayName") != null) {
				itemMeta.setDisplayName(ColourUtil.colorize(section.getString(key+".DisplayName")));
			}

			if (section.getString(key+".DisplayLore") != null) {
				itemMeta.setLore(ColourUtil.colouredLore(section.getStringList(key+".DisplayLore")));
			}

			if (section.getConfigurationSection(key+".ItemOptions") != null) {
				ConfigurationSection itemOptionsSection = section.getConfigurationSection(key+".ItemOptions");

				boolean unbreakable = itemOptionsSection.getBoolean("Unbreakable",false);
				boolean glowing = itemOptionsSection.getBoolean("Glowing",false);

				if (unbreakable) {
					itemMeta.setUnbreakable(true);
				}

				if (glowing) {
					itemStack.addEnchantment(Enchantment.DURABILITY, 1);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				}
			}

			if (section.getConfigurationSection(key+".Attributes") != null) {
				ConfigurationSection attributesSection = section.getConfigurationSection(key+".Attributes");
				final String generic = "GENERIC_";

				for (String attributeId : attributesSection.getKeys(false)) {
					Attribute attribute = Attribute.valueOf(generic+attributeId.toUpperCase());
					if (attribute == null) {
						continue;
					}

					double value = attributesSection.getDouble(attributeId);

					AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), attributeId, value, AttributeModifier.Operation.ADD_NUMBER, ItemUtil.getEquipmentSlot(itemStack));

					itemMeta.addAttributeModifier(attribute, attributeModifier);
				}
			}

			itemStack.setItemMeta(itemMeta);

			if (section.getConfigurationSection(key+".Enchantments") != null) {
				ConfigurationSection enchantmentsSection = section.getConfigurationSection(key+".Enchantments");

				for (String enchantmentId : enchantmentsSection.getKeys(false)) {
					Enchantment enchantment = Enchantment.getByName(enchantmentId);
					if (enchantment == null) {
						continue;
					}

					int level = enchantmentsSection.getInt(enchantmentId);

					itemStack.addUnsafeEnchantment(enchantment, level);
				}
			}

			this.randomItems.add(new RandomItem(key, itemStack, section.getDouble(key+".Chance")));
		}
	}

	public RandomItem getRandomItemById(String id) {
		return randomItems.stream().filter(randomItem -> randomItem.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
	}

}