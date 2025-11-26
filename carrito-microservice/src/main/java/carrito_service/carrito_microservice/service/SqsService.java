package carrito_service.carrito_microservice.service;

import carrito_service.carrito_microservice.dto.PedidoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class SqsService {

    // Hardcoded SQS URL as requested
    private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/842886583649/cola-pedidos";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SqsClient sqsClient;

    public SqsService() {
        // Use the DefaultCredentialsProvider which follows the standard AWS provider chain:
        // env vars, system properties, profile, EC2/ECS metadata. This works both locally
        // (with env vars or ~/.aws/credentials) and on EC2 with an instance role.
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String enviarPedido(PedidoDto pedido) {
        try {
            String body = objectMapper.writeValueAsString(pedido);
            SendMessageRequest req = SendMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .messageBody(body)
                    .build();

            SendMessageResponse resp = sqsClient.sendMessage(req);
            return resp.messageId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing pedido", e);
        }
    }
}
