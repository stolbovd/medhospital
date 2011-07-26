package ua.com.alus.medhosp.frontend.client.modules.attributes.cto;

import ua.com.alus.medhosp.frontend.client.main.domen.ObjectCTO;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;

/**
 * AttributeCTO
 * <p/>
 * Created by Usatov Alexey
 */
public class AttributeCTO extends ObjectCTO<AttributeDTO, AttributeCTO> {
    @Override
    public AttributeCTO convertPersonDTO(AttributeDTO abstractDTO, AttributeCTO abstractCTO) {
        AttributeCTO attributeCTO = super.convertPersonDTO(abstractDTO, abstractCTO);
        attributeCTO.setAttribute(AttributeColumns.NAME.getColumnName(), abstractDTO.get(AttributeColumns.NAME.getColumnName()));
        return abstractCTO;
    }
}
