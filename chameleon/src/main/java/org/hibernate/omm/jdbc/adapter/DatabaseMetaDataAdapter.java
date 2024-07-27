package org.hibernate.omm.jdbc.adapter;

import org.hibernate.omm.exception.NotSupportedRuntimeException;
import org.hibernate.omm.jdbc.exception.NotSupportedSQLException;
import org.hibernate.omm.jdbc.exception.SimulatedSQLException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public interface DatabaseMetaDataAdapter extends DatabaseMetaData {

    @Override
    default boolean allProceduresAreCallable() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean allTablesAreSelectable() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getURL() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getUserName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isReadOnly() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean nullsAreSortedHigh() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean nullsAreSortedLow() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean nullsAreSortedAtStart() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean nullsAreSortedAtEnd() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getDatabaseProductName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getDatabaseProductVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getDriverName() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getDriverVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getDriverMajorVersion() {
        throw new NotSupportedRuntimeException();
    }

    @Override
    default int getDriverMinorVersion() {
        throw new NotSupportedRuntimeException();
    }

    @Override
    default boolean usesLocalFiles() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean usesLocalFilePerTable() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMixedCaseIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesUpperCaseIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesLowerCaseIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesMixedCaseIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMixedCaseQuotedIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesUpperCaseQuotedIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesLowerCaseQuotedIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean storesMixedCaseQuotedIdentifiers() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getIdentifierQuoteString() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSQLKeywords() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getNumericFunctions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getStringFunctions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSystemFunctions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getTimeDateFunctions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSearchStringEscape() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getExtraNameCharacters() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsAlterTableWithAddColumn() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsAlterTableWithDropColumn() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsColumnAliasing() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean nullPlusNonNullIsNull() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsConvert() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsConvert(int fromType, int toType) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsTableCorrelationNames() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsDifferentTableCorrelationNames() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsExpressionsInOrderBy() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOrderByUnrelated() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsGroupBy() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsGroupByUnrelated() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsGroupByBeyondSelect() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsLikeEscapeClause() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMultipleResultSets() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMultipleTransactions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsNonNullableColumns() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMinimumSQLGrammar() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCoreSQLGrammar() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsExtendedSQLGrammar() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsANSI92EntryLevelSQL() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsANSI92IntermediateSQL() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsANSI92FullSQL() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsIntegrityEnhancementFacility() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOuterJoins() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsFullOuterJoins() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsLimitedOuterJoins() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getSchemaTerm() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getProcedureTerm() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getCatalogTerm() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isCatalogAtStart() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default String getCatalogSeparator() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSchemasInDataManipulation() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSchemasInProcedureCalls() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSchemasInTableDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSchemasInIndexDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSchemasInPrivilegeDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCatalogsInDataManipulation() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCatalogsInProcedureCalls() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCatalogsInTableDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCatalogsInIndexDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCatalogsInPrivilegeDefinitions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsPositionedDelete() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsPositionedUpdate() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSelectForUpdate() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsStoredProcedures() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSubqueriesInComparisons() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSubqueriesInExists() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSubqueriesInIns() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSubqueriesInQuantifieds() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsCorrelatedSubqueries() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsUnion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsUnionAll() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOpenCursorsAcrossCommit() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOpenCursorsAcrossRollback() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOpenStatementsAcrossCommit() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsOpenStatementsAcrossRollback() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxBinaryLiteralLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxCharLiteralLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnsInGroupBy() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnsInIndex() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnsInOrderBy() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnsInSelect() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxColumnsInTable() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxConnections() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxCursorNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxIndexLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxSchemaNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxProcedureNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxCatalogNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxRowSize() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean doesMaxRowSizeIncludeBlobs() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxStatementLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxStatements() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxTableNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxTablesInSelect() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getMaxUserNameLength() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getDefaultTransactionIsolation() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsTransactions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsTransactionIsolationLevel(int level) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsDataDefinitionAndDataManipulationTransactions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsDataManipulationTransactionsOnly() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean dataDefinitionCausesTransactionCommit() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean dataDefinitionIgnoredInTransactions() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getProcedureColumns(
            String catalog,
            String schemaPattern,
            String procedureNamePattern,
            String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getSchemas() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getCatalogs() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTableTypes() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getVersionColumns(String catalog, String schema, String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getImportedKeys(String catalog, String schema, String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getExportedKeys(String catalog, String schema, String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getCrossReference(
            String parentCatalog,
            String parentSchema,
            String parentTable,
            String foreignCatalog,
            String foreignSchema,
            String foreignTable) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTypeInfo() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetType(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetConcurrency(int type, int concurrency) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownUpdatesAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownDeletesAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownInsertsAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersUpdatesAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersDeletesAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersInsertsAreVisible(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean updatesAreDetected(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean deletesAreDetected(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean insertsAreDetected(int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsBatchUpdates() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default Connection getConnection() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsSavepoints() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsNamedParameters() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsMultipleOpenResults() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsGetGeneratedKeys() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getAttributes(
            String catalog,
            String schemaPattern,
            String typeNamePattern,
            String attributeNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetHoldability(int holdability) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getResultSetHoldability() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getDatabaseMajorVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getDatabaseMinorVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getJDBCMajorVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getJDBCMinorVersion() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default int getSQLStateType() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean locatorsUpdateCopy() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsStatementPooling() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default RowIdLifetime getRowIdLifetime() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getSchemas(String catalog, String schemaPattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsStoredFunctionsUsingCallSyntax() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean autoCommitFailureClosesAllResultSets() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getClientInfoProperties() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getFunctionColumns(
            String catalog,
            String schemaPattern,
            String functionNamePattern,
            String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getPseudoColumns(
            String catalog,
            String schemaPattern,
            String tableNamePattern,
            String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean generatedKeyAlwaysReturned() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T unwrap(Class<T> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isWrapperFor(Class<?> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
