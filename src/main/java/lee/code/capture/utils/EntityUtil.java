package lee.code.capture.utils;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class EntityUtil {

  public static String serializeEntity(Entity entity) {
    final net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
    final CompoundTag nbtTagCompound = new CompoundTag();
    nmsEntity.save(nbtTagCompound);
    return nbtTagCompound.toString();
  }

  public static void spawnEntity(String key, EntityType type, Location location) {
    final World world = location.getWorld();
    if (world == null) return;
    final Entity entity = world.spawnEntity(location, type);
    final UUID newUUID = entity.getUniqueId();
    final net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
    try {
      nmsEntity.load(TagParser.parseTag(key));
      nmsEntity.setUUID(newUUID);
    } catch (CommandSyntaxException ex) {
      ex.printStackTrace();
    }
    entity.teleport(location);
  }
}
