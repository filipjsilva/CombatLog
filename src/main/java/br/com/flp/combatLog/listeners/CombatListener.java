package br.com.flp.combatLog.listeners;

import br.com.flp.combatLog.service.CombatService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatListener implements Listener {
	
	private final CombatService combatService;
	
	public CombatListener(CombatService combatService) {
		this.combatService = combatService;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(combatService.isInCombat(player)) {
			player.setHealth(0.0);
			combatService.remove(player);
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player victim)) return;
		if(!(event.getDamager() instanceof Player attacker)) return;
		
		combatService.tag(victim);
		combatService.tag(attacker);
	}
	
}