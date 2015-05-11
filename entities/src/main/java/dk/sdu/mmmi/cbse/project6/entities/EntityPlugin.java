package dk.sdu.mmmi.cbse.project6.entities;

import com.decouplink.DisposableList;
import com.decouplink.Link;

import static com.decouplink.Utilities.context;
import dk.sdu.mmmi.cbse.project6.common.data.Entity;
import dk.sdu.mmmi.cbse.project6.common.services.IGamePluginService;
import static dk.sdu.mmmi.cbse.project6.entities.EntityFactory.createEnemyShip;

public class EntityPlugin implements IGamePluginService {

    DisposableList entities = new DisposableList();

    public EntityPlugin() {
    }

    @Override
    public void start(Object world) {

        // Add entities to the world
        Link<Entity> el = context(world).add(Entity.class, createEnemyShip());
        entities.add(el);
    }

    @Override
    public void stop() {
        // Remove entities
        entities.dispose();
    }

}
