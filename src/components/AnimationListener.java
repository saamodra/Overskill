
package components;

import java.util.List;

public interface AnimationListener {

    public void onStart();

    public void onAnimation(double percent);

    public void onStop();

    public void onEnd();
}
