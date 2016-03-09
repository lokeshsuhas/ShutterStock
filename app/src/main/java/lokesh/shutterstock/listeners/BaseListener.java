package lokesh.shutterstock.listeners;

/**
 * Created by Lokesh on 03-03-2016.
 */
public interface BaseListener {
    void onError(String header, String message);

    void onNext();
}
