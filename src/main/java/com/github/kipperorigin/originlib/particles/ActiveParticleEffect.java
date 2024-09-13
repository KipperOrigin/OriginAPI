package com.github.kipperorigin.originlib.particles;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ActiveParticleEffect {

    private ParticleEffect baseEffect;
    private List<List<Vector>> particleLocations;
    private int currentStep;
    private int currentRepetition;
    private boolean isReversing;

    public ActiveParticleEffect(ParticleEffect baseEffect) {
        this.baseEffect = baseEffect;
        this.particleLocations = new ArrayList<>();
        this.currentStep = 0;
        this.currentRepetition = 0;
        this.isReversing = false;
        calculateParticleLocations();
    }

    private void calculateParticleLocations() {
        for (int step = 0; step < baseEffect.getTotalSteps(); step++) {
            List<Vector> stepLocations = new ArrayList<>();
            double t = (double) step / baseEffect.getTotalSteps();

            Vector center = evaluateVectorFormula(baseEffect.getVectorFormula(), t);

            // Calculate the rotation angle for this step
            double rotationAngle = 2 * Math.PI * step / (baseEffect.getTotalSteps() * baseEffect.getRotationDivisor());

            if (baseEffect.getDuplicateValue() > 1) {
                for (int i = 0; i < baseEffect.getDuplicateValue(); i++) {
                    double angle = 2 * Math.PI * i / baseEffect.getDuplicateValue() + rotationAngle;
                    Vector rotated = rotateAroundY(center, angle);
                    stepLocations.add(rotated);
                }
            } else {
                // For single particle, rotate around Y-axis
                Vector rotated = rotateAroundY(center, rotationAngle);
                stepLocations.add(rotated);
            }

            particleLocations.add(stepLocations);
        }
    }

    private Vector evaluateVectorFormula(String vectorFormula, double t) {
        String[] components = vectorFormula.split(",");
        if (components.length != 3) {
            throw new IllegalArgumentException("Vector formula must have exactly 3 components");
        }

        double x = evaluateComponent(components[0], t);
        double y = evaluateComponent(components[1], t);
        double z = evaluateComponent(components[2], t);

        return new Vector(x, y, z).add(baseEffect.getInitialPosition());
    }

    private double evaluateComponent(String formula, double t) {
        Expression expression = new ExpressionBuilder(formula)
                .variables("t", "x", "y", "z", "PI")
                .build()
                .setVariable("t", t)
                .setVariable("x", baseEffect.getInitialPosition().getX())
                .setVariable("y", baseEffect.getInitialPosition().getY())
                .setVariable("z", baseEffect.getInitialPosition().getZ())
                .setVariable("PI", Math.PI);
        return expression.evaluate();
    }

    private Vector rotateAroundY(Vector v, double angle) {
        double x = v.getX() * Math.cos(angle) - v.getZ() * Math.sin(angle);
        double z = v.getX() * Math.sin(angle) + v.getZ() * Math.cos(angle);
        return new Vector(x, v.getY(), z);
    }

    public List<Vector> getCurrentLocations() {
        int index = isReversing ? (particleLocations.size() - 1 - currentStep) : currentStep;
        return particleLocations.get(index);
    }

    public List<Vector> getLocationsAtStep(int counter) {
        if (counter - baseEffect.getStartDelay() < 0 || counter - baseEffect.getStartDelay() % baseEffect.getDelayBetweenEmits() == 0) return null;
        long stepCounter = counter - baseEffect.getStartDelay() % baseEffect.getDelayBetweenEmits();
        long repetitions = stepCounter / baseEffect.getTotalSteps();
        long remainder = stepCounter % baseEffect.getTotalSteps();
        int index = (int) ((repetitions % 2 == 0) ? remainder : baseEffect.getTotalSteps() - remainder);
        return particleLocations.get(index);
    }

    public void nextStep() {
        currentStep++;
        if (currentStep >= baseEffect.getTotalSteps()) {
            currentStep = 0;
            currentRepetition++;
            if (currentRepetition >= baseEffect.getRepetitionsBeforeBehavior()) {
                handleBehavior();
            }
        }
    }

    private void handleBehavior() {
        switch (baseEffect.getBehavior()) {
            case REPEAT:
                currentRepetition = 0;
                break;
            case REVERSE:
                isReversing = !isReversing;
                currentRepetition = 0;
                break;
            case END:
                // The playParticleEffect method will handle ending the effect
                break;
        }
    }

    public ParticleEffect getBaseEffect() {
        return baseEffect;
    }

    public void setBaseEffect(ParticleEffect baseEffect) {
        this.baseEffect = baseEffect;
    }

    public List<List<Vector>> getParticleLocations() {
        return particleLocations;
    }

    public void setParticleLocations(List<List<Vector>> particleLocations) {
        this.particleLocations = particleLocations;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getCurrentRepetition() {
        return currentRepetition;
    }

    public void setCurrentRepetition(int currentRepetition) {
        this.currentRepetition = currentRepetition;
    }

    public boolean isReversing() {
        return isReversing;
    }

    public void setReversing(boolean reversing) {
        isReversing = reversing;
    }
}