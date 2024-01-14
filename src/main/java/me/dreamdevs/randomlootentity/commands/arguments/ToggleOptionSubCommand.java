package me.dreamdevs.randomlootentity.commands.arguments;

import me.dreamdevs.randomlootentity.Language;
import me.dreamdevs.randomlootentity.RandomLootEntityMain;
import me.dreamdevs.randomlootentity.commands.ArgumentCommand;
import me.dreamdevs.randomlootentity.objects.RandomEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ToggleOptionSubCommand implements ArgumentCommand {

	@Override
	public boolean execute(CommandSender commandSender, String[] args) {
		if (args.length != 3) {
			commandSender.sendMessage(Language.GENERAL_NO_ARGUMENTS.toString());
			return true;
		}
		if (!getArguments().contains(args[1])) {
			commandSender.sendMessage(Language.GENERAL_NO_ARGUMENT.toString());
			return true;
		}

		String entity = args[2];
		Optional<EntityType> optionalEntityType = Optional.ofNullable(EntityType.valueOf(entity.toUpperCase()));
		if (!optionalEntityType.isPresent()) {
			commandSender.sendMessage(Language.GENERAL_SOMETHING_WENT_WRONG.toString());
			return true;
		}

		RandomEntity randomEntity = RandomLootEntityMain.getInstance().getEntityManager().getRandomEntity(optionalEntityType.get());

		if (randomEntity == null) {
			commandSender.sendMessage(Language.GENERAL_SOMETHING_WENT_WRONG.toString());
			return true;
		}

		if (args[1].equalsIgnoreCase("clearDefaultDrops")) {
			randomEntity.setClearDefaultDrops(!randomEntity.isClearDefaultDrops());
			commandSender.sendMessage(Language.GENERAL_TOGGLE_OPTION.toString().replace("%OPTION%", args[1]).replace("%VALUE%", String.valueOf(randomEntity.isClearDefaultDrops())));
			return true;
		} else if (args[1].equalsIgnoreCase("clearDefaultExperience")) {
			randomEntity.setExperienceDrops(!randomEntity.isExperienceDrops());
			commandSender.sendMessage(Language.GENERAL_TOGGLE_OPTION.toString().replace("%OPTION%", args[1]).replace("%VALUE%", String.valueOf(randomEntity.isExperienceDrops())));
			return true;
		} else {
			commandSender.sendMessage(Language.GENERAL_SOMETHING_WENT_WRONG.toString());
			return true;
		}
	}

	@Override
	public String getHelpText() {
		return "/randomlootentity toggle <option> <entity> - toggles (on/off) options";
	}

	@Override
	public String getPermission() {
		return "randomlootentity.admin.toggle";
	}

	@Override
	public List<String> getArguments() {
		return Arrays.asList("clearDefaultDrops", "clearDefaultExperience");
	}
}