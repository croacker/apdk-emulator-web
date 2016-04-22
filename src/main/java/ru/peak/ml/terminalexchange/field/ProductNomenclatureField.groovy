package ru.peak.ml.terminalexchange.field
import com.google.common.collect.Lists
import ru.peak.ml.terminalexchange.FieldNumbers
import ru.peak.ml.terminalexchange.cs.CsResource
import ru.peak.ml.terminalexchange.exception.ApplicationException
import ru.peak.ml.terminalexchange.tlv.*
/**
 * оле с данными о товарной номенклатуре
 */
class ProductNomenclatureField extends Field {

    // Список товаров
    List<Product> productsList = Lists.newArrayList();

    ProductNomenclatureField(FieldNumbers number, byte[] data){
        super(number, data);
        try {
            // Получаем список Tlv элементов
            TlvList tlvList = new TlvList();
            tlvList.parse(data);

            // Ищем элемент с товарными номенклатурами
            Element Element = tlvList.getElement(Tags.PRODUCT_NOMENCLATURE);

            // Получим список номенклатур
            ElementProductNomenclature productNomenclature = new ElementProductNomenclature(Element);
            productsList.addAll(productNomenclature.getListProductNomenclature());
        } catch (Exception ex) {
            throw new ApplicationException(String.format(CsResource.PRODUCT_NOMENCLATURE_PARSE_ERROR, ex.toString()));
        }
    }

    @Override
    String toString() {
        return productsList.toString();//TODO доделать ВСТРОКУ
    }
}
