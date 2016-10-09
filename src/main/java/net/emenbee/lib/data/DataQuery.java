package net.emenbee.lib.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataQuery extends DataStatement {

    void consume(ResultSet results) throws SQLException;

}
