package br.com.flp.combatLog.service;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class CombatService {
	
	private final Map<UUID, Long> combatMap = new HashMap<>();
	private final long duration;
	
	@Getter
	private final Set<Player> inCombat = new HashSet<>();
	
	public CombatService(long duration) {
		this.duration = duration;
	}
	
	public void tag(Player player) {
		combatMap.put(player.getUniqueId(), System.currentTimeMillis());
		inCombat.add(player);
	}
	
	public boolean isInCombat(Player player) {
		Long last = combatMap.get(player.getUniqueId());
		if(last == null) return false;
		
		return System.currentTimeMillis() - last < duration;
	}
	
	public long getRemaining(Player player) {
		Long last = combatMap.get(player.getUniqueId());
		if(last == null) return 0;
		
		long remaining = (last + duration) - System.currentTimeMillis();
		return Math.max(remaining, 0);
	}
	
	public void remove(Player player) {
		combatMap.remove(player.getUniqueId());
		inCombat.remove(player);
	}
	
}