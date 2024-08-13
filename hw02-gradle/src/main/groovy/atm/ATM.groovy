package atm

class ATM implements ATMCommon {

    def billValues;
    def valueToCount;
    def currentSum;

    ATM() {
        billValues = new int[]{1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000}; // Not empty, sorted
        valueToCount = [:]
        for (def i in billValues) {
            valueToCount[i] = 0L;
        }
        currentSum = 0;
    }

    def put(def value, def count) {
        if (!valueToCount.containsKey(value)) {
            throw new IllegalArgumentException("Invalid bill value: " + value);
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count of values(<= 0): " + count);
        }
        if (currentSum + (long) value * count < 0) { // overflow
            throw new ArithmeticException("Long overflow");
        }
        valueToCount[value] = valueToCount[value] + count;
        currentSum += (long) value * count;
        return currentSum;
    }

    def dump() {
        def result = "ATM:";
        for (def i in billValues) {
            result += "\n" + i + " " + valueToCount[i];
        }
        return result;
    }

    def state() {
        return currentSum;
    }

    def get(def sumNeed) {
        if (sumNeed < 0) {
            throw new IllegalArgumentException("Invalid requested sum: (<= 0)" + sumNeed);
        }
        if (sumNeed > 40_000_000) { // 50_000_000 = OutOfMemoryError
            throw new IllegalArgumentException("Requested sum is too large (40_000_000 maximum)");
        }
        def dp = new int[billValues.length][(int) Math.min(sumNeed, currentSum) + 1];   // dp[x][y] - minimal count of bills
        for (int[] row in dp) {                                                              // with value billValues[x] we need
            Arrays.fill(row, -1);                                                       // to get y as the sum and -1 if cant
        }

        for (def i = 0; i * billValues[0] < dp[0].length && i <= valueToCount[billValues[0]]; ++i) {   // using only one value - billValues[0]
            dp[0][i * billValues[0]] = i;
        }

        for (def valInd = 1; valInd < billValues.length; ++valInd) {
            dp[valInd][0] = 0;
            int value = billValues[valInd];
            for (def sum = 1; sum < dp[0].length; ++sum) {
                if (dp[valInd - 1][sum] != -1) { // can reach sum without any value bills
                    dp[valInd][sum] = 0;
                } else if (sum - value >= 0 &&
                        dp[valInd][sum - value] != -1 &&
                        dp[valInd][sum - value] < valueToCount[value]) { // can reach (sum - value)
                    dp[valInd][sum] = dp[valInd][sum - value] + 1;           // with some value bills
                }
            }
        }

        def reachSum = (int) Math.min(sumNeed, currentSum);
        while (dp[billValues.length - 1][reachSum] == -1) { // find the greatest reachable sum
            --reachSum;
        }

        String result = "";
        def sumIndex = reachSum;
        for (def i = billValues.length - 1; i >= 0; --i) {
            def value = billValues[i];
            if (dp[i][sumIndex] != 0) {
                result += value + "=" + dp[i][sumIndex] + ", ";
            }
            valueToCount[value] =  valueToCount[value] - dp[i][sumIndex];
            currentSum -= dp[i][sumIndex] * value;
            sumIndex -= dp[i][sumIndex] * billValues[i];
        }

        result += "summary: " + reachSum;

        if (reachSum < sumNeed) {
            result += "\nWithout " + (sumNeed - reachSum);
        }

        return result;
    }

}
