package callback;

import model.json.LoginInfoObject;

public interface LoginCallback {
    void onResponse(LoginInfoObject object);
}
