package milsabores.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class ProcessSqsHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        if (event.getRecords() != null) {
            event.getRecords().forEach(r -> {
                context.getLogger().log("Processing SQS message: " + r.getBody());
                // TODO: implementar lógica de negocio: procesar pedido, actualizar inventario, etc.
            });
        }
        return null;
    }
}
