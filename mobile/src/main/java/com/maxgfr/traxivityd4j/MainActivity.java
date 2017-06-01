package com.maxgfr.traxivityd4j;

import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                buildNetwork();
            }
        });*/

        button = (Button) findViewById(R.id.button);

        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                textView.setText("lol");
            }
        });
    }


    private void buildNetwork() {
        DenseLayer inputLayer = new DenseLayer.Builder()
                .nIn(2)
                .nOut(3)
                .name("Input")
                .build();

        DenseLayer hiddenLayer = new DenseLayer.Builder()
                .nIn(3)
                .nOut(2)
                .name("Hidden")
                .build();

        OutputLayer outputLayer = new OutputLayer.Builder()
                .nIn(2)
                .nOut(2)
                .name("Output")
                .activation(Activation.SOFTMAX)
                .build();

        NeuralNetConfiguration.Builder nncBuilder = new NeuralNetConfiguration.Builder();
        nncBuilder.iterations(60000);
        nncBuilder.learningRate(0.1);

        NeuralNetConfiguration.ListBuilder listBuilder = nncBuilder.list();
        listBuilder.layer(0, inputLayer);
        listBuilder.layer(1, hiddenLayer);
        listBuilder.layer(2, outputLayer);

        listBuilder.backprop(true);

        MultiLayerNetwork myNetwork = new MultiLayerNetwork(listBuilder.build());
        myNetwork.init();

        final int NUM_SAMPLES = 4;

        INDArray trainingInputs = Nd4j.zeros(NUM_SAMPLES, inputLayer.getNIn());
        INDArray trainingOutputs = Nd4j.zeros(NUM_SAMPLES, outputLayer.getNOut());

        // If 0,0 show 0
        trainingInputs.putScalar(new int[]{0,0}, 0);
        trainingInputs.putScalar(new int[]{0,1}, 0);
        trainingOutputs.putScalar(new int[]{0,0}, 0);
        trainingOutputs.putScalar(new int[]{0,1}, 1);

        // If 0,1 show 1
        trainingInputs.putScalar(new int[]{1,0}, 0);
        trainingInputs.putScalar(new int[]{1,1}, 1);
        trainingOutputs.putScalar(new int[]{1,0}, 1);
        trainingOutputs.putScalar(new int[]{1,1}, 0);

        // If 1,0 show 1
        trainingInputs.putScalar(new int[]{2,0}, 1);
        trainingInputs.putScalar(new int[]{2,1}, 0);
        trainingOutputs.putScalar(new int[]{2,0}, 1);
        trainingOutputs.putScalar(new int[]{2,1}, 0);

        // If 1,1 show 0
        trainingInputs.putScalar(new int[]{3,0}, 1);
        trainingInputs.putScalar(new int[]{3,1}, 1);
        trainingOutputs.putScalar(new int[]{3,0}, 0);
        trainingOutputs.putScalar(new int[]{3,1}, 1);

        DataSet myData = new DataSet(trainingInputs, trainingOutputs);
        myNetwork.fit(myData);

        // Create input
        INDArray actualInput = Nd4j.zeros(1,2);
        actualInput.putScalar(new int[]{0,0}, 1);
        actualInput.putScalar(new int[]{0,1}, 1);

        // Generate output
        INDArray actualOutput = myNetwork.output(actualInput);
        Log.d("myNetwork Output", actualOutput.toString());
    }

}

