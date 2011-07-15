package ua.com.alus.medhosp.frontend.client.main.domen;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.prototype.data.Constants;


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
        abstractCTO.setAttribute(Constants.ENTITY_ID, abstractDTO.get(Constants.ENTITY_ID));
        return abstractCTO;
    }

    public D convertPersonCTO(E abstractCTO, D abstractDTO) {
        for (String column : abstractDTO.getColumns()) {
            abstractDTO.put(column, abstractCTO.getAttributeAsString(column));
        }
        abstractDTO.put(Constants.ENTITY_ID, abstractCTO.getAttributeAsString(Constants.ENTITY_ID));
        return abstractDTO;
    }
}
