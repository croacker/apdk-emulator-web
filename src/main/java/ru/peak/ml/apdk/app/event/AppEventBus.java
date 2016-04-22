package ru.peak.ml.apdk.app.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import ru.peak.ml.apdk.app.MlApdkUI;

/**
 *
 */
public class AppEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MlApdkUI.getAppEventBus().eventBus.post(event);
    }

    public static void register(final Object object) {
        MlApdkUI.getAppEventBus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        MlApdkUI.getAppEventBus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
                                      final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}