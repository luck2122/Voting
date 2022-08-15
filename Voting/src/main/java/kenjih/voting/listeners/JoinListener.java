package kenjih.voting.listeners;

import kenjih.voting.Voting;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class JoinListener implements Listener {

    private final Voting plugin;

    public JoinListener(Voting plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        ArrayList<Voters> voterList = plugin.getVoterList();
        Player player = event.getPlayer();
        event.setJoinMessage(player.getName() + " hat das Spiel betreten");
        if(player.isOp()) {
            player.getInventory().setItem(4, plugin.getHidePlayerItem());
            return;
        };
        Voters voter = new Voters(plugin);
        voter.setName(player.getName());
        voterList.add(voter);
        plugin.setVoterList(voterList);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event){
        ArrayList<Voters> voterList = plugin.getVoterList();
        Player player = event.getPlayer();
        if(player.isOp()) return;
        for(Voters voters : voterList){
            if(voters.getName().equals(player.getName())) {
                voterList.remove(voters);
                break;
            }
        }
        plugin.setVoterList(voterList);
    }
}