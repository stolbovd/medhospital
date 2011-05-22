package ua.com.alus.medhosp.frontend.client.main.domen;

import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import static ua.com.alus.medhosp.frontend.shared.AbstractDTO.KEY;


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
        abstractCTO.setAttribute(KEY, abstractDTO.get(KEY));
        return abstractCTO;
    }

    public D convertPersonCTO(E abstractCTO, D abstractDTO) {
        for (String column : abstractDTO.getColumns()) {
            abstractDTO.put(column, abstractCTO.getAttributeAsString(column));
        }
        abstractDTO.put(KEY, abstractCTO.getAttributeAsString(KEY));
        return abstractDTO;
    }
}
