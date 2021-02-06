public class NetherlandsFlag {

	// 荷兰国旗问题
	public static int[] partition(int[] arr, int L, int R, int p) {
		int less = L - 1; // <区的右边界
		int more = R + 1; // >区的左边界
		while (L < more) { // L是当前数的下标
			if (arr[L] < p) {
				swap(arr, ++less, L++);
			} else if (arr[L] > p) {
				swap(arr, --more, L);
			} else {
				L++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	// for test
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static int[] generateArray() {
		int[] arr = new int[10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 3);
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] test = generateArray();

		printArray(test);
		int[] res = partition(test, 0, test.length - 1, 1);
		printArray(test);
		System.out.println(res[0]);
		System.out.println(res[1]);

	}
}
