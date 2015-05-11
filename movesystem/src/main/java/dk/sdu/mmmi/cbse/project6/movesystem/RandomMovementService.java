package dk.sdu.mmmi.cbse.project6.movesystem;

import static com.decouplink.Utilities.context;
import dk.sdu.mmmi.cbse.project6.common.data.BehaviourEnum;
import dk.sdu.mmmi.cbse.project6.common.data.Entity;
import dk.sdu.mmmi.cbse.project6.common.data.GameTime;
import dk.sdu.mmmi.cbse.project6.common.data.Position;
import dk.sdu.mmmi.cbse.project6.common.data.Rotation;
import dk.sdu.mmmi.cbse.project6.common.data.Velocity;
import dk.sdu.mmmi.cbse.project6.common.services.IEntityProcessingService;

public class RandomMovementService implements IEntityProcessingService {

	private int turnDirection = 1;

	@Override
	public void process(Object world, Entity entity) {
		BehaviourEnum b = context(entity).one(BehaviourEnum.class);

		if (b.equals(BehaviourEnum.RANDOM_MOVEMENT)) {

			// Get context from entity
			Rotation rotation = context(entity).one(Rotation.class);
			Position position = context(entity).one(Position.class);
			Velocity velocity = context(entity).one(Velocity.class);

			// Generate random movement direction
			if (Math.random() < 0.1) {
				turnDirection = -turnDirection;
			}
			rotation.angle += turnDirection * 0.1;

			double deltaT = context(world).one(GameTime.class).delta;

			position.x += velocity.vectorX * Math.cos(rotation.angle) * deltaT;
			position.y += velocity.vectorY * Math.sin(rotation.angle) * deltaT;

		}
	}
}
