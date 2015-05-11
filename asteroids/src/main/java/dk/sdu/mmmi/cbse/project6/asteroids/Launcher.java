package dk.sdu.mmmi.cbse.project6.asteroids;

import org.apache.tuscany.sca.Node;
import static org.apache.tuscany.sca.TuscanyRuntime.runComposite;
import playn.core.PlayN;
import playn.java.JavaPlatform;

/**
 *
 * @author jcs
 */
public class Launcher {

    public static void main(String[] args) {

        Node node = runComposite("asteroids.composite", "target/classes");

        JavaPlatform.Config config = new JavaPlatform.Config();
        JavaPlatform.register(config);
        AsteroidsGame game = new AsteroidsGame(node);
        PlayN.run(game);
    }
}
