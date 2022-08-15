package kenjih.voting.commands;

import kenjih.voting.Voting;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class GetVotesCommand implements CommandExecutor {

    private final Voting plugin;
    private ArrayList<Voters> voterList;

    public GetVotesCommand(Voting plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(!player.isOp()){
            player.sendMessage("§cDazu hast du keine Rechte!");
            return false;
        }
        voterList = plugin.getVoterList();
        Collections.sort(voterList, Voters.voterNo);
        Inventory voterInv = plugin.createVotesInv(voterList);
        if(voterInv == null){
            player.sendMessage("§cDas Inventar ist null");
            return false;
        }
        player.openInventory(voterInv);
        return false;
    }
}