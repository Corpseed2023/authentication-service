package com.authentication.controller.teamMemberController;//package com.lawzoom.companyservice.controller.teamMemberController;
//
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//
//public class IPTest {
//
//    public static void main(String[] args) {
//        try {
//            System.out.println("hhhh");
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            System.out.println(networkInterfaces);
//
//            while (networkInterfaces.hasMoreElements()) {
//                System.out.println(networkInterfaces);
//
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                System.out.println(networkInterface);
//
//
//                // Check if the interface is a Wi-Fi interface
//                if (networkInterface.getDisplayName().toLowerCase().contains("wi-fi")
//
//                        || networkInterface.getDisplayName().toLowerCase().contains("wireless")) {
//                    System.out.println(networkInterface);
//
//                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
//
//                    System.out.println(inetAddresses);
//                    System.out.println("gkyuiygj");
//
//
//
//                    while (inetAddresses.hasMoreElements()) {
//                        InetAddress inetAddress = inetAddresses.nextElement();
//
//                        // Check if it's not a loopback address and is an IPv6 address
//                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof java.net.Inet6Address) {
//                            System.out.println("Wi-Fi IPv6 Address: " + inetAddress.getHostAddress());
//                        }
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}