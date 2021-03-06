package me.armar.plugins.autorank.pathbuilder.requirement;

import me.armar.plugins.autorank.language.Lang;
import me.staartvin.plugins.pluginlibrary.Library;
import me.staartvin.plugins.pluginlibrary.hooks.QuestsFatPigsAreFatHook;
import org.bukkit.entity.Player;

// Quests plugin that is used is from FatPigsAreFat, hence FPAF in name.

public class QuestsFPAFCompleteSpecificQuestRequirement extends AbstractRequirement {

    private QuestsFatPigsAreFatHook handler = null;
    private String questName = null;

    @Override
    public String getDescription() {
        return Lang.QUESTS_COMPLETE_SPECIFIC_QUEST_REQUIREMENT.getConfigValue(questName);
    }

    @Override
    public String getProgress(final Player player) {
        return handler.isQuestCompleted(player.getUniqueId(), questName) + "";
    }

    @Override
    public boolean meetsRequirement(final Player player) {

        if (!handler.isAvailable())
            return false;

        return handler.isQuestCompleted(player.getUniqueId(), questName);
    }

    @Override
    public boolean setOptions(final String[] options) {

        // Add dependency
        addDependency(Library.QUESTS_FATPIGSAREFAT);

        handler = (QuestsFatPigsAreFatHook) this.getDependencyManager().getLibraryHook(Library.QUESTS_FATPIGSAREFAT);

        if (options.length > 0) {
            questName = options[0];
        } else {
            this.registerWarningMessage("No quest name was provided.");
            return false;
        }

        if (handler == null || !handler.isAvailable()) {
            this.registerWarningMessage("Quests is not available!");
            return false;
        }

        return true;
    }
}
