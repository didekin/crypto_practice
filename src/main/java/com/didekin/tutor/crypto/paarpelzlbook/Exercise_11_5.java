package com.didekin.tutor.crypto.paarpelzlbook;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.math.BigDecimal.valueOf;

/**
 * User: pedro
 * Date: 24/07/16
 * Time: 14:15
 */
public class Exercise_11_5 {

    final int length;
    final double collisionProbability;

    public Exercise_11_5(int length, double collisionProbability)
    {
        this.length = length;
        this.collisionProbability = collisionProbability;
    }

    private static double getResult(Exercise_11_5 exercise)
    {
        double power = pow(2, (exercise.length + 1) / 2);
        double ln = log((1 / (1 - exercise.collisionProbability)));
        return power * sqrt(ln);
    }

    public static void main(String[] args)
    {
        Exercise_11_5 exercise = new Exercise_11_5(64, 0.5);
        double result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);

        exercise = new Exercise_11_5(64, .1);
        result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);

        exercise = new Exercise_11_5(128, .5);
        result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);

        exercise = new Exercise_11_5(128, .1);
        result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);

        exercise = new Exercise_11_5(160, .5);
        result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);

        exercise = new Exercise_11_5(160, .1);
        result = getResult(exercise);
        System.out.printf("Length %d  probability = %.1f inputs = %.0f %n", exercise.length, exercise
                .collisionProbability, result);
    }
}
