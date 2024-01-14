package me.dreamdevs.randomlootentity.commands;

import lombok.Getter;
import me.dreamdevs.randomlootentity.Language;
import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.commands.arguments.ReloadSubCommand;
import me.dreamdevs.randomlootentity.commands.arguments.ToggleOptionSubCommand;
import me.dreamdevs.randomlootentity.utils.ColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandHandler implements TabExecutor {

	private final @Getter HashMap<String, Class<? extends ArgumentCommand>> arguments;

	public CommandHandler(@NotNull RandomLootEntityMain plugin) {
		this.arguments = new HashMap<>();
		registerCommand("reload", ReloadSubCommand.class);
		registerCommand("toggle", ToggleOptionSubCommand.class);
		Objects.requireNonNull(plugin.getCommand("randomlootentity")).setExecutor(this);
		Objects.requireNonNull(plugin.getCommand("randomlootentity")).setTabCompleter(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
		try {
			if(strings.length >= 1) {
				if(arguments.containsKey(strings[0])) {
					Class<? extends ArgumentCommand> argumentCommand = arguments.get(strings[0]).asSubclass(ArgumentCommand.class);
					ArgumentCommand argument = argumentCommand.getConstructor().newInstance();
					if(commandSender.hasPermission(argument.getPermission())) {
						if(strings.length > 1 && argument.getArguments().isEmpty()) {
							commandSender.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
							return true;
						}
						argument.execute(commandSender, strings);
						return true;
					} else {
						commandSender.sendMessage(Language.GENERAL_NO_PERMISSION.toString());
						return true;
					}
				} else {
					commandSender.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
					return true;
				}
			} else {
				commandSender.sendMessage(ColourUtil.colorize("&aHelp for RandomLootEntity:"));
				for(Class<? extends ArgumentCommand> argumentCommand : arguments.values()) {
					commandSender.sendMessage(ColourUtil.colorize(argumentCommand.getConstructor().newInstance().getHelpText()));
				}
				return true;
			}
		} catch (Exception e) {

		}
		return true;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
		List<String> completions = new ArrayList<>();
		if(strings.length == 1) {
			StringUtil.copyPartialMatches(strings[0], arguments.keySet(), completions);
			Collections.sort(completions);
			return completions;
		} else if(strings.length == 2 && !getArgumentsForSubcommand(strings[0]).isEmpty()) {
			return getArgumentsForSubcommand(strings[0]);
		} else return Collections.emptyList();
	}

	private List<String> getArgumentsForSubcommand(@NotNull String subcommand) {
		List<String> listArguments = new ArrayList<>();
		try {
			listArguments = arguments.get(subcommand).getConstructor().newInstance().getArguments();
			Collections.sort(listArguments);
		} catch (Exception e) {

		}
		return listArguments;
	}

	public void registerCommand(String command, Class<? extends ArgumentCommand> clazz) {
		arguments.put(command, clazz);
		try {
			Bukkit.getPluginManager().addPermission(new Permission(clazz.getConstructor().newInstance().getPermission()));
		} catch (Exception e) {

		}
	}

}