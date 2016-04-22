package ru.peak.ml.terminalexchange.tlv;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import ru.peak.ml.terminalexchange.cs.CsConvert;
import ru.peak.ml.terminalexchange.cs.CsResource;
import ru.peak.ml.terminalexchange.exception.ApplicationException;

import java.util.List;
import java.util.Map;

/**
 * Элемент данных в формате TLV, данные по товарным номенклатурам
 */
public class ElementProductNomenclature extends Element {

    public ElementProductNomenclature(byte[] data) {
        super(Tags.PRODUCT_NOMENCLATURE, data);
    }

    public ElementProductNomenclature(List<Product> productsList)
    {
        super(Tags.PRODUCT_NOMENCLATURE);
        // Список элементов с числом позиций, EANs и стоимостям
        TlvList ListProductNomenclature = new TlvList();

        // Число позиций
        ElementCount ECount = new ElementCount(CsConvert.ToUInt16(productsList.size()));
        ListProductNomenclature.AddElement(ECount);

        // EAN'ны
        List<String> listEans = Lists.transform(productsList, new Function<Product, String>() {
            @Override
            public String apply(Product product) {
                return product.getEan();
            }
        });
        ElementEans EEans = new ElementEans(listEans);
        ListProductNomenclature.AddElement(EEans);

        // Стоимости
        List<Integer> listCosts = Lists.transform(productsList, product -> product.getCost());
        ElementCosts ECosts = new ElementCosts(listCosts);
        ListProductNomenclature.AddElement(ECosts);

        // Количество
        List<Long> listProductAmounts = Lists.transform(productsList, product -> product.getAmount());
        ElementLong eAmounts = new ElementLong(listProductAmounts, Tags.PRODUCT_AMOUNT);
        ListProductNomenclature.AddElement(eAmounts);

        // Количество
        List<Integer> listProductPrices = Lists.transform(productsList, product -> product.getPrice());
        ElementUints ePrices = new ElementUints(listProductPrices, Tags.PRODUCT_PRICE);
        ListProductNomenclature.AddElement(ePrices);

        setData(ListProductNomenclature.ToArray());
    }

    public ElementProductNomenclature(Element element) {
        super(Tags.PRODUCT_NOMENCLATURE, element.getData());
    }

    public List<Product> getListProductNomenclature() {
        // Парсим данные о номенклатурах
        TlvList TlvList = new TlvList();
        TlvList.parse(getData());

        // Получаем количество позиций
        short positionCount = getPositionCount(TlvList.getElement(Tags.COUNT));
        Map<Integer, String> eansList = getEans(TlvList.getElement(Tags.EANS));
        Map<Integer, Integer> costsList = getCosts(TlvList.getElement(Tags.COSTS));
        Map<Integer, Long> productAmounts = getProductAmounts(TlvList.getElement(Tags.PRODUCT_AMOUNT));
        Map<Integer, Integer> productPrices = getProductPrices(TlvList.getElement(Tags.PRODUCT_PRICE));

        // Проверяем количество номеров товаров
        if (eansList.size() != positionCount) {
            throw new ApplicationException(String.format(CsResource.NUMBER_OF_EANS_NOT_EQUAL_TO_POSITIONS, eansList.size(), positionCount));
        }

        // Проверяем количество стоимостей
        if (costsList.size() != positionCount) {
            throw new ApplicationException(String.format(CsResource.NUMBER_OF_COSTS_NOT_EQUAL_TO_POSITIONS, costsList.size(), positionCount));
        }

        // Проверяем количество номеров товаров
        if (productAmounts.size() != positionCount) {
            throw new ApplicationException(String.format(CsResource.NUMBER_OF_PRODUCT_COUNTS_NOT_EQUAL_TO_POSITIONS, productAmounts.size(), positionCount));
        }

        // Проверяем количество номеров товаров
        if (productPrices.size() != positionCount) {
            throw new ApplicationException(String.format(CsResource.NUMBER_OF_PRODUCT_PRICES_NOT_EQUAL_TO_POSITIONS, productPrices.size(), positionCount));
        }

        return concatData(eansList, costsList, productAmounts, productPrices);
    }

    private List<Product> concatData(Map<Integer, String> eansList, Map<Integer, Integer> costsList, Map<Integer, Long> productAmounts, Map<Integer, Integer> productPrices) {
        List<Product> products = Lists.newArrayList();
        for (Integer key: eansList.keySet()){
            String ean = eansList.get(key);
            Integer cost = costsList.get(key);
            Long amount = productAmounts.get(key);
            Integer price = productPrices.get(key);
            if(ean != null && cost != null && amount != null && price != null){
                Product product = new Product();
                product.setEan(ean);
                product.setCost(cost);
                product.setAmount(amount);
                product.setPrice(price);
                products.add(product);
            }
        }
        return products;
    }

    //возвращает список количеств товаров
    private Map<Integer, Long> getProductAmounts(Element El) {
        ElementLong productCounts = new ElementLong(El.getData(), Tags.PRODUCT_AMOUNT);
        return productCounts.getList();
    }

    //возвращает список цен
    private Map<Integer, Integer> getProductPrices(Element El) {
        ElementUints productPrices = new ElementUints(El.getData(), Tags.PRODUCT_PRICE);
        return productPrices.getList();
    }

    // Возвращает стоимости
    private Map<Integer, Integer> getCosts(Element element) {
        ElementCosts ElementCosts = new ElementCosts(element.getData());
        return ElementCosts.getList();
    }

    // Возвращает EAN'ны
    private Map<Integer, String> getEans(Element element) {
        ElementEans ElementEans = new ElementEans(element.getData());
        return ElementEans.getList();
    }

    // Возвращает количество позиций
    private short getPositionCount(Element element) {
        ElementCount ElementCount = new ElementCount(element.getData());
        return ElementCount.getCount();
    }
}


