package com.sixtydigits.cassandra.pelops.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.junit.Before;
import com.sixtydigits.cassandra.pelops.Cluster;
import com.sixtydigits.cassandra.pelops.ColumnFamilyManager;
import com.sixtydigits.cassandra.pelops.KeyspaceManager;
import com.sixtydigits.cassandra.pelops.Mutator;
import com.sixtydigits.cassandra.pelops.OperandPolicy;
import com.sixtydigits.cassandra.pelops.Selector;
import com.sixtydigits.cassandra.pelops.pool.DebuggingPool;
import com.sixtydigits.cassandra.pelops.pool.IThriftPool;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Abstract integration test class which runs its own embedded cassandra server.
 */
public abstract class AbstractIntegrationTest {

	protected final Logger logger = LoggerFactory.getLogger(this
			.getClass());

	public static final String RPC_LISTEN_ADDRESS = getenv("CASSANDRA_HOST", "localhost");

	public static final int RPC_PORT = Integer.parseInt(getenv("CASSANDRA_THRIFT_PORT", "19160"));

	public static final String KEYSPACE = "PelopsTesting";


	public static Cluster cluster = new Cluster(RPC_LISTEN_ADDRESS, RPC_PORT);

	private static KeyspaceManager keyspaceManager;

	private static ColumnFamilyManager columnFamilyManager;

	private static List<CfDef> colFamilyDefs;

	private IThriftPool pool = new DebuggingPool(cluster, KEYSPACE, new OperandPolicy());

	public KeyspaceManager getKeyspaceManager() {
		return keyspaceManager;
	}

	public ColumnFamilyManager getColumnFamilyManager() {
		return columnFamilyManager;
	}

	protected Cluster getCluster() {
		return cluster;
	}

	protected IThriftPool getPool() {
		return pool;
	}

	static String getenv(String name, String defaultValue) {
		String actual = System.getenv(name);

		if (actual != null) {
			return actual;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Starts embedded cassandra server.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	public static void setup(List<CfDef> columnDefinitions) throws Exception {
		colFamilyDefs = columnDefinitions;

		keyspaceManager = new KeyspaceManager(cluster);
		columnFamilyManager = new ColumnFamilyManager(cluster, KEYSPACE);

		List<KsDef> keyspaces = keyspaceManager.getKeyspaceNames();
		for (KsDef ksDef : keyspaces)
			if (ksDef.name.equals(KEYSPACE)) {
				keyspaceManager.dropKeyspace(KEYSPACE);
			}

		KsDef keyspaceDefinition = new KsDef(KEYSPACE, KeyspaceManager.KSDEF_STRATEGY_SIMPLE, new ArrayList<CfDef>());
        Map<String, String> strategyOptions = new HashMap<String, String>();
        strategyOptions.put("replication_factor", "1");
        keyspaceDefinition.setStrategy_options(strategyOptions);

		for (CfDef colFamilyDef : colFamilyDefs) {
			keyspaceDefinition.addToCf_defs(colFamilyDef);
		}

		keyspaceManager.addKeyspace(keyspaceDefinition);
	}

	/**
	 * Database prepare before test.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	@Before
	public void setupTest() throws Exception {
		truncate();
		prepareData();
	}

	/**
	 * Data prepare. Its purpose is to be overloaded from test class.
	 * 
	 * @throws Exception
	 *             if an error occurs
	 */
	public void prepareData() throws Exception {
	}

	/**
	 * Truncate all column families.
	 */
	public void truncate() {
		for (CfDef colFamilyDef : colFamilyDefs) {
			try {
				columnFamilyManager
						.truncateColumnFamily(colFamilyDef.getName());
			} catch (Exception e) {
				throw new IllegalStateException(
						"Failed to truncate column family "
								+ colFamilyDef.getName(), e);
			}
		}
	}

	/**
	 * @return new mutator instance
	 */
	public Mutator createMutator() {
		return pool.createMutator();
	}

	/**
	 * @return new selector instance
	 */
	public Selector createSelector() {
		return pool.createSelector();
	}
}
