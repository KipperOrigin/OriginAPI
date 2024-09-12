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

            double centerX = evaluateFormula(baseEffect.getFormulaX(), t);
            double centerY = evaluateFormula(baseEffect.getFormulaY(), t);
            double centerZ = evaluateFormula(baseEffect.getFormulaZ(), t);

            // Calculate the rotation angle for this step
            double rotationAngle = 2 * Math.PI * step / (baseEffect.getTotalSteps() * baseEffect.getRotationDivisor());

            if (baseEffect.getDuplicateValue() > 1) {
                for (int i = 0; i < baseEffect.getDuplicateValue(); i++) {
                    double angle = 2 * Math.PI * i / baseEffect.getDuplicateValue() + rotationAngle;
                    double radius = Math.sqrt(centerX * centerX + centerZ * centerZ);
                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);
                    stepLocations.add(new Vector(x, centerY, z));
                }
            } else {
                // For single particle, rotate around Y-axis
                double x = centerX * Math.cos(rotationAngle) - centerZ * Math.sin(rotationAngle);
                double z = centerX * Math.sin(rotationAngle) + centerZ * Math.cos(rotationAngle);
                stepLocations.add(new Vector(x, centerY, z));
            }

            particleLocations.add(stepLocations);
        }
    }

    private double evaluateFormula(String formula, double t) {
        Expression expression = new ExpressionBuilder(formula)
                .variables("t", "x", "y", "z", "PI")
                .build()
                .setVariable("t", t)
                .setVariable("x", baseEffect.getInitialX())
                .setVariable("y", baseEffect.getInitialY())
                .setVariable("z", baseEffect.getInitialZ())
                .setVariable("PI", Math.PI);
        return expression.evaluate();
    }

    public List<Vector> getCurrentLocations() {
        int index = isReversing ? (particleLocations.size() - 1 - currentStep) : currentStep;
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