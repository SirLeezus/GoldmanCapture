package lee.code.capture.managers;

import lee.code.capture.Capture;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DelayManager {
  private final Capture capture;
  private final ConcurrentHashMap<UUID, ConcurrentHashMap<String, Long>> playersOnDelay = new ConcurrentHashMap<>();

  public DelayManager(Capture capture) {
    this.capture = capture;
  }

  public boolean isOnDelay(UUID uuid, String key) {
    if (!playersOnDelay.containsKey(uuid)) return false;
    else return playersOnDelay.get(uuid).containsKey(key);
  }

  public void setOnDelay(UUID uuid, String key, long delay) {
    if (playersOnDelay.containsKey(uuid)) {
      playersOnDelay.get(uuid).put(key, delay);
    } else {
      final ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
      map.put(key, System.currentTimeMillis() + delay);
      playersOnDelay.put(uuid, map);
    }
    scheduleDelay(uuid, key, delay);
  }

  private void scheduleDelay(UUID uuid, String key, long delay) {
    Bukkit.getServer().getAsyncScheduler().runDelayed(capture, scheduledTask ->
      removeDelay(uuid, key), delay, TimeUnit.MILLISECONDS);
  }

  private void removeDelay(UUID uuid, String key) {
    playersOnDelay.get(uuid).remove(key);
    if (playersOnDelay.get(uuid).isEmpty()) playersOnDelay.remove(uuid);
  }
}
