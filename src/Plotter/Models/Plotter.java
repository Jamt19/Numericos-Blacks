package Plotter.Models;

import Plotter.Views.CoordinateView;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Class for creates Graph
 *
 * @author Jack Shendrikov
 *
 */
public class Plotter {

    private LineChart graph;

    private Pane screen;

    private CoordinateView coordinateView;

    public Plotter(LineChart graph, Pane screen) {
        this.graph = graph;
        this.screen = screen;
    }

    public void plot(CoordinatePair[] pointsArray,
            ArrayList<CoordinatePair> intersections, CoordinatePair punto) {
        graph.getData().clear();
        ArrayList<CoordinatePair> allPoints = new ArrayList<>();

        XYChart.Series dataSeries = new XYChart.Series();

        for (final CoordinatePair point : pointsArray) {
            if (point == null) {
                continue;
            }
            XYChart.Data dataPoint = new XYChart.Data<>(point.getKey(), point.getValue());

            final Circle graphPoint = new Circle(3);
            graphPoint.setFill(Color.BLACK);  // only here to later check whether it has been initialized

            for (CoordinatePair intersection : intersections) {

                if (roundPair(intersection).equals(roundPair(point))) {
                    graphPoint.setFill(Color.RED);
                    graphPoint.setOnMouseClicked(event -> {
                        coordinateView = new CoordinateView(roundDecimals(point.getKey(), 3), roundDecimals(point.getValue(), 3));

                        coordinateView.setLayoutX(graphPoint.getLayoutX() + 50);
                        coordinateView.setLayoutY(graphPoint.getLayoutY() + 50);

                        screen.getChildren().add(coordinateView);
                    });
                    graphPoint.setOnMouseReleased(event -> {
                        screen.getChildren().remove(coordinateView);

                        coordinateView = null;
                    });
                    break;
                }
            }

            if (graphPoint.getFill().equals(Color.BLACK)) {
                graphPoint.setFill(Color.TRANSPARENT);
            }

            graphPoint.setOnMouseEntered(event -> {
                coordinateView = new CoordinateView(roundDecimals(point.getKey(), 3), roundDecimals(point.getValue(), 3));

                coordinateView.setLayoutX(graphPoint.getLayoutX() + 50);
                coordinateView.setLayoutY(graphPoint.getLayoutY() + 50);

                screen.getChildren().add(coordinateView);
            });

            graphPoint.setOnMouseExited(event -> {
                screen.getChildren().remove(coordinateView);

                coordinateView = null;
            });

            dataPoint.setNode(graphPoint);
            point.setGraphPoint(graphPoint);

            dataSeries.getData().add(dataPoint);

            allPoints.add(point);
        }

        XYChart.Data rootPoint = new XYChart.Data<>(punto.getKey(), punto.getValue());

        final Circle graphRootPoint = new Circle(7);
        graphRootPoint.setPickOnBounds(true);
        graphRootPoint.setFill(Color.BLACK);
        graphRootPoint.setOnMouseEntered(event -> {
            coordinateView = new CoordinateView(roundDecimals(punto.getKey(), 3), roundDecimals(punto.getValue(), 3));

            coordinateView.setLayoutX(graphRootPoint.getLayoutX() + 50);
            coordinateView.setLayoutY(graphRootPoint.getLayoutY() + 50);
            event.consume();
            screen.getChildren().add(coordinateView);
        });

        graphRootPoint.setOnMouseExited(event -> {
            screen.getChildren().remove(coordinateView);
            event.consume();
            coordinateView = null;
        });

        rootPoint.setNode(graphRootPoint);
        punto.setGraphPoint(graphRootPoint);

        dataSeries.getData().add(rootPoint);

        allPoints.add(punto);

        graph.getData().add(dataSeries);
    }

