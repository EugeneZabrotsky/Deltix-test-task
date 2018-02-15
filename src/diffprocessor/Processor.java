package diffprocessor;

/**
 * Created by VavilauA on 6/19/2015.
 */
public class Processor {
    long limit;
    public Processor(long limit) {
        // TODO: initialize.
        this.limit = limit;
    }

    public void doProcess(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput) {
        // TODO: make "mustBeEqualTo" list equal to "expectedOutput".
        // 0. Processor will be created once and then will be used billion times.
        // 1. Use methods: AddFirst, AddLast, AddBefore, AddAfter, Remove to modify list.
        // 2. Do not change expectedOutput list.
        // 3. At any time number of elements in list could not exceed the "Limit".
        // 4. "Limit" will be passed into Processor's constructor. All "mustBeEqualTo" and "expectedOutput" lists will have the same "Limit" value.
        // 5. At any time list elements must be in non-descending order.
        // 6. Implementation must perform minimal possible number of actions (AddFirst, AddLast, AddBefore, AddAfter, Remove).
        // 7. Implementation must be fast and do not allocate excess memory.
        removeUnnecessaryElements(mustBeEqualTo, expectedOutput);
        addNecessaryElements(mustBeEqualTo, expectedOutput);
    }

    private void addNecessaryElements(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput){
        SortedLimitedList.Entry<Double> currentMustBeElement = mustBeEqualTo.first;
        SortedLimitedList.Entry<Double> currentExpectedElement = expectedOutput.first;

        while (currentMustBeElement != null){
            while (currentMustBeElement != null && currentMustBeElement.getValue().compareTo(currentExpectedElement.getValue()) > 0){
                mustBeEqualTo.addBefore(currentMustBeElement, currentExpectedElement.getValue());
                currentExpectedElement = currentExpectedElement.getNext();
            }
            currentMustBeElement = currentMustBeElement.getNext();
            currentExpectedElement = currentExpectedElement.getNext();
        }

        while (currentExpectedElement != null){
            mustBeEqualTo.addLast(currentExpectedElement.getValue());
            currentExpectedElement = currentExpectedElement.getNext();
        }
    }

    private void removeAllElementsAfterMustBeElement(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList.Entry<Double> element){
        while (element.getNext() != null)
            mustBeEqualTo.remove(element.getNext());
    }


    private void removeUnnecessaryElements(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput){
        SortedLimitedList.Entry<Double> currentMustBeElement = mustBeEqualTo.first;
        SortedLimitedList.Entry<Double> currentExpectedElement = expectedOutput.first;

        if (currentMustBeElement == null)
            return;

        if (currentExpectedElement == null){
            removeAllElementsAfterMustBeElement(mustBeEqualTo, currentMustBeElement);
            mustBeEqualTo.remove(currentMustBeElement);
            return;
        }

        while (currentMustBeElement != null){
            while (currentMustBeElement != null && currentMustBeElement.getValue().compareTo(currentExpectedElement.getValue()) > 0){
                if (currentExpectedElement.getNext() != null)
                    currentExpectedElement = currentExpectedElement.getNext();
                else{
                    removeAllElementsAfterMustBeElement(mustBeEqualTo, currentMustBeElement);
                    mustBeEqualTo.remove(currentMustBeElement);
                    currentMustBeElement = null;
                }
            }

            if (currentMustBeElement == null)
                continue;

            if (currentMustBeElement.getValue().compareTo(currentExpectedElement.getValue()) == 0){
                currentMustBeElement = currentMustBeElement.getNext();
                currentExpectedElement = currentExpectedElement.getNext();
            }

            if (currentMustBeElement == null)
                continue;

            if (currentExpectedElement == null){
                removeAllElementsAfterMustBeElement(mustBeEqualTo, currentMustBeElement);
                mustBeEqualTo.remove(currentMustBeElement);
            }

            if (currentMustBeElement.getValue().compareTo(currentExpectedElement.getValue()) < 0){
                SortedLimitedList.Entry<Double> currentMustBeElementTemp = currentMustBeElement.getNext();
                mustBeEqualTo.remove(currentMustBeElement);
                currentMustBeElement = currentMustBeElementTemp;
            }
        }
    }
}
