package net.emenbee.lib.world;

import net.emenbee.lib.except.UtilInstantiationException;
import net.emenbee.lib.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public final class LocationUtils {

    private static final String DELIMITOR = ";";

    private LocationUtils() {
        throw new UtilInstantiationException();
    }

    public static World getWorld(String name) {
        return Bukkit.getWorld(name);
    }

    public static String chunkToString(Chunk chunk) {
        return String.format("%s%s%d%s%d", chunk.getWorld().getName(), DELIMITOR, chunk.getX(), DELIMITOR, chunk.getZ());
    }

    public static Chunk stringToChunk(String string) {
        String[] parts = string.split(DELIMITOR);
        return LocationUtils.getWorld(parts[0]).getChunkAt(
                NumberUtils.parse(Integer.class, parts[1]), NumberUtils.parse(Integer.class, parts[2])
        );
    }

    public static String locationToString(Location location) {
        return String.format("%s%s%f%s%f%s%f", location.getWorld().getName(), DELIMITOR, location.getX(), DELIMITOR, location.getY(), DELIMITOR, location.getZ());
    }

    public static Location stringToLocation(String string) {
        String[] parts = string.split(DELIMITOR);
        return new Location(
                LocationUtils.getWorld(parts[0]),
                NumberUtils.parse(Double.class, parts[1]),
                NumberUtils.parse(Double.class, parts[2]),
                NumberUtils.parse(Double.class, parts[3])
        );
    }

}
