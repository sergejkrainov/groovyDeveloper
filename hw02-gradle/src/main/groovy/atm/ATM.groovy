package atm

class ATM implements ATMCommon {

    def billValues
    def currentSum;
    def currentSumRur;
    def currentSumUSD;
    def currentSumEuro;
    def banknotes = new ArrayList<Banknote>()
    def banknotesToAdd = new ArrayList<Banknote>()
    def valueToCountRur;
    def valueToCountUSD;
    def valueToCountEuro;

    ATM() {
        billValues = [] // Not empty, sorted
        valueToCountRur = [:]
        valueToCountUSD = [:]
        valueToCountEuro = [:]

        Nominal.values().each{
            billValues << it.getAmount()
        }
        for (def i in billValues) {
            valueToCountRur[i] = 0L;
            valueToCountUSD[i] = 0L;
            valueToCountEuro[i] = 0L;
        }
        currentSum = 0;
        currentSumRur = 0;
        currentSumUSD = 0;
        currentSumEuro = 0;
    }

    def put(def value, def count, def currency) {
        if (!billValues.contains(value)) {
            throw new IllegalArgumentException("Invalid bill value: " + value);
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count of values(<= 0): " + count);
        }
        if (currentSum + (long) value * count < 0) { // overflow
            throw new ArithmeticException("Long overflow");
        }
        currentSum = (long) value * count;
        switch(currency){
            case "Rur":
                currentSumRur += currentSum
                break
            case "USD":
                currentSumUSD += currentSum
                break
            case "Euro":
                currentSumEuro += currentSum
                break
        }
        Nominal findNom;
        Currency findCur = Currency.valueOf(currency)
        for(Nominal nom in Nominal.values()){
            if(nom.getAmount() == value){
                findNom = nom;
            }
        }
        for(int i =0; i < count; i++){
            banknotesToAdd << new Banknote(findNom, findCur)
        }

        banknotesToAdd.each {
            switch(currency){
                case "Rur":
                    valueToCountRur[it.getNominal().getAmount()] += 1
                    break
                case "USD":
                    valueToCountUSD[it.getNominal().getAmount()] += 1
                    break
                case "Euro":
                    valueToCountEuro[it.getNominal().getAmount()] += 1
                    break
            }
        }
        banknotes.addAll(banknotesToAdd)
        banknotesToAdd.removeAll(banknotesToAdd)

        return currentSum;
    }

    void dump() {
        println(getResultDump(valueToCountRur, "Rur"))
        println(getResultDump(valueToCountUSD, "USD"))
        println(getResultDump(valueToCountEuro, "Euro"))
    }

    String getResultDump(def valueToCount, def currency){
        def result = currency + ":";
        for (def i in billValues) {
            result += "\n" + i + " " + valueToCount[i];
        }
        return result;
    }

    void state() {
        println("Rur:" + currentSumRur);
        println("USD:" + currentSumUSD);
        println("Euro:" + currentSumEuro);

    }

    def get(def sumNeed, def currency) {
        if (sumNeed < 0) {
            throw new IllegalArgumentException("Invalid requested sum: (<= 0)" + sumNeed);
        }
        if (sumNeed > 40_000_000) { // 50_000_000 = OutOfMemoryError
            throw new IllegalArgumentException("Requested sum is too large (40_000_000 maximum)");
        }

        def curSum
        switch(currency){
            case "Rur":
                curSum = currentSumRur
                break
            case "USD":
                curSum = currentSumUSD
                break
            case "Euro":
                curSum = currentSumEuro
                break
        }
        def dp = new int[billValues.size()][(int) Math.min(sumNeed, curSum) + 1];   // dp[x][y] - minimal count of bills
        for (int[] row in dp) {                                                              // with value billValues[x] we need
            Arrays.fill(row, -1);                                                       // to get y as the sum and -1 if cant
        }

        //calculate reachSum
        def reachSum
        def callFuncResult
        def result = ""
        switch(currency){
            case "Rur":
                callFuncResult = calculateReachSum(dp, valueToCountRur, currentSumRur, sumNeed)
                reachSum = callFuncResult["reachSum"]
                dp = callFuncResult["dp"]
                result = calculateCurentSumRur(reachSum, dp)
                break
            case "USD":
                callFuncResult = calculateReachSum(dp, valueToCountUSD, currentSumUSD, sumNeed)
                reachSum = callFuncResult["reachSum"]
                dp = callFuncResult["dp"]
                result = calculateCurentSumUSD(reachSum, dp)
                break
            case "Euro":
                callFuncResult = calculateReachSum(dp, valueToCountEuro, currentSumEuro, sumNeed)
                reachSum = callFuncResult["reachSum"]
                dp = callFuncResult["dp"]
                result = calculateCurentSumEuro(reachSum, dp)
                break
        }

        result += "summary: " + reachSum;

        if (reachSum < sumNeed) {
            result += "\nWithout " + (sumNeed - reachSum);
        }

        return result;
    }


    def calculateReachSum(def dp, def valueToCount, def currentSum, def sumNeed) {
        for (def i = 0; i * billValues[0] < dp[0].length && i <= valueToCount[billValues[0]]; ++i) {   // using only one value - billValues[0]
            dp[0][i * billValues[0]] = i;
        }

        for (def valInd = 1; valInd < billValues.size(); ++valInd) {
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
        while (dp[billValues.size() - 1][reachSum] == -1) { // find the greatest reachable sum
            --reachSum;
        }
        def returnResult = [:]
        returnResult["reachSum"] = reachSum
        returnResult["dp"] = dp
        return returnResult
    }
    def calculateCurentSumRur(def sumIndex, def dp) {
        def result = ""
        for (def i = billValues.size() - 1; i >= 0; --i) {
            def value = billValues[i];
            if (dp[i][sumIndex] != 0) {
                result += value + "=" + dp[i][sumIndex] + ", ";
            }
            valueToCountRur[value] =  valueToCountRur[value] - dp[i][sumIndex];
            currentSumRur -= dp[i][sumIndex] * value;
            sumIndex -= dp[i][sumIndex] * billValues[i];
        }
        return result
    }

    def calculateCurentSumUSD(def sumIndex, def dp) {
        def result = ""
        for (def i = billValues.size() - 1; i >= 0; --i) {
            def value = billValues[i];
            if (dp[i][sumIndex] != 0) {
                result += value + "=" + dp[i][sumIndex] + ", ";
            }
            valueToCountUSD[value] =  valueToCountUSD[value] - dp[i][sumIndex];
            currentSumUSD -= dp[i][sumIndex] * value;
            sumIndex -= dp[i][sumIndex] * billValues[i];
        }
        return result
    }

    def calculateCurentSumEuro(def sumIndex, def dp) {
        def result = ""
        for (def i = billValues.size() - 1; i >= 0; --i) {
            def value = billValues[i];
            if (dp[i][sumIndex] != 0) {
                result += value + "=" + dp[i][sumIndex] + ", ";
            }
            valueToCountEuro[value] =  valueToCountEuro[value] - dp[i][sumIndex];
            currentSumEuro -= dp[i][sumIndex] * value;
            sumIndex -= dp[i][sumIndex] * billValues[i];
        }
        return result
    }

}
