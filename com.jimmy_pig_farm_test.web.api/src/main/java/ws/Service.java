package ws;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bis.*;
import model.Request;

@Path("/json")
public class Service {


    @POST
    @Path("/GetAR")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetAR(Request request) {
        return Response.ok(new BISAR().GetAR(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/InsertAR")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InsertAR(Request request) {
        return Response.ok(new BISAR().InsertAR(request), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/GetAdminUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetAdminUser(Request request) {
        return Response.ok(new BISAdminUser().GetAdminUser(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/InsertAdminUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InsertAdminUser(Request request) {
        return Response.ok(new BISAdminUser().InsertAdminUser(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/UpdateAdminUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateAdminUser(Request request) {
        return Response.ok(new BISAdminUser().UpdateAdminUser(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/DeleteAdminUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeleteAdminUser(Request request) {
        return Response.ok(new BISAdminUser().DeleteAdminUser(request), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/GetMasterFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMasterFile(Request request) {
        return Response.ok(new BISGetMasterFile().GetMasterFile(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/newGetMasterFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newGetMasterFile(Request request) {
        return Response.ok(new BISGetMasterFile().newMasterFile(request), MediaType.APPLICATION_JSON).build();
    }



  @POST
        @Path("/UpdateUpdatemaster")
        @Produces(MediaType.APPLICATION_JSON)
        public Response Updatemaster(Request request) {
            return Response.ok(new BISGetMasterFile().Updatemaster(request), MediaType.APPLICATION_JSON).build();
        }

    @POST
    @Path("/Insertmaster")
    @Produces(MediaType.APPLICATION_JSON)
    public Response BISGetMasterFile(Request request) {
        return Response.ok(new BISGetMasterFile ().Insertmaster(request), MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/newInsertmaster")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newinsertMasterFile(Request request) {
        return Response.ok(new BISGetMasterFile ().NewInsertmaster(request), MediaType.APPLICATION_JSON).build();
    }



    @POST
        @Path("/Deletemaster")
        @Produces(MediaType.APPLICATION_JSON)
        public Response Deletemaster(Request request) {
            return Response.ok(new BISGetMasterFile().Deletemaster(request), MediaType.APPLICATION_JSON).build();
        }


    @POST
    @Path("/Insertburn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Insertburn(Request request) {
        return Response.ok(new BISburn ().Insertburn(request), MediaType.APPLICATION_JSON).build();
    }
@POST
@Path("/Getburn")
@Produces(MediaType.APPLICATION_JSON)
public Response Getburn(Request request) {
    return Response.ok(new BISburn ().Getburn(request), MediaType.APPLICATION_JSON).build();
}

    @POST
    @Path("/Delburn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Delburn(Request request) {
        return Response.ok(new BISburn ().Deleteburn(request), MediaType.APPLICATION_JSON).build();
    }



    @POST
    @Path("/GetMMSRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMMSRecord(Request request) {
        return Response.ok(new BISMMSRecord().GetMMSRecord(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/InsertMMSRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InsertMMSRecord(Request request) {
        return Response.ok(new BISMMSRecord().InsertMMSRecord(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/MMSFileUpload")
    @Produces(MediaType.APPLICATION_JSON)
    public Response MMSFileUpload(Request request) {
        return Response.ok(new BISMMSRecord().MMSFileUpload(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/SendEmail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response SendEmail(Request request) {
        return Response.ok(new BISMMSRecord().SendEmail(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/UploadImage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response UploadImage(Request request) {
        return Response.ok(new BISFileInfo().UploadImage(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/GetCameraNotificationControl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetCameraNotificationControl(Request request) {
        return Response.ok(new BISCameraNotificationControl().GetCameraNotificationControl(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/DeleteCameraNotificationControl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeleteCameraNotificationControl(Request request) {
        return Response.ok(new BISCameraNotificationControl().DeleteCameraNotificationControl(request), MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Path("/InsertCameraNotificationControl")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InsertCameraNotificationControl(Request request) {
        return Response.ok(new BISCameraNotificationControl().InsertCameraNotificationControl(request), MediaType.APPLICATION_JSON).build();
    }

}