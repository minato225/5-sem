package sample;

import Domain.Model.RailWayTransport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    private List<RailWayTransport> trains;

    public Button countP;
    public Button countB;
    public Button sort;
    public Button filter;

    public Label lCountP;
    public Label lCountB;

    public TreeView<String> DataShow;

    public TextField min;
    public TextField max;

    @SuppressWarnings("unchecked")
    public TreeItem<String> InitTrains(List<RailWayTransport> transport) {
        var root = new TreeItem<>("Trains");
        root.setExpanded(true);

        transport.forEach(x -> {
            var trainRoot = new TreeItem<>("Wagons");
            x.getWagons().forEach(w -> {
                var id = new TreeItem<>("ID: " + w.getId());
                var persons = new TreeItem<>("Peoples");

                w.getPeoples().forEach(p -> {
                    var person = new TreeItem<>("Name: " + p.getName());

                    person.getChildren().addAll(
                            new TreeItem<>("Baggage: " + p.getBaggage()),
                            new TreeItem<>("Age: " + p.getAge()),
                            new TreeItem<>("Stus: " + p.getStatus())
                    );

                    persons.getChildren().add(person);
                });

                id.getChildren().addAll(
                        new TreeItem<>("Type: " + w.getType()),
                        persons
                );

                trainRoot.getChildren().add(id);
            });

            var trainName = new TreeItem<>("Name: " + x.getName());
            trainName.getChildren().addAll(
                    new TreeItem<>("Country: " + x.getCountry()),
                    new TreeItem<>("Max Speed: " + x.getMaxSpeed()),
                    trainRoot
            );

            root.getChildren().add(trainName);
        });

        return root;
    }

    public void Init(ActionEvent e) {
        this.trains = DataLoader.LoadTrainsData(5);
        this.DataShow.setRoot(InitTrains(trains));
        this.countP.setDisable(false);
        this.countB.setDisable(false);
        this.sort.setDisable(false);
        this.filter.setDisable(false);
    }

    public void filter(ActionEvent e) {
        var min = this.min.getText();
        var max = this.max.getText();

        if (max.isEmpty() || min.isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR, "Empty", ButtonType.OK);
            alert.show();
            return;
        }

        this.trains = findTrainsBySpeed(Integer.parseInt(min), Integer.parseInt(max));
        this.DataShow.setRoot(InitTrains(this.trains));
    }

    public void CountPassengers(ActionEvent e) {
        var count = 0;
        for (var train : this.trains) {
            for (var w : train.getWagons()) {
                count += w.getPeoples().size();
            }
        }

        this.lCountP.setText(count + "");
    }

    public void sort(ActionEvent e) {
        Collections.sort(this.trains);
        this.DataShow.setRoot(InitTrains(this.trains));
    }


    public void CountBaggage(ActionEvent e) {
        var count = 0;

        for (var train : this.trains) {
            for (var coach : train.getWagons()) {
                for (var people : coach.getPeoples()) {
                    count += people.getBaggage().getCount();
                }
            }
        }

        this.lCountB.setText(count + "");
    }

    /**
     * Filter list of transports by speed.
     *
     * @param min allowable min speed.
     * @param max allowable max speed.
     * @return list of filtered transport depo.
     */
    public List<RailWayTransport> findTrainsBySpeed(int min, int max) {
        return this.trains.stream()
                .filter(x -> x.getMaxSpeed() > min && x.getMaxSpeed() < max)
                .collect(Collectors.toList());
    }
}
