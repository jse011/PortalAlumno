package com.consultoraestrategia.ss_portalalumno.util;

public class DriveUrlParser
{
    public static String getDocumentId(String docIDfull) {
        int docIDstart = docIDfull.indexOf("/d/");
        int DocIDend = docIDfull.indexOf('/', docIDstart + 3);
        String docID = null;
        if (docIDstart == -1) {
            //Invalid URL readonly
            return null;
        }
        if (DocIDend == -1) {
            docID = docIDfull.substring(docIDstart + 3);
        }
        else {
            docID = docIDfull.substring(docIDstart + 3, DocIDend);
        }

        return docID;
    }
}
