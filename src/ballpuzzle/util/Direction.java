package ballpuzzle.util;

public enum Direction {
	UP {
		@Override
		public Direction getOpposite() {
			return DOWN;
		}
	},
	DOWN {
		@Override
		public Direction getOpposite() {
			return UP;
		}
	},
	LEFT {
		@Override
		public Direction getOpposite() {
			return RIGHT;
		}
	},
	RIGHT {
		@Override
		public Direction getOpposite() {
			return LEFT;
		}
	};
	
	public abstract Direction getOpposite();
	
	public static Direction getDirByWeight(double weight) {
		if(weight == 0)
			return UP;
		else if(weight == 1)
			return DOWN;
		else if(weight == 2)
			return LEFT;
		else if(weight == 3)
			return RIGHT;
		else
			return null;
	}
}
