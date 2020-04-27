package software.amazon.ssm.maintenancewindow;

<<<<<<< HEAD
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
=======
import org.junit.jupiter.api.Assertions;
>>>>>>> Updated implementation of all handler
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
<<<<<<< HEAD

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
=======
import software.amazon.ssm.maintenancewindow.translator.ExceptionTranslator;
import software.amazon.awssdk.services.ssm.model.TooManyUpdatesException;
import software.amazon.awssdk.services.ssm.model.DeleteMaintenanceWindowRequest;
import software.amazon.awssdk.services.ssm.model.DeleteMaintenanceWindowResponse;
import software.amazon.cloudformation.exceptions.CfnThrottlingException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.HandlerErrorCode;

import java.util.function.Function;

import org.mockito.ArgumentMatchers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
>>>>>>> Updated implementation of all handler

@ExtendWith(MockitoExtension.class)
public class DeleteHandlerTest {

<<<<<<< HEAD
=======
    private static final String WINDOW_ID = "mw-1234567890";

    private DeleteHandler handler;
>>>>>>> Updated implementation of all handler
    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

<<<<<<< HEAD
=======
    @Mock
    private ExceptionTranslator exceptionTranslator;

>>>>>>> Updated implementation of all handler
    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        logger = mock(Logger.class);
<<<<<<< HEAD
    }

    @Test
    public void handleRequest_SimpleSuccess() {
        final DeleteHandler handler = new DeleteHandler();

        final ResourceModel model = ResourceModel.builder().build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();

        final ProgressEvent<ResourceModel, CallbackContext> response
            = handler.handleRequest(proxy, request, null, logger);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        assertThat(response.getCallbackContext()).isNull();
        assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        assertThat(response.getResourceModel()).isEqualTo(request.getDesiredResourceState());
        assertThat(response.getResourceModels()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getErrorCode()).isNull();
    }
=======
        exceptionTranslator = mock(ExceptionTranslator.class);
        handler = new DeleteHandler(exceptionTranslator);
    }

    @Test
    public void handleRequestWithWindowId() {
        final ResourceModel model = ResourceModel.builder()
                .windowId(WINDOW_ID)
                .build();

        final DeleteMaintenanceWindowRequest expectedDeleteMaintenanceWindowRequest =
                DeleteMaintenanceWindowRequest.builder()
                        .windowId(model.getWindowId())
                        .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
                .desiredResourceState(model)
                .build();

        final ProgressEvent<ResourceModel, CallbackContext> response =
                handler.handleRequest(proxy, request, null, logger);

        final ProgressEvent<ResourceModel, CallbackContext> expectedProgressEvent =
                ProgressEvent.defaultSuccessHandler(null);

        assertThat(response).isEqualTo(expectedProgressEvent);

        verify(proxy)
                .injectCredentialsAndInvokeV2(eq(expectedDeleteMaintenanceWindowRequest), ArgumentMatchers.<Function<DeleteMaintenanceWindowRequest, DeleteMaintenanceWindowResponse>>any());
        verifyZeroInteractions(exceptionTranslator);
    }

    @Test
    void handleRequestWithNoRequiredParametersPresent() {
        final ResourceModel model = ResourceModel.builder()
                .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
                .desiredResourceState(model)
                .build();

        final ProgressEvent<ResourceModel, CallbackContext> response =
                handler.handleRequest(proxy, request, null, logger);

        final ProgressEvent<ResourceModel, CallbackContext> expectedProgressEvent =
                ProgressEvent.<ResourceModel, CallbackContext>builder()
                        .resourceModel(model)
                        .status(OperationStatus.FAILED)
                        .errorCode(HandlerErrorCode.InvalidRequest)
                        .message("WindowId must be specified to delete a maintenance window.")
                        .build();

        assertThat(response).isEqualTo(expectedProgressEvent);
        verifyZeroInteractions(proxy);
        verifyZeroInteractions(exceptionTranslator);
    }

    @Test
    void handleRequestThrowsTranslatedServiceException() {
        final ResourceModel model = ResourceModel.builder()
                .windowId(WINDOW_ID)
                .build();

        final DeleteMaintenanceWindowRequest expectedDeleteMaintenanceWindowRequest =
                DeleteMaintenanceWindowRequest.builder()
                        .windowId(model.getWindowId())
                        .build();

        final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
                .desiredResourceState(model)
                .build();

        final TooManyUpdatesException serviceException = TooManyUpdatesException.builder().build();

        when(
                proxy.injectCredentialsAndInvokeV2(
                        eq(expectedDeleteMaintenanceWindowRequest),
                        ArgumentMatchers.<Function<DeleteMaintenanceWindowRequest, DeleteMaintenanceWindowResponse>>any()))
                .thenThrow(serviceException);
        when(
                exceptionTranslator.translateFromServiceException(
                        serviceException,
                        expectedDeleteMaintenanceWindowRequest))
                .thenReturn(new CfnThrottlingException("DeleteMaintenanceWindow", serviceException));

        Assertions.assertThrows(CfnThrottlingException.class, () -> {
            handler.handleRequest(proxy, request, null, logger);
        });

        verify(exceptionTranslator)
                .translateFromServiceException(serviceException, expectedDeleteMaintenanceWindowRequest);
    }

>>>>>>> Updated implementation of all handler
}
