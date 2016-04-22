package ru.peak.ml.apdk.app.data.dataenum;


/**
 * Тип результата запроса к АПДК
 */
enum  ApdkResponseType {

    SUCCESS(code:"success"),
    ERROR(code:"error"),
    CONNECTION_ERROR(code:"connection_error");

    String code;

}
