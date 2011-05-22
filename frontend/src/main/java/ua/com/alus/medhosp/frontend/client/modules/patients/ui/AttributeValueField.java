package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;

/**
 * Created by Usatov Alexey.
 * Date: 22.05.11
 * Time: 19:41
 */
public class AttributeValueField extends ListGridField {

    private AttributeCellFormatter attributeFormatter;

    public AttributeValueField(String name, String title) {
        super(name, title);
        setCellFormatter(getAttributeFormatter());
    }

    private AttributeCellFormatter getAttributeFormatter(){
        if(attributeFormatter == null){
            attributeFormatter = new AttributeCellFormatter();
        }
        return attributeFormatter;
    }

    private class AttributeCellFormatter implements CellFormatter {

        public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
            JavaScriptObject javaScriptObject = ((PatientCTO) record).getPatAtttribueValue(getName());
            if (javaScriptObject == null) {
                return "";
            }
            return getJLabel(javaScriptObject);
        }
    }

    private native String getJLabel(JavaScriptObject object)/*-{
        return object['attributeValue'];
    }-*/;
}
