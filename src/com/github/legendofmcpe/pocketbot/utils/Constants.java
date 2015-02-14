package com.github.legendofmcpe.pocketbot.utils;

public interface Constants{
  public final static byte RAKNET_VERSION = 5;
  public final static int MCPE_PROTOCOL = 20;
  public final static byte[] MAGIC_BYTES = {
    (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
    (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE,
    (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD,
    (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78
  };
  public final static byte OPEN_CONNECTION_REQUEST_1 = 0x05;
  public final static byte OPEN_CONNECTION_REPLY_1 = 0x06;
  public final static byte OPEN_CONNECTION_REQUEST_2 = 0x07;
  public final static byte OPEN_CONNECTION_REPLY_2 = 0x08;
  public final static byte INCOMPATIBLE_PROTOCOL_VERSION = 0x1A;

  public final static byte LOGIN_PACKET = (byte) 0x82;

  public final static byte LOGIN_STATUS_PACKET = (byte) 0x83;

  public final static byte MESSAGE_PACKET = (byte) 0x85;
}
