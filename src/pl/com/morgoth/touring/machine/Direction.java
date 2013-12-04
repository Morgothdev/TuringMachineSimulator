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

	public static Direction valueof(String text) {
		if (text.equals("->")) {
			return RIGHT;
		} else if (text.equals("<-")) {
			return LEFT;
		} else {
			throw new IllegalArgumentException("Unknown direction \"" + text+"\"");
		}
	}
}
