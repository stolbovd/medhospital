package ua.com.alus.medhosp.frontend.client.main.domen;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;


/**
 * Created by Usatov Alexey
 * Date: 14.05.11
 * Time: 1:07
 */
public abstract class ObjectCTO<D extends AbstractDTO, E extends ObjectCTO> extends ListGridRecord {

    public E convertPersonDTO(D abstractDTO, E abstractCTO) {
        for (String column : abstractDTO.getColumns()) {
            abstractCTO.setAttribute(column, abstractDTO.get(column));
        }
        abstractCTO.setAttribute(BaseColumns.ENTITY_ID.getColumnName(), abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()));
        return abstractCTO;
    }

    public D convertPersonCTO(E abstractCTO, D abstractDTO) {
        for (String column : abstractDTO.getColumns()) {
            abstractDTO.put(column, abstractCTO.getAttributeAsString(column));
        }
        abstractDTO.put(BaseColumns.ENTITY_ID.getColumnName(), abstractCTO.getAttributeAsString(BaseColumns.ENTITY_ID.getColumnName()));
        return abstractDTO;
    }
}
