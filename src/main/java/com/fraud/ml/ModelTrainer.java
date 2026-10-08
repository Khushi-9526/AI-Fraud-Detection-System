package com.fraud.ml;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class ModelTrainer {

    public static void main(String[] args) {

        try {

            // Load training dataset
            File file = new File(
                    "src/main/resources/fraud_training.csv"
            );

            CSVLoader loader = new CSVLoader();
            loader.setSource(file);

            Instances data = loader.getDataSet();

            System.out.println(
                    "Training data loaded: "
                    + data.numInstances()
                    + " transactions"
            );

            // Convert categorical columns to nominal
            StringToNominal filter =
                    new StringToNominal();

            filter.setAttributeRange("2-6");
            filter.setInputFormat(data);

            data = Filter.useFilter(data, filter);

            // Last column is the fraud class
            data.setClassIndex(
                    data.numAttributes() - 1
            );

            // Create Random Forest
            RandomForest model =
                    new RandomForest();

            model.setNumIterations(100);
            model.setSeed(42);

            System.out.println(
                    "Training Random Forest..."
            );

            // Train the model
            model.buildClassifier(data);

            System.out.println(
                    "Model training completed."
            );

            // Evaluate model
            Evaluation evaluation =
                    new Evaluation(data);

            evaluation.crossValidateModel(
                    model,
                    data,
                    5,
                    new java.util.Random(42)
            );

            System.out.println(
                    "\n===== MODEL EVALUATION ====="
            );

            System.out.println(
                    evaluation.toSummaryString()
            );

            System.out.println(
                    "Correctly Classified: "
                    + evaluation.pctCorrect()
                    + "%"
            );

            System.out.println(
                    "Incorrectly Classified: "
                    + evaluation.pctIncorrect()
                    + "%"
            );

            // Save trained model
            File modelFile =
                    new File(
                            "src/main/resources/fraud_model.model"
                    );

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(modelFile)
                    );

            output.writeObject(model);
            output.close();

            System.out.println(
                    "\nTrained model saved to:"
            );

            System.out.println(
                    modelFile.getAbsolutePath()
            );

        }
        catch (Exception e) {

            System.out.println(
                    "Error while training model:"
            );

            e.printStackTrace();
        }
    }
}
