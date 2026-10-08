package com.fraud.ml;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.SerializationHelper;

import java.io.File;

public class ModelTrainer {

    public static void main(String[] args) {

        try {

            // Training dataset
            File file = new File(
                    "src/main/resources/fraud_training.csv"
            );

            // Load CSV dataset
            CSVLoader loader = new CSVLoader();
            loader.setSource(file);

            Instances data = loader.getDataSet();

            // Last column is the target/class
            data.setClassIndex(data.numAttributes() - 1);

            System.out.println(
                    "Training records: "
                            + data.numInstances()
            );

            System.out.println(
                    "Features: "
                            + (data.numAttributes() - 1)
            );

            // Create Random Forest
            RandomForest model = new RandomForest();

            model.setNumIterations(100);

            // Train model
            model.buildClassifier(data);

            // Evaluate model using 10-fold cross validation
            Evaluation evaluation =
                    new Evaluation(data);

            evaluation.crossValidateModel(
                    model,
                    data,
                    10,
                    new java.util.Random(42)
            );

            System.out.println();
            System.out.println(
                    "===== MODEL PERFORMANCE ====="
            );

            System.out.println(
                    "Accuracy: "
                            + String.format(
                                    "%.2f",
                                    evaluation.pctCorrect()
                            )
                            + "%"
            );

            System.out.println(
                    "Precision: "
                            + String.format(
                                    "%.2f",
                                    evaluation.weightedPrecision()
                            )
            );

            System.out.println(
                    "Recall: "
                            + String.format(
                                    "%.2f",
                                    evaluation.weightedRecall()
                            )
            );

            System.out.println();

            // Save trained model
            File modelFile = new File(
                    "src/main/resources/fraud_model.model"
            );

            SerializationHelper.write(
                    modelFile.getAbsolutePath(),
                    model
            );

            System.out.println(
                    "Model saved successfully:"
            );

            System.out.println(
                    modelFile.getAbsolutePath()
            );

        }
        catch (Exception e) {

            System.out.println(
                    "Model training failed."
            );

            e.printStackTrace();
        }
    }
}
