package com.priadko;

import com.priadko.arduino.dao.MeasureDao;
import com.priadko.arduino.dao.MeasureDaoImpl;
import com.priadko.arduino.dao.TypeMeasureDao;
import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;
import com.priadko.arduino.util.HibernateUtil;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * Hello world!
 */
public class App implements SerialPortEventListener {
    SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
    };
    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public void initialize() {
        // the next line is for Raspberry Pi and
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyUSB0");

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                saveMeasureToDB(parseMeasure(inputLine));
                System.out.println(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public static void main(String[] args) throws Exception {
        HibernateUtil.getSessionFactory().openSession();
        App main = new App();
        main.initialize();
        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();
        System.out.println("Started");
    }

    public Measure parseMeasure(String inputString) {
        Measure measure = new Measure();
        String[] st = inputString.split("=");
        if ((st.length != 2)) return null;
        String param = st[0].trim();
        String value = st[1].trim().split(" ")[0];
        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        //date.setTime(cal.getTimeInMillis());
        measure.setDateTime(date);
        TypeMeasure typeMeasure = getTypeMeasureByName(param);
        measure.setTypeMeasure(typeMeasure);
        measure.setValue(Double.valueOf(value));

        return measure;
    }

    public void saveMeasureToDB(Measure measure) {
        if (measure != null) {
            MeasureDao measureDao = new MeasureDaoImpl();
            measureDao.create(measure);
        }
    }

    public TypeMeasure getTypeMeasureByName(String s) {
        TypeMeasureDao typeMeasureDao = new TypeMeasureDao();
        List<TypeMeasure> typeMeasureByName = typeMeasureDao.getTypeMeasureByName(s);
        if (typeMeasureByName.isEmpty()) {
            TypeMeasure typeMeasure = new TypeMeasure();
            typeMeasure.setName(s);
            typeMeasureDao.create(typeMeasure);
            return typeMeasure;
        }
        return typeMeasureByName.get(0);
    }
}
