package me.mchiappinam.pdghpurger;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable(){
		File file = new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			}
			catch(Exception e) {}
	}
		autoPurge();
}

	private void autoPurge() {
		if(getConfig().getString("Ativado")=="true") {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(10)); //10 dias para remover os players
        long until = calendar.getTimeInMillis();
	    List<String> cleared = autoPurgeDatabase(until);
		getServer().getConsoleSender().sendMessage("§3[PDGHPurger] §c"+cleared.size()+" §2contas removidas.");
			purgeDat(cleared); //em null posso colocar cleared
		}else{
			getServer().getConsoleSender().sendMessage("§cPDGHPurger desativado! :(");
		}
	}

	public void purgeDat(List<String> cleared) {
		int i = 0;
		for (String name : cleared) {
			org.bukkit.OfflinePlayer player = Bukkit.getOfflinePlayer(name);
			if (player == null) continue;
			String playerName = player.getName();
			File playerFile = new File (this.getServer().getWorldContainer() + File.separator + getConfig().getString("Mundo_Padrao") + File.separator + "players" + File.separator + playerName + ".dat");
			if (playerFile.exists()) {
				playerFile.delete();
				i++;
			}
		}
		getServer().getConsoleSender().sendMessage("§3[PDGHPurger] §c"+i+" §2jogadores inativos removidos com sucesso. (.dat)");
	}

    public List<String> autoPurgeDatabase(long until) {
        List<String> cleared = autoPurgeDatabase(until);
        return cleared;
    }
    
}
