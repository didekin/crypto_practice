package com.didekin.tutor.cipher;

import java.security.SecureRandom;

/**
 * User: pedro@didekin
 * Date: 25/04/2018
 * Time: 18:02
 * <p>
 *
 * A DRBG may be used to obtain pseudorandom bits for different purposes (e.g., DSA private keys and AES keys)
 *
 * - The seed is a function of entropy input, a nonce and a personalization string.
 * - The random generator generates pseudorandom bits upon request using the seed and possibly additional input.
 * A new internal state for the next request is also generated.
 * - The reseed function acquires new entropy input and combines it with the current internal state and any additional input that is provided
 * to create a new seed and a new internal state.
 */
public class DRGBOne {

    /*SP800SecureRandom random =
            new SP800SecureRandomBuilder(new SecureRandom(), true)
                    .setPersonalizationString(
                            Strings.toByteArray("My Bouncy Castle SecureRandom"))
                    .buildHash(new SHA512Digest(), null, false);*/


}
