package software.amazon.ssm.maintenancewindow;

<<<<<<< HEAD
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
=======
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import software.amazon.awssdk.services.ssm.model.CreateMaintenanceWindowRequest;
import software.amazon.awssdk.services.ssm.model.CreateMaintenanceWindowResponse;
import software.amazon.awssdk.services.ssm.model.InternalServerErrorException;
import software.amazon.cloudformation.exceptions.CfnServiceInternalErrorException;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;
import software.amazon.cloudformation.proxy.ProgressEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.ssm.maintenancewindow.translator.request.CreateMaintenanceWindowTranslator;
import software.amazon.ssm.maintenancewindow.translator.ExceptionTranslator;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
>>>>>>> Updated implementation of all handler

@ExtendWith(MockitoExtension.class)
public class CreateHandlerTest {

<<<<<<< HEAD
=======
    private static final Boolean ALLOWED_UNASSOCIATED_TARGETS = false;
    private static final Integer CUTOFF = 1;
    private static final String SCHEDULE = "cron(0 4 ? * SUN *)";
    private static final Integer DURATION = 2;
    private static final String NAME = "TestMaintenanceWindow";
    private static final String WINDOW_ID = "mw-1234567890";
    private static final ResourceModel model = ResourceModel.builder()
            .name(NAME)
            .allowUnassociatedTargets(ALLOWED_UNASSOCIATED_TARGETS)
            .schedule(SCHEDULE)
            .duration(DURATION)
            .cutoff(CUTOFF)
            .build();
    private static final ResourceHandlerRequest<ResourceModel> request = ResourceHandlerRequest.<ResourceModel>builder()
            .desiredResourceState(model)
            .build();
    private static final CreateMaintenanceWindowRequest createMaintenanceWindowRequest =
            CreateMaintenanceWindowRequest.builder()
                    .name(model.getName())
                    .allowUnassociatedTargets(model.getAllowUnassociatedTargets())
                    .schedule(model.getSchedule())
                    .duration(model.getDuration())
                    .cutoff(model.getCutoff())
                    .build();

    private CreateHandler handler;

>>>>>>> Updated implementation of all handler
    @Mock
    private AmazonWebServicesClientProxy proxy;

    @Mock
    private Logger logger;

<<<<<<< HEAD
=======
    @Mock
    private ExceptionTranslator exceptionTranslator;

    @Mock
    private CreateMaintenanceWindowTranslator createMaintenanceWindowTranslator;

>>>>>>> Updated implementation of all handler
    @BeforeEach
    public void setup() {
        proxy = mock(AmazonWebServicesClientProxy.class);
        logger = mock(Logger.class);
<<<<<<< HEAD
    }

    @Test
    public void handleRequest_SimpleSuccess() {
        final CreateHandler handler = new CreateHandler();

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
=======
        exceptionTranslator = mock(ExceptionTranslator.class);
        createMaintenanceWindowTranslator = mock(CreateMaintenanceWindowTranslator.class);
        handler = new CreateHandler(createMaintenanceWindowTranslator, exceptionTranslator);
    }

    @Test
    public void handleCreateRequestForSuccess() {

        when(createMaintenanceWindowTranslator.resourceModelToRequest(model))
                .thenReturn(createMaintenanceWindowRequest);

        final CreateMaintenanceWindowResponse result =
                CreateMaintenanceWindowResponse.builder()
                        .windowId(WINDOW_ID)
                        .build();

        when(
                proxy.injectCredentialsAndInvokeV2(
                        eq(createMaintenanceWindowRequest),
                        ArgumentMatchers.<Function<CreateMaintenanceWindowRequest, CreateMaintenanceWindowResponse>>any()))
                .thenReturn(result);

        final ResourceModel expectedModel = request.getDesiredResourceState();
        expectedModel.setWindowId(WINDOW_ID);

        final ProgressEvent<ResourceModel, CallbackContext> response
                = handler.handleRequest(proxy, request, null, logger);

        final ProgressEvent<ResourceModel, CallbackContext> expectedProgressEvent =
                ProgressEvent.defaultSuccessHandler(expectedModel);

        assertThat(response).isEqualTo(expectedProgressEvent);
        verifyZeroInteractions(exceptionTranslator);

    }

    @Test
    void handleCreateRequestThrowsTranslatedServiceException() {

        when(createMaintenanceWindowTranslator.resourceModelToRequest(model))
                .thenReturn(createMaintenanceWindowRequest);

        final InternalServerErrorException serviceException = InternalServerErrorException.builder().build();

        when(
                proxy.injectCredentialsAndInvokeV2(
                        eq(createMaintenanceWindowRequest),
                        ArgumentMatchers.<Function<CreateMaintenanceWindowRequest, CreateMaintenanceWindowResponse>>any()))
                .thenThrow(serviceException);

        when(exceptionTranslator.translateFromServiceException(serviceException, createMaintenanceWindowRequest))
                .thenReturn(new CfnServiceInternalErrorException("CreateMaintenanceWindowAssociation", serviceException));

        Assertions.assertThrows(CfnServiceInternalErrorException.class, () -> {
            handler.handleRequest(proxy, request, null, logger);
        });

        verify(exceptionTranslator)
                .translateFromServiceException(serviceException, createMaintenanceWindowRequest);

>>>>>>> Updated implementation of all handler
    }
}