    public void plot(CoordinatePair[] pointsArray,
            ArrayList<CoordinatePair> intersections, boolean arePoints) {
        ArrayList<CoordinatePair> allPoints = new ArrayList<>();
        
        XYChart.Series dataSeries = new XYChart.Series();

        for (final CoordinatePair point : pointsArray) {
            if (point == null) {
                continue;
            }
            XYChart.Data dataPoint = new XYChart.Data<>(point.getKey(), point.getValue());

            final Circle graphPoint = new Circle(3);
            graphPoint.setFill(Color.BLACK);  // only here to later check whether it has been initialized

            for (CoordinatePair intersection : intersections) {

                if (roundPair(intersection).equals(roundPair(point))) {
                    graphPoint.setFill(Color.WHITE);
                    graphPoint.setOnMouseClicked(event -> {
                        coordinateView = new CoordinateView(roundDecimals(point.getKey(), 3), roundDecimals(point.getValue(), 3));

                        coordinateView.setLayoutX(graphPoint.getLayoutX() + 50);
                        coordinateView.setLayoutY(graphPoint.getLayoutY() + 50);

                        screen.getChildren().add(coordinateView);
                    });
                    graphPoint.setOnMouseReleased(event -> {
                        screen.getChildren().remove(coordinateView);

                        coordinateView = null;
                    });
                    break;
                }
            }

            if (graphPoint.getFill().equals(Color.BLACK)) {
                graphPoint.setFill(Color.TRANSPARENT);
            }
            if (arePoints) {
                graphPoint.setFill(Color.BLUE);
            }

            graphPoint.setOnMouseEntered(event -> {
                coordinateView = new CoordinateView(roundDecimals(point.getKey(), 3), roundDecimals(point.getValue(), 3));

                coordinateView.setLayoutX(graphPoint.getLayoutX() + 50);
                coordinateView.setLayoutY(graphPoint.getLayoutY() + 50);

                screen.getChildren().add(coordinateView);
            });

            graphPoint.setOnMouseExited(event -> {
                screen.getChildren().remove(coordinateView);

                coordinateView = null;
            });

            dataPoint.setNode(graphPoint);
            point.setGraphPoint(graphPoint);

            dataSeries.getData().add(dataPoint);

            allPoints.add(point);
        }
        graph.getData().add(dataSeries);
    }
    
    public void plotNoIntersections(CoordinatePair[] pointsArray) {
        ArrayList<CoordinatePair> allPoints = new ArrayList<>();
        
        XYChart.Series dataSeries = new XYChart.Series();

        for (final CoordinatePair point : pointsArray) {
            if (point == null) {
                continue;
            }
            XYChart.Data dataPoint = new XYChart.Data<>(point.getKey(), point.getValue());

            final Circle graphPoint = new Circle(3);
            graphPoint.setFill(Color.TRANSPARENT);            

            graphPoint.setOnMouseEntered(event -> {
                coordinateView = new CoordinateView(roundDecimals(point.getKey(), 3), roundDecimals(point.getValue(), 3));

                coordinateView.setLayoutX(graphPoint.getLayoutX() + 50);
                coordinateView.setLayoutY(graphPoint.getLayoutY() + 50);

                screen.getChildren().add(coordinateView);
            });

            graphPoint.setOnMouseExited(event -> {
                screen.getChildren().remove(coordinateView);

                coordinateView = null;
            });

            dataPoint.setNode(graphPoint);
            point.setGraphPoint(graphPoint);

            dataSeries.getData().add(dataPoint);

            allPoints.add(point);
        }
        graph.getData().add(dataSeries);
    }

    public ArrayList<CoordinatePair> findIntersections(ArrayList<CoordinatePair> allPoints) {  // gets all repeated points, i.e. intersections in equation graphs
        HashSet<CoordinatePair> uniquePoints = new HashSet<>();
        ArrayList<CoordinatePair> duplicatePoints = new ArrayList<>();

        allPoints.removeAll(Collections.singleton(null));

        allPoints.replaceAll(this::roundPair); // rounds all the coordinates for easier comparisons

        for (CoordinatePair point : allPoints) {
            if (!uniquePoints.add(point)) {  // returns false if point is a duplicate
                duplicatePoints.add(point);
                point.setIntersection(true);
            }
        }

        return duplicatePoints;
    }

    private double roundDecimals(double value, int roundTo) {
        value = value * (Math.pow(10, roundTo));
        long temp = Math.round(value);
        return (double) temp / 1000;
    }

    private CoordinatePair roundPair(CoordinatePair pair) {
        double roundedKey = roundDecimals(pair.getKey(), 3);
        double roundedValue = roundDecimals(pair.getValue(), 3);

        return new CoordinatePair(roundedKey, roundedValue);
    }

}
