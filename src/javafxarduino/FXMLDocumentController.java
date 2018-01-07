/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxarduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Samuelson
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox cbPortas;
    @FXML
    private Button btnConectar;

    private SerialPort porta;
    private int led = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarPortas();

    }

    public void carregarPortas() {

        SerialPort[] portNames = SerialPort.getCommPorts();

        for (SerialPort portName : portNames) {
            cbPortas.getItems().add(portName.getSystemPortName());
        }

    }

    @FXML
    private void conectar(ActionEvent event) {

        if (btnConectar.getText().equals("Conectar")) {

            porta = SerialPort.getCommPort(cbPortas.getSelectionModel().getSelectedItem().toString());
            porta.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

            if (porta.openPort()) {
                btnConectar.setText("Desconectar");
                cbPortas.setDisable(true);
            }

        } else {

            porta.closePort();
            cbPortas.setDisable(false);
            btnConectar.setText("Conectar");

        }
    }
    
    @FXML
    private void ligarLed(ActionEvent event) {

       PrintWriter output = new PrintWriter(porta.getOutputStream());
       
       if(led == 0){
           output.print("1");
           output.flush();
           led = 1;
       }else{
           output.print("0");
           output.flush();
           led = 0;
       }
       
    }

}
