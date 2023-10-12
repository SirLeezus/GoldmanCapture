package lee.code.capture;

import lee.code.capture.listeners.CaptureListener;
import lee.code.capture.managers.DelayManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Capture extends JavaPlugin {
  @Getter private DelayManager delayManager;
  @Getter private Data data;

  @Override
  public void onEnable() {
    this.data = new Data();
    this.delayManager = new DelayManager(this);
    registerListeners();
  }

  @Override
  public void onDisable() {
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new CaptureListener(this), this);
  }
}
