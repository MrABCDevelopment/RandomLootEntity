package me.dreamdevs.randomlootentity;

import lombok.Getter;
import me.dreamdevs.randomlootentity.commands.CommandHandler;
import me.dreamdevs.randomlootentity.listeners.EntityListeners;
import me.dreamdevs.randomlootentity.managers.EntityManager;
import me.dreamdevs.randomlootentity.managers.ItemManager;
import me.dreamdevs.randomlootentity.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public final class RandomLootEntityMain extends JavaPlugin {

    private @Getter static RandomLootEntityMain instance;
    private @Getter EntityManager entityManager;
    private @Getter ItemManager itemManager;

    @Override
    public void onEnable() {
        instance = this;

        loadLanguage();

        this.itemManager = new ItemManager(this);
        this.entityManager = new EntityManager();

        getServer().getPluginManager().registerEvents(new EntityListeners(), this);

        new CommandHandler(this);
        new Metrics(this, 20741);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> new UpdateChecker(this, 114501).getVersion(version -> {
            if (getDescription().getVersion().equals(version)) {
                Util.sendPluginMessage("");
                Util.sendPluginMessage("&aYour version is up to date!");
                Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                Util.sendPluginMessage("");
            } else {
                Util.sendPluginMessage("");
                Util.sendPluginMessage("&aThere is new RandomLootEntity version!");
                Util.sendPluginMessage("&aYour version: " + getDescription().getVersion());
                Util.sendPluginMessage("&aNew version: " + version);
                Util.sendPluginMessage("");
            }
        }), 10L, 20L * 1200);

    }

    @Override
    public void onDisable() {

    }

    public void loadLanguage() {
        File config = new File(getDataFolder(), "language.yml");
        Util.tryCreateFile(config);

        YamlConfiguration lang = YamlConfiguration.loadConfiguration(config);
        Stream.of(Language.values()).filter(setting -> lang.getString(setting.getPath()) == null)
                .forEach(setting -> lang.set(setting.getPath(), setting.getDefault()));

        Language.setFile(lang);
        try {
            lang.save(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
