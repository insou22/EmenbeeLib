package net.emenbee.lib.data;

import lombok.Getter;
import net.emenbee.lib.message.Strings;

public final class DataInfo {

    @Getter private final String url;
    @Getter private final String username;
    @Getter private final String password;

    private DataInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DataInfo valueOf(String hostname, int port, String database, String username, String password) {
        return DataInfo.valueOf(Strings.format("{0}:{1}/{2}", hostname, port, database), username, password);
    }

    public static DataInfo valueOf(String hostname, int port, String database) {
        return DataInfo.valueOf(Strings.format("{0}:{1}/{2}", hostname, port, database));
    }

    public static DataInfo valueOf(String url) {
        return DataInfo.valueOf(url, null, null);
    }

    public static DataInfo valueOf(String url, String username, String password) {
        return new DataInfo(url, username, password);
    }

}
