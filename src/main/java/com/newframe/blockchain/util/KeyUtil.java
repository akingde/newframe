package com.newframe.blockchain.util;

import com.newframe.blockchain.algorithm.eddsa.EdDSAPrivateKey;
import com.newframe.blockchain.algorithm.eddsa.spec.EdDSANamedCurveTable;
import com.newframe.blockchain.algorithm.eddsa.spec.EdDSAParameterSpec;
import com.newframe.blockchain.algorithm.eddsa.spec.EdDSAPrivateKeySpec;
import com.newframe.blockchain.algorithm.sha3.sha.Keccak;
import com.newframe.blockchain.algorithm.sha3.sha.Parameters;

import java.util.UUID;

/**
 * 公私钥工具
 *
 * @author wangtao
 * @date 2018/6/15
 */

public class KeyUtil {

    private KeyUtil() {
        throw new AssertionError("不能实例化 KeyUtil");
    }

    /**
     * 获取私钥
     */
    public static String privateKey() throws Exception {
        String message = UUID.randomUUID().toString() + System.currentTimeMillis();
        System.out.println(message);
        Keccak keccak = new Keccak();
        String hexKey = HexUtil.bytesToHex(message.getBytes());
        byte[] hash = keccak.getHash(hexKey.getBytes(), Parameters.KECCAK_256);
        return HexUtil.bytesToHex(hash);
    }

    /**
     * 获取公钥
     */
    public static String publicKey(String privateKey) throws Exception {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        EdDSAPrivateKeySpec privKey = new EdDSAPrivateKeySpec(HexUtil.hexToBytes(privateKey), spec);
        EdDSAPrivateKey sKey = new EdDSAPrivateKey(privKey);
        return HexUtil.bytesToHex(sKey.getAbyte());
    }
}
