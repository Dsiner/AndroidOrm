package com.d.lib.orm.sqlite.internal;

/**
 * SqlUtils
 * Created by D on 2020/9/11.
 */
public class SqlUtils {
    private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static StringBuilder appendColumn(StringBuilder builder, String column) {
        builder.append('"').append(column).append('"');
        return builder;
    }

    public static StringBuilder appendColumn(StringBuilder builder, String tableAlias, String column) {
        builder.append(tableAlias).append(".\"").append(column).append('"');
        return builder;
    }

    public static StringBuilder appendColumns(StringBuilder builder, String tableAlias, String[] columns) {
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            appendColumn(builder, tableAlias, columns[i]);
            if (i < length - 1) {
                builder.append(',');
            }
        }
        return builder;
    }

    public static StringBuilder appendColumns(StringBuilder builder, String[] columns) {
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            builder.append('"').append(columns[i]).append('"');
            if (i < length - 1) {
                builder.append(',');
            }
        }
        return builder;
    }

    public static StringBuilder appendPlaceholders(StringBuilder builder, int count) {
        for (int i = 0; i < count; i++) {
            if (i < count - 1) {
                builder.append("?,");
            } else {
                builder.append('?');
            }
        }
        return builder;
    }

    public static StringBuilder appendColumnsEqualPlaceholders(StringBuilder builder, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            appendColumn(builder, columns[i]).append("=?");
            if (i < columns.length - 1) {
                builder.append(',');
            }
        }
        return builder;
    }

    public static StringBuilder appendColumnsEqValue(StringBuilder builder, String tableAlias, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            appendColumn(builder, tableAlias, columns[i]).append("=?");
            if (i < columns.length - 1) {
                builder.append(',');
            }
        }
        return builder;
    }

    public static String createSqlInsert(String tablename, String[] columns) {
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append('"').append(tablename).append('"').append(" (");
        appendColumns(builder, columns);
        builder.append(") VALUES (");
        appendPlaceholders(builder, columns.length);
        builder.append(')');
        return builder.toString();
    }

    /**
     * Creates an select for given columns with a trailing space
     */
    public static String createSqlSelect(String tablename, String tableAlias, String[] columns, boolean distinct) {
        if (tableAlias == null || tableAlias.length() < 0) {
            throw new RuntimeException("Table alias required");
        }

        StringBuilder builder = new StringBuilder(distinct ? "SELECT DISTINCT " : "SELECT ");
        SqlUtils.appendColumns(builder, tableAlias, columns).append(" FROM ");
        builder.append('"').append(tablename).append('"').append(' ').append(tableAlias).append(' ');
        return builder.toString();
    }

    /**
     * Creates SELECT COUNT(*) with a trailing space.
     */
    public static String createSqlSelectCountStar(String tablename, String tableAliasOrNull) {
        StringBuilder builder = new StringBuilder("SELECT COUNT(*) FROM ");
        builder.append('"').append(tablename).append('"').append(' ');
        if (tableAliasOrNull != null) {
            builder.append(tableAliasOrNull).append(' ');
        }
        return builder.toString();
    }

    /**
     * Remember: SQLite does not support joins nor table alias for DELETE.
     */
    public static String createSqlDelete(String tablename, String[] columns) {
        String quotedTablename = '"' + tablename + '"';
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(quotedTablename);
        if (columns != null && columns.length > 0) {
            builder.append(" WHERE ");
            appendColumnsEqValue(builder, quotedTablename, columns);
        }
        return builder.toString();
    }

    public static String createSqlUpdate(String tablename, String[] updateColumns, String[] whereColumns) {
        String quotedTablename = '"' + tablename + '"';
        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append(quotedTablename).append(" SET ");
        appendColumnsEqualPlaceholders(builder, updateColumns);
        builder.append(" WHERE ");
        appendColumnsEqValue(builder, quotedTablename, whereColumns);
        return builder.toString();
    }

    public static String createSqlCount(String tablename) {
        return "SELECT COUNT(*) FROM \"" + tablename + '"';
    }

    public static String[] mergerString(final String[] array_1, final String[] array_2) {
        String[] array_3 = new String[array_1.length + array_2.length];
        System.arraycopy(array_1, 0, array_3, 0, array_1.length);
        System.arraycopy(array_2, 0, array_3, array_1.length, array_2.length);
        return array_3;
    }

    public static Object[] mergerObject(final Object[] array_1, final Object[] array_2) {
        Object[] array_3 = new Object[array_1.length + array_2.length];
        System.arraycopy(array_1, 0, array_3, 0, array_1.length);
        System.arraycopy(array_2, 0, array_3, array_1.length, array_2.length);
        return array_3;
    }

    public static String escapeBlobArgument(byte[] bytes) {
        return "X'" + toHex(bytes) + '\'';
    }

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int byteValue = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[byteValue >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[byteValue & 0x0F];
        }
        return new String(hexChars);
    }
}
