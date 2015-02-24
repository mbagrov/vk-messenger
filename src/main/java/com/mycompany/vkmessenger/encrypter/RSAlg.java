/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.encrypter;

import java.util.Random;
import java.math.BigInteger;

/**
 *
 * @author odour
 */
public class RSAlg {

  /*  private int p = 7;
    private int q = 13;
    private int n = 91;
    private int e = 5;
    private int eilerFunc = 72;
    private int d = 29;
    private int k = 2;
    */
 /*   private int p;
    private int q;
    private int n;
    private int e;
    private int eilerFunc;
    private int d;
    private int k;

    private int value;

    public RSAlg(int val) {
          setP();
         setQ();

         //  System.out.println(this.p);
         //  System.out.println(this.q);

         setN();

         //  System.out.println(this.n);

         setEilerFunc();

         //   System.out.println(this.eilerFunc);
        
         setE();
        
         //  System.out.println(this.e);
        
         setDK();

        //   System.out.println(this.d + " " + this.k);
        System.out.println(val);
        this.value = encrypt(val);
        System.out.println(this.value);
        System.out.println(decrypt(this.value));
    }

    private int setStartValues() {
        Random rand = new Random();
        int buf;
        while (true) {
            buf = rand.nextInt(20);
            if (buf > 1) {
                return buf;
            }
        }
    }

    private void setP() {
        this.p = setStartValues();
    }

    private void setQ() {
        this.q = setStartValues();
    }

    private void setN() {
        this.n = this.p * this.q;
    }

    private void setEilerFunc() {
        this.eilerFunc = (this.p - 1) * (this.q - 1);
    }

    private Boolean checkComprimeIntegers(int e, int eiler) {
        int eBuf = e;
        int eilerBuf = eiler;

        while (eBuf != eilerBuf) {
            if (eBuf > eilerBuf) {
                eBuf -= eilerBuf;
            } else {
                eilerBuf -= eBuf;
            }
            if (eBuf == 1) {
                return true;
            }
        }
        return false;
    }

    private void setE() {
        for (int i = 2; i < this.n; i++) {
            if (checkComprimeIntegers(i, this.eilerFunc)) {
                this.e = i;
                break;
            }
        }
    }

    private void setDK() {
        int k = 0;
        double buf;
        while (true) {
            k++;
            buf = (k * this.eilerFunc + 1) / this.e;
            if (checkInt(buf)) {
                this.d = (int) buf;
                this.k = k;
                break;
            }
        }
    }

    private Boolean checkInt(double value) {
        int buf = (int) value;
        if ((value - buf) == 0) {
            return true;
        } else {
            return false;
        }
    }
/*
    public int encrypt(int num) {
        return (int) (Math.pow(num, this.e) % this.n);
    }

    public int decrypt(int num) {
        return (int) (Math.pow(num, this.d) % this.n);
    }*/

  /*  public int encrypt(int num) {
        BigInteger number = new BigInteger(Integer.toString(num));
	BigInteger second = new BigInteger(Integer.toString(this.n));
        number = number.pow(this.e);
        number = number.mod(second);
        return number.intValue();
    }

    public int decrypt(int num) {
        BigInteger number = new BigInteger(Integer.toString(num));
	BigInteger second = new BigInteger(Integer.toString(this.n));
        number = number.pow(this.d);
        number = number.mod(second);
        return number.intValue();
    }*/
}
