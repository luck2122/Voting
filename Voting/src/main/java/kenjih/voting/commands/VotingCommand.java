package kenjih.voting.commands;

import kenjih.voting.Voting;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class VotingCommand implements CommandExecutor{

    private final Voting plugin;

    public VotingCommand(Voting plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;
        Inventory votersInv = plugin.createVoterInv();
        if(votersInv == null){
            player.sendMessage("§cDas Inventar ist null");
            return false;
        }
        player.openInventory(votersInv);
        return false;
    }
}