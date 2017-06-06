package com.maxgfr.traxivityd4j;

import org.apache.commons.io.IOUtils;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by maxime on 29-May-17.
 */

public class LoadMultiLayerNetwork {

    /** Instance unique non préinitialisée */
    private static LoadMultiLayerNetwork INSTANCE = null;

    /** Constructeur privé */
    private LoadMultiLayerNetwork() {
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized LoadMultiLayerNetwork getInstance() {
        if (INSTANCE == null)
        { 	INSTANCE = new LoadMultiLayerNetwork();
        }
        return INSTANCE;
    }

    public MultiLayerNetwork loadModelFromD4J (InputStream is) throws IOException {
        MultiLayerNetwork restored = ModelSerializer.restoreMultiLayerNetwork(is);
        return restored;
    }

    /*//0.8.0 of D4J : throw new UnsupportedOperationException("Reading HDF5 files from InputStreams currently unsupported.");
    public MultiLayerNetwork loadModelFromKeras (InputStream is) throws InvalidKerasConfigurationException, IOException, UnsupportedKerasConfigurationException {
        MultiLayerNetwork restored = KerasModelImport.importKerasSequentialModelAndWeights(is);
        return restored;
    }*/

}