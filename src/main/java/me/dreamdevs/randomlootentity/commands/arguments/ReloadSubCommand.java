package me.dreamdevs.randomlootentity.commands.arguments;

import me.dreamdevs.randomlootentity.Language;
import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.commands.ArgumentCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadSubCommand implements ArgumentCommand {

	@Override
	public boolean execute(CommandSender commandSender, String[] args) {
		Language.reloadLanguage();
		RandomLootEntityMain.getInstance().getItemManager().load();
		RandomLootEntityMain.getInstance().getEntityManager().load();
		commandSender.sendMessage(Language.COMMAND_RELOAD_ALL.toString());
		return true;
	}

	@Override
	public String getHelpText() {
		return "/randomlootentity reload - reloads plugin configuration";
	}

	@Override
	public String getPermission() {
		return "randomlootentity.admin.reload";
	}

	@Override
	public List<String> getArguments() {
		return Collections.emptyList();
	}
}