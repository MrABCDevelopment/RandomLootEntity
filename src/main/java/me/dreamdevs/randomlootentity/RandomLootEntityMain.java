package me.dreamdevs.randomlootentity;

import lombok.Getter;
import me.dreamdevs.randomlootentity.commands.CommandHandler;
import me.dreamdevs.randomlootentity.managers.EntityManager;
import me.dreamdevs.randomlootentity.utils.Util;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public final class RandomLootEntityMain extends JavaPlugin {

    private @Getter static RandomLootEntityMain instance;
    private @Getter EntityManager entityManager;

    @Override
    public void onEnable() {
        instance = this;

        loadLanguage();

        this.entityManager = new EntityManager();

        new CommandHandler(this);
        new Metrics(this, 20741);
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
