package com.consultoraestrategia.ss_portalalumno.util;

import com.raizlabs.android.dbflow.sql.language.property.IProperty;

public class UtilsDBFlow {
    public static IProperty[] f_allcolumnTable(IProperty... ALL_COLUMN_PROPERTIES) {
        for (int i = 0; i < ALL_COLUMN_PROPERTIES.length; i++) {
            ALL_COLUMN_PROPERTIES[i] = ALL_COLUMN_PROPERTIES[i].withTable();
        }
        return ALL_COLUMN_PROPERTIES;
    }


    public static IProperty[] f_allcolumnTable(IProperty[] ALL_COLUMN_PROPERTIES, IProperty... ALL_SECOND_COLUMN_PROPERTIES) {

        for (int i = 0; i < ALL_COLUMN_PROPERTIES.length; i++) {
            ALL_COLUMN_PROPERTIES[i] = ALL_COLUMN_PROPERTIES[i].withTable();
        }

        for (int i = 0; i < ALL_SECOND_COLUMN_PROPERTIES.length; i++) {
            ALL_SECOND_COLUMN_PROPERTIES[i] = ALL_SECOND_COLUMN_PROPERTIES[i];
        }

        int lengA = ALL_COLUMN_PROPERTIES.length;
        int lengB = ALL_SECOND_COLUMN_PROPERTIES.length;

        IProperty[] iProperties = new IProperty[lengA + lengB];
        System.arraycopy(ALL_COLUMN_PROPERTIES, 0, iProperties, 0, lengA);
        System.arraycopy(ALL_SECOND_COLUMN_PROPERTIES, 0, iProperties, lengA, lengB);


        return iProperties;
    }

}
