package club.deltapvp.deltacore.impl.api;

import club.deltapvp.deltacore.api.utilities.sql.DeltaSQLAPI;
import club.deltapvp.deltacore.api.utilities.sql.SQLConnector;
import club.deltapvp.deltacore.impl.sql.h2.H2Connector;
import club.deltapvp.deltacore.impl.sql.mysql.MySQLConnector;

public class DeltaSQLAPIImpl extends DeltaSQLAPI {

    public DeltaSQLAPIImpl() {
        setInstance(this);
    }

    @Override
    public SQLConnector createConnection(String s, String s1, String s2, String s3, String s4) {
        return new MySQLConnector(s, s1, s2, s3, s4);
    }

    @Override
    public SQLConnector createConnection(String s) {
        return createConnection(s, "database");
    }

    @Override
    public SQLConnector createConnection(String s, String s1, String s2) {
        return new H2Connector(s, s1, s2);
    }

    @Override
    public SQLConnector createConnection(String s, String s1) {
        return new H2Connector(s, s1);
    }
}
