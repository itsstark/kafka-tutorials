
package com.example.syslogtestserver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketAddress;
import org.productivity.java.syslog4j.server.SyslogServerConfigIF;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import org.productivity.java.syslog4j.server.SyslogServerIF;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;
import org.productivity.java.syslog4j.util.SyslogUtility;

import com.example.kafkaserver.KafkaTestProducer;

/**
 *
 * @author vishal
 */
public class TestServer {

    public static void usage() {
        
        System.out.println("Usage:");
        System.out.println();
        System.out.println("\t --host <host_address>");
        System.out.println();
        System.out.println("\t --port <port_number>");
    }
    
    // validating input format
    public static Boolean validate(String[] args) {
        if(args.length != 4) {
            usage();
            System.exit(1);
        }
        if(args[0].equals("--host") && args[1].contains(".") && args[2].equals("--port") && args[3].matches("[0-9]+")) {
            return true;
        }
        else if(args[2].equals("--host") && args[3].contains(".") && args[0].equals("--port") && args[1].matches("[0-9]+")) {
            return true;
        }
        else {
            usage();
            System.exit(0);
        }
        return false;
    }
    
    public static void main(String[] args) {
        
        // IMPORTANT if(validate(args)) {
            SyslogServerIF syslogServer = org.productivity.java.syslog4j.server.SyslogServer.getInstance("udp");
            SyslogServerConfigIF syslogServerConfig = syslogServer.getConfig();
            /* IMPORTANT command line arguments 
            if(args[0].equals("--host")) {
                syslogServerConfig.setHost(args[1]);
                syslogServerConfig.setPort(Integer.parseInt(args[3]));
            }
            else {
                syslogServerConfig.setHost(args[3]);
                syslogServerConfig.setPort(Integer.parseInt(args[1]));
            }*/
            syslogServerConfig.setHost("192.168.1.37");
            syslogServerConfig.setPort(5555);
            System.out.println("Syslog Started");
            syslogServerConfig.addEventHandler( new ForwardKafka());
            org.productivity.java.syslog4j.server.SyslogServer.getThreadedInstance("udp");

            //System.out.println("DONE");
            while (true) {
               SyslogUtility.sleep(1000);
            }
        //}
    }
}

class ForwardKafka implements SyslogServerSessionEventHandlerIF {

    @Override
    public Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event) {
        
        // Checking values
        System.out.println("event.msg : " + event.getMessage());
        System.out.println("event.date: " + event.getDate());
        System.out.println("event.date: " + event.getLevel());
        
        /*try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("events.log", true)))) {
            out.println(event.getDate());
            out.println(event.getMessage());
            KafkaTestProducer kafkaProducer = new KafkaTestProducer("topic1", String.valueOf(event.getDate()), "FATAL", event.getMessage());
        }
        catch (IOException e) {
            System.out.println(e);
        }*/
        KafkaTestProducer kafkaProducer = new KafkaTestProducer("topic1", String.valueOf(event.getDate()), "FATAL", event.getMessage());
    }

    @Override
    public void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(SyslogServerIF syslogServer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(SyslogServerIF syslogServer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
