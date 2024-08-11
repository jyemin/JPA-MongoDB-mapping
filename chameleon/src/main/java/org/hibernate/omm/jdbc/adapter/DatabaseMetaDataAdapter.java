/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.omm.jdbc.adapter;

import com.mongodb.lang.Nullable;
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
    default boolean supportsTransactionIsolationLevel(final int level) throws SimulatedSQLException {
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
    default ResultSet getProcedures(@Nullable final String catalog, @Nullable final String schemaPattern,
            @Nullable final String procedureNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getProcedureColumns(
            @Nullable final String catalog,
            @Nullable final String schemaPattern,
            @Nullable final String procedureNamePattern,
            @Nullable final String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTables(@Nullable final String catalog, @Nullable final String schemaPattern, @Nullable final String tableNamePattern,
            @Nullable final String[] types)
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
    default ResultSet getColumns(@Nullable final String catalog, @Nullable final String schemaPattern, @Nullable final String tableNamePattern,
            @Nullable final String columnNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getColumnPrivileges(@Nullable final String catalog, @Nullable final String schema, final String table,
            @Nullable final String columnNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTablePrivileges(@Nullable final String catalog, @Nullable final String schemaPattern,
            @Nullable final String tableNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getBestRowIdentifier(@Nullable final String catalog, @Nullable final String schema, final String table, final int scope,
            final boolean nullable)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getVersionColumns(@Nullable final String catalog, @Nullable final String schema, final String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getPrimaryKeys(@Nullable final String catalog, @Nullable final String schema, final String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getImportedKeys(@Nullable final String catalog, @Nullable final String schema, final String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getExportedKeys(@Nullable final String catalog, @Nullable final String schema, final String table) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getCrossReference(
            @Nullable final String parentCatalog,
            @Nullable final String parentSchema,
            final String parentTable,
            @Nullable final String foreignCatalog,
            @Nullable final String foreignSchema,
            final String foreignTable) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getTypeInfo() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getIndexInfo(@Nullable final String catalog, @Nullable final String schema, final String table, final boolean unique,
            final boolean approximate)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetType(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetConcurrency(final int type, final int concurrency) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownUpdatesAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownDeletesAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean ownInsertsAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersUpdatesAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersDeletesAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean othersInsertsAreVisible(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean updatesAreDetected(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean deletesAreDetected(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean insertsAreDetected(final int type) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsBatchUpdates() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getUDTs(@Nullable final String catalog, @Nullable final String schemaPattern, @Nullable final String typeNamePattern,
            @Nullable final int[] types)
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
    default ResultSet getSuperTypes(@Nullable final String catalog, @Nullable final String schemaPattern,
            @Nullable final String typeNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getSuperTables(@Nullable final String catalog, @Nullable final String schemaPattern,
            @Nullable final String tableNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getAttributes(
            @Nullable final String catalog,
            @Nullable final String schemaPattern,
            @Nullable final String typeNamePattern,
            @Nullable final String attributeNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean supportsResultSetHoldability(final int holdability) throws SimulatedSQLException {
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
    default ResultSet getSchemas(@Nullable final String catalog, @Nullable final String schemaPattern) throws SimulatedSQLException {
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
    default ResultSet getFunctions(@Nullable final String catalog, @Nullable final String schemaPattern,
            @Nullable final String functionNamePattern)
            throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getFunctionColumns(
            @Nullable final String catalog,
            @Nullable final String schemaPattern,
            @Nullable final String functionNamePattern,
            @Nullable final String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default ResultSet getPseudoColumns(
            @Nullable final String catalog,
            @Nullable final String schemaPattern,
            @Nullable final String tableNamePattern,
            @Nullable final String columnNamePattern) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean generatedKeyAlwaysReturned() throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default <T> T unwrap(final Class<T> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }

    @Override
    default boolean isWrapperFor(final Class<?> iface) throws SimulatedSQLException {
        throw new NotSupportedSQLException();
    }
}
