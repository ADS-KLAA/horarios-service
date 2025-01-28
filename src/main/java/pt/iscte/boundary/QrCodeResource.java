package pt.iscte.boundary;

import com.google.zxing.WriterException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.iscte.controllers.QrCodeController;

import java.io.IOException;
import java.util.Map;

@Path("/qrcode")
public class QrCodeResource {

    @Inject
    QrCodeController qrCodeController;

    @GET
    @Path("/generate/{aulaId}")
    @Produces("image/png")
    public Response generateQrCode(@PathParam("aulaId") String aulaId) {
        try {
            byte[] qrCodeImage = qrCodeController.generateQrCode(aulaId);
            return Response.ok(qrCodeImage).type("image/png").build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        } catch (WriterException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }
}
