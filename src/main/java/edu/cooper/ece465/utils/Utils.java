package edu.cooper.ece465.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.multipart.MultipartFile;

import org.apache.tika.Tika;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.charset.Charset;
import java.util.Random;

public class Utils {
    public static long fetchPid() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(processName.split("@")[0]);
    }

    public static void writePidToLocalFile(String fileName, final long pid) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(String.format("%d", pid));
        writer.close();
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += characters.charAt(random.nextInt(characters.length()));
        }
        return randomString;
    }

    public static boolean checkAndCreateDirectory(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);

            // Java NIO (Recommended)
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                return true; // Directory created
            } else {
                return false; // Directory already exists
            }

            // Alternatively, using File class (Java IO)
            // File directory = new File(directoryPath);
            // if (!directory.exists()) {
            //     directory.mkdirs(); // Creates all parent directories as needed
            //     return true; // Directory created
            // } else {
            //     return false; // Directory already exists
            // }
        } catch (IOException e) {
            // Handle exceptions gracefully (log, rethrow, etc.)
            // For example:
            // log.error("Error creating directory: {}", e.getMessage());
            return false; // Indicate failure
        }
    }

    public static boolean isTextFile(File file) {
        if (!file.exists() || file.isDirectory()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            int charCode;
            while ((charCode = fis.read()) != -1) {
                // Check if the character is a control character (other than whitespace)
                if (Character.isISOControl(charCode) && !Character.isWhitespace(charCode)) {
                    return false;
                }
            }
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log it or rethrow)
            // TODO: log the exception
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isTextFile(FileInputStream file) {
//        if (!file.exists() || file.isDirectory()) {
//            return false;
//        }

        try (FileInputStream fis = file) {
            int charCode;
            while ((charCode = fis.read()) != -1) {
                // Check if the character is a control character (other than whitespace)
                if (Character.isISOControl(charCode) && !Character.isWhitespace(charCode)) {
                    return false;
                }
            }
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log it or rethrow)
            // TODO: log the exception
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isTextFile(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.startsWith("text/");
    }

    // see https://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring#manual_auth
//    public static HttpHeaders createHeaders(String username, String password){
//        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(
//                    auth.getBytes(Charset.forName("US-ASCII")) );;
//            String authHeader = "Basic " + new String( encodedAuth );
//            set( "Authorization", authHeader );
//        }};
//    }

    public static String getMyIpAddress() {
        try {
            String localHost = InetAddress.getLocalHost().getHostAddress();
            return localHost;
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to fetch my IP address!", e);
        }
    }

    public static String getMyHostname() {
        try {
//            String localHost = InetAddress.getLocalHost().getCanonicalHostName();
            String localHost = InetAddress.getLocalHost().getHostName();
            return localHost;
//            InetAddress localHost = InetAddress.getLocalHost();
//            if (localHost.getAddress().length == 4) { // Check for IPv4 (4 bytes)
//                String hostname = localHost.getHostName();
//                log.debug(String.format("Hostname: %s", hostname));
//                log.debug(String.format("IPv4 Address: %s", localHost.getHostAddress()));
//                return hostname;
//            } else {
//                System.err.println("No IPv4 address found.");
//            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to fetch hostname!", e);
        }
    }

    public static String getIpFromHostPort(String hostPort) {
//        return hostPort.split(":")[0];
        // TODO: rewrite properly with exception handling
        String ip = hostPort.split(":")[0];
        try {
            InetAddress address = InetAddress.getByName(ip);
            if (address instanceof Inet4Address) {
                return(address.getHostAddress()); // Output: 1.2.3.4
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(String.format("Invalid IP address: %s: %s", hostPort, e.getMessage()));
        }
        return ip;
    }

//    public static String getHostPortOfServer() {
////        if (ipPort != null) {
//        return ipPort;
////        }
////        String ip;
////        try {
////            ip = InetAddress.getLocalHost().getHostAddress();
////        } catch (UnknownHostException e) {
////            throw new RuntimeException("failed to fetch Ip!", e);
////        }
////        int port = Integer.parseInt(System.getProperty("server.port"));
////        ipPort = ip.concat(":").concat(String.valueOf(port));
////        return ipPort;
//    }

    public static String getHostnameFromIpAddress(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(String.format("Invalid IP address: %s: %s", ip, e.getMessage()));
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}