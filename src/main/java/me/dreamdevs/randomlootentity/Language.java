package me.dreamdevs.randomlootentity;

import me.dreamdevs.randomlootentity.utils.ColourUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public enum Language {

	// General messages
	GENERAL_LOADED_MESSAGES("GeneralMessages.Loaded-Messages","&aLoaded %AMOUNT% messages!"),
	GENERAL_NO_PERMISSION("GeneralMessages.No-Permission","&cYou do not have permission to perform this command!"),
	GENERAL_NO_ARGUMENT("GeneralMessages.No-Argument","&cThis argument does not exist!"),
	GENERAL_NO_ARGUMENTS("GeneralMessages.No-Arguments","&cThere are no arguments for this sub command!"),
	GENERAL_SOMETHING_WENT_WRONG("GeneralMessages.Something-Went-Wrong", "&cSomething went wrong!"),
	GENERAL_TOGGLE_OPTION("GeneralMessages.ToggleOption", "&7You set %OPTION% to %VALUE%"),

	COMMAND_CORRECT_USAGE("Commands.Correct-Usage","&cCorrect usage /randomlootentity <subcommand>"),
	COMMAND_RELOAD_ALL("Commands.Reload-Files","&aReloaded all configuration files!");

	private final String path;
	private final Object def;
	private static YamlConfiguration CONFIG;

	/**
	 * Lang enum constructor
	 */
	Language(String path, Object start) {
		this.path = path;
		this.def = start;
	}

	/**
	 * Set the {@code YamlConfiguration} to use.
	 *
	 * @param config
	 * The config to set.
	 */
	public static void setFile(YamlConfiguration config) {
		CONFIG = config;
	}

	public static void reloadLanguage() {
		try {
			CONFIG.load("language.yml");
		} catch (Exception e) {

		}
	}

	@Override
	public String toString() {
		return ColourUtil.colorize(Objects.requireNonNull(CONFIG.getString(getPath())));
	}

	/**
	 * Get the default value of the path.
	 *
	 * @return The default value of the path.
	 */
	public Object getDefault() {
		return this.def;
	}

	/**
	 * Get the path to the string.
	 *
	 * @return The path to the string.
	 */
	public String getPath() {
		return this.path;
	}
}
