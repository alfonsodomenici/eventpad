/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.security;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import it.ad.eventpad.users.control.UserStore;
import it.ad.eventpad.users.entity.User;
import java.util.Optional;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@Path("/authentication")
@PermitAll
public class AuthenticationResource {

    @Inject
    UserStore store;

    @Inject
    JWTManager jwtManager;

    @Inject
    Logger LOG;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Credential credential) {
        Optional<it.ad.eventpad.users.entity.User> user = store.search(credential);
        if (user.isPresent()) {
            JsonObject jwt = Json.createObjectBuilder()
                    .add("token", token(user.get()))
                    .build();
            return Response.ok(jwt)
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("reason", "invalid username or password")
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("usr") String usr, @FormParam("pwd") String pwd) {
        Optional<User> user = store.search(new Credential(usr, pwd));
        if (user.isPresent()) {
            JsonObject jwt = Json.createObjectBuilder()
                    .add("token", token(user.get()))
                    .build();
            return Response.ok(jwt)
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("token", "ko")
                .build();
    }

    private String token(User usr) {
        String result = jwtManager.generate(usr);
        LOG.log(Level.INFO, "------------ generated token -------------------");
        LOG.log(Level.INFO, result);
        LOG.log(Level.INFO, "------------ curl command for test -------------");
        LOG.log(Level.INFO, "curl -v -i -H''Authorization: Bearer {0}'' http://localhost:8080/projectwork/resources/users", result);
        return result;
    }
}
