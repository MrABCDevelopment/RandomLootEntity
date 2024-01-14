package me.dreamdevs.randomlootentity.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Util {

	public static void sendPluginMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(ColourUtil.colorize(message));
	}

	public static boolean chance(double chance) {
		return Math.random() < chance;
	}

	public static void tryCreateFile(@NotNull File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// To do nothing
			}
		}
	}

}
