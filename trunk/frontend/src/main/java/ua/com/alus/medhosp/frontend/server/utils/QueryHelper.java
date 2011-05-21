package ua.com.alus.medhosp.frontend.server.utils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 23:35
 */
public class QueryHelper {

    public synchronized static String listToString(List a) {
        if (a == null)
            return "null";
        int iMax = a.size() - 1;
        if (iMax == -1)
            return "()";

        StringBuilder b = new StringBuilder();
        b.append('(');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a.get(i).toString()));
            if (i == iMax)
                return b.append(')').toString();
            b.append(", ");
        }
    }
}
