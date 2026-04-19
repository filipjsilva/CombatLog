package br.com.flp.combatLog;

import br.com.flp.combatLog.listeners.CombatListener;
import br.com.flp.combatLog.service.CombatService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatLogPlugin extends JavaPlugin {
	
	private final long COMBAT_DURATION = 15000L; // 15s
	
	private CombatService combatService;
	
	@Override
	public void onEnable() {
		combatService = new CombatService(COMBAT_DURATION);
		
		getServer().getPluginManager().registerEvents(new CombatListener(combatService), this);
		
		getServer().getScheduler().runTaskTimer(this, this::tick, 0, 20);
	}
	
	private void tick() {
		for(Player player : combatService.getInCombat()) {
			
			long remaining = combatService.getRemaining(player) / 1000;
			if(remaining <= 0) {
				combatService.remove(player);
			}
		}
	}
	
}