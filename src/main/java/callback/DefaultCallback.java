package callback;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public abstract class DefaultCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        //переподключение
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        ResponseBody resBody = response.body();
        if (resBody != null) {
            String res = resBody.string();
            int statusCode = response.code();
            switch (statusCode) {
                case 200:
                    onSuccess(res);
                    break;
                default:
                    onError(statusCode);
                    break;
            }
        }
    }

    public abstract void onSuccess(String response);
    public abstract void onError(int statusCode);
}
