package sconti.ukp;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.AbstractQueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class CustomQueryRunner extends AbstractQueryRunner {
	public CustomQueryRunner() {
		super();
	}

	public CustomQueryRunner(DataSource ds) {
		super(ds);
	}

	public CustomQueryRunner(boolean pmdKnownBroken) {
		super(pmdKnownBroken);
	}

	public CustomQueryRunner(DataSource ds, boolean pmdKnownBroken) {
		super(ds, pmdKnownBroken);
	}

	public <T> T RsMapping(ResultSetHandler<T> rsh, ResultSet rs) {

		T result = null;

		try {
			result = rsh.handle(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
