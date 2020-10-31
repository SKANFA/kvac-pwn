package com.github.kvac.pwn.kvacpwnroot.kvac.pwn.libs.crypter;

public interface Crypt {

    byte[] encrypt(byte[] data);

    byte[] decrypt(byte[] data);
}

