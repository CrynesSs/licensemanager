package com.swa.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class IPAddress {

    private static final String IP_ADDRESS_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                    "{3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private String ipAddress;

    // Constructors
    public IPAddress() {
        // Default constructor
    }

    public IPAddress(String ipAddress) {
        if (isValidIPAddress(ipAddress)) {
            this.ipAddress = ipAddress;
        } else {
            throw new IllegalArgumentException("Invalid IP address format");
        }
    }

    // Getter and Setter
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        if (isValidIPAddress(ipAddress)) {
            this.ipAddress = ipAddress;
        } else {
            throw new IllegalArgumentException("Invalid IP address format");
        }
    }

    // IP address validation
    private boolean isValidIPAddress(String ipAddress) {
        return Pattern.matches(IP_ADDRESS_REGEX, ipAddress);
    }

    // Additional methods, equals, hashCode, toString, etc.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPAddress ipAddress1 = (IPAddress) o;
        return Objects.equals(ipAddress, ipAddress1.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress);
    }

    @Override
    public String toString() {
        return "IPAddress{" +
                "ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
