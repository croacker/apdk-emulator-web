package ru.peak.ml.terminalexchange.tlv;

import com.google.common.collect.Maps;
import ru.peak.ml.terminalexchange.cs.CsResource;
import ru.peak.ml.terminalexchange.exception.ApplicationException;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Список Tlv элементов
 */
public class TlvList {

    // Список элементов
    Map<Tags, Element> elements = Maps.newHashMap();

    // Парсит массив байт
    public void parse(byte[] data) {
        short firstIndex = 0;
        elements.clear();

        do {
            Element element = new Element(firstIndex, data);
            firstIndex += element.getFullLength();
            elements.put(element.getTag(), element);
        } while (firstIndex < data.length);
    }

    // Возвращает элемент в виде массива быйт
    public byte[] ToArray() {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        for (Element element : elements.values()) {
            byte[] elementData = element.toArray();
               dataStream.write(elementData, 0, elementData.length);
        }
        return dataStream.toByteArray();
    }

    // Добавляет элемент
    public void AddElement(Element element) {
        elements.put(element.getTag(), element);
    }

    public Element getElement(Tags tag) {
        Element element = elements.get(tag);

        if (element == null) {
            throw new ApplicationException(String.format(CsResource.TLV_ELEMENT_NOT_FOUND, tag));
        }
        return element;
    }
}

