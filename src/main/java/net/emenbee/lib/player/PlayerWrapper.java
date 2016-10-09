package net.emenbee.lib.player;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

public abstract class PlayerWrapper {

    private final WeakReference<Player> reference;

    protected PlayerWrapper(UUID uuid) {
        this(Players.get(uuid));
    }

    protected PlayerWrapper(Player player) {
        this.reference = new WeakReference<>(player);
    }

    public Player get() {
        return this.reference.get();
    }

    @Override
    public boolean equals(Object object) {
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        PlayerWrapper other = (PlayerWrapper) object;

        return this.get().getUniqueId().equals(other.get().getUniqueId());
    }

}
