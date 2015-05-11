package dk.sdu.mmmi.cbse.project6.asteroids;

import static com.decouplink.Utilities.context;
import dk.sdu.mmmi.cbse.project6.common.data.Entity;
import dk.sdu.mmmi.cbse.project6.common.data.GameTime;
import dk.sdu.mmmi.cbse.project6.common.data.Position;
import dk.sdu.mmmi.cbse.project6.common.data.Rotation;
import dk.sdu.mmmi.cbse.project6.common.data.Scale;
import dk.sdu.mmmi.cbse.project6.common.data.View;
import dk.sdu.mmmi.cbse.project6.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.project6.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.List;
import org.apache.tuscany.sca.Node;
import org.oasisopen.sca.NoSuchServiceException;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.util.Clock;

public class AsteroidsGame extends Game.Default {

    private final Clock.Source clock = new Clock.Source(33);
    private GroupLayer layer;
    private final Object world = new Object();
    private Node node;

    public AsteroidsGame(Node node) {
        super(33); // call update every 33ms (30 times per second)
        this.node = node;
    }

    @Override
    public void init() {

        // Add clock to world context
        context(world).add(GameTime.class, new GameTime());

		// Lookup all Game Plugins using ServiceLoader
        for(IGamePluginService igps : getPluginService()){
            igps.start(world);
        }

        // create a group layer to hold everything
        layer = graphics().rootLayer();

        // create and add background image layer
        layer.add(graphics().createImmediateLayer(
                new StarRenderer(clock, world)));

        // Create views for each entity
        createViews();
    }

    @Override
    public void update(int delta) {

        clock.update(delta);
        context(world).one(GameTime.class).delta = delta;

		// Process each entity using provided processing services (i.e.,
        // ServiceLoader services)
        IEntityProcessingService entityProcessorService = getEntityProcessingService();
        for (Entity e : context(world).all(Entity.class)) {
            entityProcessorService.process(world, e);
        }
    }

    @Override
    public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
        // here!
        clock.paint(alpha);

        for (Entity e : context(world).all(Entity.class)) {
            ImageLayer view = context(e).one(ImageLayer.class);
            Position p = context(e).one(Position.class);
            Rotation r = context(e).one(Rotation.class);
            Scale s = context(e).one(Scale.class);

            view.setTranslation(p.x, p.y);
            view.setRotation(r.angle);
            view.setAlpha(1.0f);
            view.setScale(s.x, s.y);
        }
    }

    private void createViews() {
        for (Entity entity : context(world).all(Entity.class)) {

            View sprite = context(entity).one(View.class);

            String imagePath = sprite.getImageFilePath();

            Image image = assets().getImageSync(imagePath);

            ImageLayer viewLayer = graphics().createImageLayer(image);
            viewLayer.setOrigin(image.width() / 2f, image.height() / 2f);

            context(entity).add(ImageLayer.class, viewLayer);
            layer.add(viewLayer);
        }
    }

    private List<IGamePluginService> getPluginService() {

        List<IGamePluginService> gp = new ArrayList();

        try {
            gp.add(node.getService(IGamePluginService.class, "EntityComponent"));
            gp.add(node.getService(IGamePluginService.class, "PlayerComponent"));
        } catch (NoSuchServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gp;
    }

    private IEntityProcessingService getEntityProcessingService() {
        IEntityProcessingService s = null;

        try {
            s = node.getService(IEntityProcessingService.class,
                    "EntityProcessingComponent");
        } catch (NoSuchServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return s;
    }
}
