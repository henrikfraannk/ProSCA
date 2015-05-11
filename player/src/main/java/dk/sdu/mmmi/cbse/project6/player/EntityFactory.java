package dk.sdu.mmmi.cbse.project6.player;

import static com.decouplink.Utilities.context;
import dk.sdu.mmmi.cbse.project6.common.data.BehaviourEnum;
import dk.sdu.mmmi.cbse.project6.common.data.Entity;
import dk.sdu.mmmi.cbse.project6.common.data.Position;
import dk.sdu.mmmi.cbse.project6.common.data.Rotation;
import dk.sdu.mmmi.cbse.project6.common.data.Scale;
import dk.sdu.mmmi.cbse.project6.common.data.Velocity;
import dk.sdu.mmmi.cbse.project6.common.data.View;

public class EntityFactory {

    public static Entity createPlayerShip() {
        Entity enemyShip = new Entity();
        context(enemyShip).add(View.class, new View("images/Ship.png"));
        context(enemyShip).add(Position.class, new Position(360, 280));
        context(enemyShip).add(Rotation.class, new Rotation());
        context(enemyShip).add(Velocity.class, new Velocity());
        context(enemyShip).add(Scale.class, new Scale());
        context(enemyShip).add(BehaviourEnum.class,
                BehaviourEnum.RANDOM_MOVEMENT);
        return enemyShip;
    }
}
