package com.fraud.ml;

import com.fraud.model.Transaction;

import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FraudMLService {

    private RandomForest model;
    private Instances structure;

    public FraudMLService() {
        try {
            InputStream stream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("fraud_model.model");

            if (stream == null) {
                throw new IllegalStateException(
                        "fraud_model.model was not found."
                );
            }

            try (ObjectInputStream input =
                         new ObjectInputStream(stream)) {
                model = (RandomForest) input.readObject();
            }

            structure = createStructure();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not load the fraud detection model.", e
            );
        }
    }

    private Instances createStructure() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        attributes.add(new Attribute("amount"));

        attributes.add(new Attribute(
                "location",
                Arrays.asList(
                        "Bangalore", "Delhi", "Mumbai",
                        "Pune", "Unknown"
                )
        ));

        attributes.add(new Attribute(
                "transactionType",
                Arrays.asList("Offline", "Online")
        ));

        attributes.add(new Attribute(
                "international",
                Arrays.asList("No", "Yes")
        ));

        attributes.add(new Attribute(
                "device",
                Arrays.asList("Laptop", "Mobile", "Unknown")
        ));

        attributes.add(new Attribute(
                "fraud",
                Arrays.asList("No", "Yes")
        ));

        Instances data = new Instances(
                "FraudDetection",
                attributes,
                0
        );

        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    public FraudPrediction predict(Transaction transaction)
            throws Exception {

        Instance instance = new DenseInstance(
                structure.numAttributes()
        );

        instance.setDataset(structure);

        instance.setValue(0, transaction.getAmount());
        instance.setValue(1, transaction.getLocation());
        instance.setValue(2, transaction.getTransactionType());
        instance.setValue(
                3,
                transaction.isInternational() ? "Yes" : "No"
        );
        instance.setValue(4, transaction.getDevice());

        instance.setMissing(5);

        double[] probabilities =
                model.distributionForInstance(instance);

        double fraudProbability = probabilities[1] * 100;

        String prediction;
        String riskLevel;

        if (fraudProbability >= 70) {
            prediction = "FRAUD";
            riskLevel = "HIGH";
        } else if (fraudProbability >= 40) {
            prediction = "SUSPICIOUS";
            riskLevel = "MEDIUM";
        } else {
            prediction = "SAFE";
            riskLevel = "LOW";
        }

        return new FraudPrediction(
                fraudProbability,
                prediction,
                riskLevel
        );
    }
}
