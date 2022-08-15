package kenjih.voting;

import kenjih.voting.commands.GetVotesCommand;
import kenjih.voting.commands.VotingCommand;
import kenjih.voting.listeners.JoinListener;
import kenjih.voting.listeners.PlayerDeathListener;
import kenjih.voting.listeners.ShowHidePlayerListener;
import kenjih.voting.voters.Voters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Voting extends JavaPlugin {

    private ArrayList<Voters> voterList;
    private final ItemStack showPlayerItem = new ItemStack(Material.GLOWSTONE_DUST);
    private final ItemMeta showPlayerItemMeta = showPlayerItem.getItemMeta();
    private final ItemStack hidePlayerItem = new ItemStack(Material.REDSTONE);
    private final ItemMeta hidePlayerItemMeta = hidePlayerItem.getItemMeta();
    public final String VOTER_INV_NAME = "§aVoter Inventar";
    private final ItemStack voterItem = new ItemStack(Material.PAPER);
    private final ItemMeta voterMeta = voterItem.getItemMeta();
    private ArrayList<Player> players;

    @Override
    public void onEnable() {
        players = new ArrayList<>();
        voterList = new ArrayList<>();
        PluginManager pluginManager = Bukkit.getPluginManager();
        showPlayerItemMeta.setDisplayName("§aSpieler anzeigen");
        hidePlayerItemMeta.setDisplayName("§aSpieler verstecken");
        showPlayerItem.setItemMeta(showPlayerItemMeta);
        hidePlayerItem.setItemMeta(hidePlayerItemMeta);
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new ShowHidePlayerListener(this, showPlayerItem, hidePlayerItem), this);
        getCommand("vote").setExecutor(new VotingCommand(this));
        getCommand("votes").setExecutor(new GetVotesCommand(this));
        System.out.println("§cVoting Plugin startet");
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    public ArrayList<Voters> getVoterList(){
        return this.voterList;
    }

    public void setVoterList(ArrayList<Voters> voterList){
        this.voterList = voterList;
    }

    public Inventory createVoterInv(){
        int invSize = (int) Math.round(voterList.size() / 9) + 1;
        Inventory voterInv = Bukkit.createInventory(null, invSize * 9, VOTER_INV_NAME);
        try {
            for(int i = 0; i < voterList.size(); i++){
                if(voterList.get(i) == null)
                    continue;
                voterMeta.setDisplayName(voterList.get(i).getName());
                voterItem.setItemMeta(voterMeta);
                voterInv.setItem(i, voterItem);
            }
            return voterInv;
        }catch (Exception ex){
            Bukkit.getConsoleSender().sendMessage(ex.getMessage());
        }
        return null;
    }


    public ItemStack getShowPlayerItem() {
        return showPlayerItem;
    }

    public ItemStack getHidePlayerItem() {
        return hidePlayerItem;
    }

    public Inventory createVotesInv(ArrayList<Voters> voters){
        try {
            int invSize = (int) Math.round(voterList.size() / 9) + 1;
            List<String> lore = new ArrayList<>();
            Inventory voterInv = Bukkit.createInventory(null, invSize * 9, VOTER_INV_NAME);
            for(int i = 0; i < voterList.size(); i++){
                voterMeta.setDisplayName(voters.get(i).getName());
                lore.clear();
                lore.add("" +voters.get(i).getVotes());
                voterMeta.setLore(lore);
                voterItem.setItemMeta(voterMeta);
                voterInv.setItem(i, voterItem);
            }
            return voterInv;
        }catch (Exception ex){
            Bukkit.getConsoleSender().sendMessage(ex.getMessage());
        }
        return null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
