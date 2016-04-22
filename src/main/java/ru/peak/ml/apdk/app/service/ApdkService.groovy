package ru.peak.ml.apdk.app.service;

import groovy.util.logging.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.peak.ml.apdk.app.service.apdk.ApdkMessage;
import ru.peak.ml.terminalexchange.message.RequestMessage;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 */
@Slf4j
@Service
public class ApdkService {

    @Autowired
    private AppPersistService appPersistService;

    public String sendMessage(ApdkMessage apdkMessage) throws IOException {
        String result;
        try {
            Socket socket = getSocket(apdkMessage);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream inputStream = new DataInputStream(sin);
            DataOutputStream outputStream = new DataOutputStream(sout);

            byte[] data = apdkMessage.getData();

            outputStream.write(data);
            outputStream.flush();

            byte[] responseMessageData = IOUtils.toByteArray(inputStream);
            log.info("The server sent me this : " + ArrayUtils.toString(responseMessageData));

            RequestMessage responseMessage = new RequestMessage(responseMessageData);

            result = apdkMessage.getFormatter().toMessageString(responseMessage);
            if(result.length() > 255){
                result = result.substring(0, 255);
            }

            log.info(result);
            saveResult(apdkMessage.getOperationCaption(), result, DatatypeConverter.printHexBinary(responseMessageData));
        } catch (Exception x) {
            log.error(x.getMessage(), x);
            saveError(apdkMessage.getOperationCaption(), x);
            throw x;
        }
        return result;
    }

    private Socket getSocket(ApdkMessage message) throws IOException {
        InetAddress ipAddress = InetAddress.getByName(message.getServerAddress());
        return new Socket(ipAddress, message.getServerPort());
    }

    protected void saveResult(String operationType, String title, String originalData) {
        appPersistService.saveRequestResult(operationType, title, originalData);
    }

    protected void saveError(String operationType, Exception e) {
        appPersistService.saveError(operationType, e);
    }

}
