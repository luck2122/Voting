package kenjih.voting.listeners;

import kenjih.voting.Voting;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

public class PlayerDeathListener implements Listener {

    private final Voting plugin;

    public PlayerDeathListener(Voting plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        ArrayList<Voters> voterList = plugin.getVoterList();
        for(Voters voter : voterList){
            if(voter.getName().equals(player.getName())){
                voterList.remove(voter);
                plugin.setVoterList(voterList);
                break;
            }
        }
        player.getPlayer().kickPlayer("§cDu bist gestorben!");
        Bukkit.broadcastMessage("Der Spieler" + player.getPlayer().getName() + " ist gestorben");
    }
}