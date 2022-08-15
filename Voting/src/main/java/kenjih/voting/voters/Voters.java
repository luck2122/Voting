package kenjih.voting.voters;

import kenjih.voting.Voting;
import org.apache.logging.log4j.util.PropertySource;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class Voters {

    private int votes = 0;
    private String name;
    private final Voting plugin;

    public Voters(Voting plugin){
        this.plugin = plugin;
    }

    public int getVotes() {
        return votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Comparator<Voters> voterNo = (voter1, voter2) -> {
        int voterNo1 = voter1.getVotes();
        int voterNo2 = voter2.getVotes();

        return voterNo1 - voterNo2;
    };

    public void voteForPlayer(Player player){
        ArrayList<Player> playerList = plugin.getPlayers();
        if(playerList.contains(player)) {
            this.votes--;
            playerList.remove(player);
            plugin.setPlayers(playerList);
        }else{
            playerList.add(player);
            plugin.setPlayers(playerList);
            this.votes++;
        }
    }
}