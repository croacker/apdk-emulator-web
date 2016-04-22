package ru.peak.ml.apdk.app.service.formatter;

import ru.peak.ml.terminalexchange.message.Message;

/**
 *
 */
interface MessageFormatter {

    String toMessageString(Message message);

}
