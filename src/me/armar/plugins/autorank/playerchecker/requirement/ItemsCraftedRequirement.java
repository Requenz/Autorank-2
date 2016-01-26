package me.armar.plugins.autorank.playerchecker.requirement;

import org.bukkit.entity.Player;

import me.armar.plugins.autorank.language.Lang;
import me.armar.plugins.autorank.statsmanager.handlers.StatsHandler;

public class ItemsCraftedRequirement extends Requirement {

	int itemsCrafted = -1;

	@Override
	public String getDescription() {

		String lang = Lang.ITEMS_CRAFTED_REQUIREMENT
				.getConfigValue(itemsCrafted + "");

		// Check if this requirement is world-specific
		if (this.isWorldSpecific()) {
			lang = lang.concat(" (in world '" + this.getWorld() + "')");
		}

		return lang;
	}

	@Override
	public String getProgress(final Player player) {
		final int progressBar = this.getStatsPlugin().getNormalStat(
				StatsHandler.statTypes.ITEMS_CRAFTED.toString(),
				player.getUniqueId(), this.getWorld());

		return progressBar + "/" + itemsCrafted;
	}

	@Override
	public boolean meetsRequirement(final Player player) {

		final int realItemsCrafted = this.getStatsPlugin().getNormalStat(
				StatsHandler.statTypes.ITEMS_CRAFTED.toString(),
				player.getUniqueId(), this.getWorld());

		return realItemsCrafted >= itemsCrafted;
	}

	@Override
	public boolean setOptions(String[] options) {

		itemsCrafted = Integer.parseInt(options[0]);

		return itemsCrafted != -1;
	}
}
