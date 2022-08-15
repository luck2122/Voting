package kenjih.voting.listeners;

import kenjih.voting.Voting;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class ShowHidePlayerListener implements Listener {

    private final Voting plugin;
    private final ItemStack showPlayerItem;
    private final ItemStack hidePlayerItem;

    public ShowHidePlayerListener(Voting plugin, ItemStack showPlayerItem, ItemStack hidePlayerItem){
        this.plugin = plugin;
        this.hidePlayerItem = hidePlayerItem;
        this.showPlayerItem = showPlayerItem;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerItemCLick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!player.isOp())
            return;
        if(player.getInventory().getItemInMainHand().getType() == Material.GLOWSTONE_DUST){
            for(Player current : Bukkit.getOnlinePlayers()){
                for(Player hidePlayer : Bukkit.getOnlinePlayers()){
                    if(current != player && hidePlayer != player && current != hidePlayer)
                        current.hidePlayer(plugin, hidePlayer);
                }
            }
            player.getInventory().setItemInMainHand(hidePlayerItem);
        }else if(player.getInventory().getItemInMainHand().getType() == Material.REDSTONE){
            for(Player current : Bukkit.getOnlinePlayers()){
                for(Player hidePlayer : Bukkit.getOnlinePlayers()){
                    if(current != player && hidePlayer != player&& current != hidePlayer)
                        current.showPlayer(plugin, hidePlayer);
                }
            }
            player.getInventory().setItemInMainHand(showPlayerItem);
        }
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVote(InventoryClickEvent event){
        ArrayList<Voters> voterList = plugin.getVoterList();
        Player player = (Player) event.getWhoClicked();
        try {
            if(!event.getView().getTitle().equals(plugin.VOTER_INV_NAME)){
                return;
            }
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null) {
                return;
            }
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if(clickedItemMeta.getDisplayName().equals("")) {
                return;
            }
            for (Voters voter : voterList){
                if(voter.getName().equalsIgnoreCase(clickedItemMeta.getDisplayName())) {
                    voter.voteForPlayer(player);
                    player.sendMessage("voted");
                    break;
                }
            }
        }catch (Exception e){
            player.sendMessage("§cDieser Slot ist leer.");
        }
    }
}