package pl.com.morgoth.touring.machine;

public enum Direction {
	LEFT {
		@Override
		public String toString() {
			return "<-";
		}
	},
	RIGHT {
		@Override
		public String toString() {
			return "->";
		}

	};
}
