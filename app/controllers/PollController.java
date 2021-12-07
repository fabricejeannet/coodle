package controllers;

import com.google.inject.Inject;
import domain.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

public class PollController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public PollController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result create(Http.Request request) {
        Form<User> userForm = formFactory.form(User.class).withDirectFieldAccess(true);
        User user = userForm.bindFromRequest(request).get();
        return ok();
    }

}
