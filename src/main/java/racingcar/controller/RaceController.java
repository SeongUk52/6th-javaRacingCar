package racingcar.controller;

import static java.util.stream.Collectors.joining;

import java.util.List;
import racingcar.domain.Car;
import racingcar.domain.TryRemains;
import racingcar.service.CarMover;
import racingcar.service.WinnerChecker;
import racingcar.verifier.InputVerifier;
import racingcar.view.InputHandler;
import racingcar.view.OutputHandler;

public class RaceController {
    private final List<Car> carList;
    private TryRemains tryRemains;

    public RaceController(List<Car> carList) {
        this.carList = carList;
        createTryRemains(InputHandler.inputNumberOfTry());
        OutputHandler.printRaceProgressLabel();
        doTry();
        printFinalWinner();
    }

    private void doTry() {
        if (tryRemains.isZero()) {
            return;
        }
        tryRemains.doTry();
        CarMover.move(carList);
        printRaceProgress();
        doTry();
    }

    private void printRaceProgress() {
        OutputHandler.printRaceProgress(
                carList.stream()
                        .map(Car::getNameToString)
                        .toList(),
                carList.stream()
                        .map(Car::getProgressToInt)
                        .toList());
    }

    private void printFinalWinner() {
        OutputHandler.printFinalWinner(
                WinnerChecker.findWinner(carList)
                        .stream()
                        .map(Car::getNameToString)
                        .collect(joining(", ")));
    }

    private void createTryRemains(String userInput) {
        InputVerifier.verifyTry(userInput);
        tryRemains = new TryRemains(Integer.parseInt(userInput));
    }
}
